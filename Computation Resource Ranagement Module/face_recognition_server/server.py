import socket
import os
import cv2
import numpy as np
import time

# 检测人脸
def detect_face(img):
    # 将测试图像转换为灰度图像，因为opencv人脸检测器需要灰度图像
    gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)

    # 加载OpenCV人脸检测分类器Haar
    face_cascade = cv2.CascadeClassifier('./haarcascade_frontalface_default.xml')

    # 检测多尺度图像，返回值是一张脸部区域信息的列表（x,y,宽,高）
    faces = face_cascade.detectMultiScale(gray, scaleFactor=1.2, minNeighbors=5)

    # 如果未检测到面部，则返回原始图像
    if (len(faces) == 0):
        return None, None

    # 目前假设只有一张脸，xy为左上角坐标，wh为矩形的宽高
    (x, y, w, h) = faces[0]
    X = (x, y, w, h)

    # 返回图像的正面部分
    return gray[y:y + w, x:x + h], faces[0]


# 该函数将读取所有的训练图像，从每个图像检测人脸并将返回两个相同大小的列表，分别为脸部信息和标签
def prepare_training_data(data_folder_path):
    # 获取数据文件夹中的目录（每个主题的一个目录）
    dirs = os.listdir(data_folder_path)

    # 两个列表分别保存所有的脸部和标签
    faces = []
    labels = []

    # 浏览每个目录并访问其中的图像
    for dir_name in dirs:
        # dir_name(str类型)即标签
        label = int(dir_name)
        # 建立包含当前主题主题图像的目录路径
        subject_dir_path = data_folder_path + "/" + dir_name
        # 获取给定主题目录内的图像名称
        subject_images_names = os.listdir(subject_dir_path)

        # 浏览每张图片并检测脸部，然后将脸部信息添加到脸部列表faces[]
        for image_name in subject_images_names:
            # 建立图像路径
            image_path = subject_dir_path + "/" + image_name
            # 读取图像
            image = cv2.imread(image_path)
            # 显示图像0.1s
            cv2.imshow("Training on image...", image)
            cv2.waitKey(100)

            # 检测脸部
            face, rect = detect_face(image)
            # 我们忽略未检测到的脸部
            if face is not None:
                # 将脸添加到脸部列表并添加相应的标签
                faces.append(face)
                labels.append(label)

    cv2.waitKey(1)
    cv2.destroyAllWindows()
    # 最终返回值为人脸和标签列表
    return faces, labels


def main():
    tcp = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    tcp.bind(('172.19.5.75', 1000))   # 云自己的ip
    print('服务器运行中')
    tcp.listen(128)

    while True:
        # 等待连接
        sock, client_addr = tcp.accept()
        print('客户端地址', client_addr)

        # 获得收件人姓名
        name = sock.recv(1024).decode()

        # 寻找收件人的训练集
        faces, labels = prepare_training_data('training_data/'+name)

        # 创建识别器
        face_recognizer = cv2.face.LBPHFaceRecognizer_create()
        face_recognizer.train(faces, np.array(labels))

        # 保存模型
        face_recognizer.save('recognizer')

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


if __name__ == '__main__':
    main()
