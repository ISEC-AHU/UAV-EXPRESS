import socket
import os
import cv2
import numpy as np
import time
import face_recognition
import pickle


def face_train(name, path='training_data/'):
    # Load the jpg files into numpy arrays
    name_image = face_recognition.load_image_file(path + name + ".jpg")
    print(path + name + ".jpg")
    try:
        name_face_encoding = face_recognition.face_encodings(name_image)[0]
    except IndexError:
        print("I wasn't able to locate any faces in at least one of the images. Check the image files. Aborting...")
        quit()

    known_faces = [
        name_face_encoding
    ]
    return known_faces


# results = face_recognition.compare_faces(known_faces, unknown_face_encoding)

def main():
    tcp = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    # tcp.bind(('172.19.5.75', 1000))  # 云自己的ip
    tcp.bind(('192.168.31.108', 4000))
    print('服务器运行中')
    tcp.listen(128)

    while True:
        # 等待连接
        sock, client_addr = tcp.accept()
        print('客户端地址', client_addr)

        # 获得收件人姓名
        name = sock.recv(1024).decode()

        # 寻找收件人的训练集
        known_faces = face_train(name)

        # 向边发送收件人的训练集
        # ZBW add
        data = pickle.dumps(known_faces)
        sock.send(data)
        print('发送完成')
        sock.close()

        """
        # 获取模型大小
        fil_size = os.path.getsize('recognizer')
        print('发送模型size')
        sock.send(str(fil_size).encode())
        sign = sock.recv(1024).decode()
        if sign == 'edge_ok':
            file = open('recognizer')
            print('发送模型')
            while True:
                send_data = file.read()
                sock.send(send_data.encode())
                if len(send_data) == 0:
                    print('发送完成')
                    break
        time.sleep(5)  # 进程挂起秒数
        sock.send('complete'.encode())
        finial_signal = sock.recv(1024)
        if finial_signal == 'edge_ok':
            sock.close()
        """


if __name__ == '__main__':
    main()
