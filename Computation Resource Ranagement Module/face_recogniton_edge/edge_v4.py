# -*- coding: utf-8 -*-
import socket
import cv2
import pickle
import face_recognition
import divide_detect_img
from PIL import Image
import os
import time

CLOUD_ID = '192.168.31.108'
CLOUD_PORT = 3000
EDGE_ID = '192.168.31.108'
EDGE_PORT = 4586

# tcp = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
# print('正在连接终端')
# tcp.bind((EDGE_ID, EDGE_PORT))  # 边自己的ip
# tcp.listen(128)
# sock_client, client_addr = tcp.accept()
# print('连接成功')
# print('终端地址', client_addr)


# 接受收货人姓名
def receive_name():
    """
    边-端 接收姓名
    :return: 收货人姓名
    """
    tcp_client1 = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    # 先与终端连接 接收 收货人姓名
    print('正在连接终端')
    tcp_client1.bind((EDGE_ID, EDGE_PORT))  # 边自己的ip
    tcp_client1.listen(128)
    sock_client, client_addr = tcp_client1.accept()
    print('连接成功')
    print('终端地址', client_addr)

    # 获得收货人姓名
    name = sock_client.recv(1024).decode()
    print(name)
    sock_client.send('client_ok'.encode())
    return name


# 发送收货人姓名到云，下载指定模型
def send_name_to_server(name):
    """
    边-云 下载云的模型
    :param name: 收货人的姓名
    :return: NULL
    """
    tcp = socket.socket(socket.AF_INET, socket.SOCK_STREAM)  # 使用IPv4、TCP套接字类型
    server_ip = CLOUD_ID  # 云的ip  172.19.5.75  122.51.166.174
    server_port = CLOUD_PORT  # 云的端口
    server_addr = (server_ip, server_port)
    tcp.connect(server_addr)  # 连接到远程socket
    print('与服务器连接成功')
    # 将姓名发送给服务器
    tcp.send(name.encode())

    # 接收模型（列表）
    data = tcp.recv(1024 * 1024 * 32)
    print('接受完成')
    known_faces = pickle.loads(data)
    name_face_encoding = known_faces[0]
    print(name_face_encoding)

    tcp.send('edge_ok'.encode())
    return known_faces

# 接收图像、接收检测框坐标、分割图像、保存分割图像、（姿势识别）、人脸识别
# 接受目标识别后的图片并进行切分和保存，返回切分后的图片的地址
def receive_img():
    """
    边-端 接收端的图片
    :return: 经过目标检测后识别出的所有图片的路径，列表
    """
    # 与终端进行连接
    tcp_client1 = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    print('等待连接终端')
    tcp_client1.bind((EDGE_ID, EDGE_PORT))  # 边自己的ip #172.19.5.18
    tcp_client1.listen(128)
    sock_client, client_addr = tcp_client1.accept()  # 返回一对（conn socket对象，可以在该连接上发送和接受数据，address 另一端地址）
    print('连接成功')
    print('终端地址', client_addr)

    # 接收从终端发送来的图片
    img_size = sock_client.recv(1024 * 1024)  # 从socket接收数据： bufsize 一次性接收最大数据量
    print('图片size:', int(img_size))
    sock_client.send('edge_ok'.encode())  # 将数据发送到socket
    count = 0  # 已获取的数据量

    # TODO 可修改，当前是把传过来的图片保存到img_path，多图片会覆盖
    img_path = 'test_img.jpg'
    while True:
        data = sock_client.recv(1024 * 1024 * 32)
        count += len(data)
        print(count)

        # 打开img_path，写入接收图片数据，ab 接收二进制文件
        receive_img_data = open(img_path, 'ab')
        receive_img_data.write(data)
        receive_img_data.close()

        print('已传输', count * 100 / int(img_size), '%')
        if count == int(img_size):
            break
    print('图片路径：', img_path)
    # 图片传输完毕，发送确认信息，可接受坐标信息
    sock_client.send('edge_recv'.encode())  # 将数据发送到socket

    # 接收目标检测坐标信息
    data = sock_client.recv(1024 * 1024)
    coords = pickle.loads(data)
    print(coords)

    # 坐标信息接收完毕，可接收图片名称
    sock_client.send('coords_recv'.encode())

    # 接收图片名称
    data_img_name = sock_client.recv(1024 * 1024)
    # img_name = pickle.loads(data_img_name)
    img_name = data_img_name.decode()
    # print("img_name = %s" % img_name)
    img_name = img_name[:-4]
    print("new_img_name = %s" % img_name)

    return img_path, coords, img_name
    # # 切分图像并进行保存
    # img = Image.open(img_path)
    # detected_img = divide_detect_img.get_targets(img, coords)
    # img_paths = []
    # for i in range(len(detected_img)):
    #     path = 'result/' + img_name + '_' + str(i) + '.jpg'
    #     # path = 'result/temp_'+str(i)+'.jpg'
    #     detected_img[i].save(path)
    #     img_paths.append(path)
    #
    # os.remove(img_path)

    # print('成功接受检测后的图像，开始人脸识别')
    # for img_path in img_paths:
    #     print('当前识别的图片路径为：%s' % img_path)
    #     try:
    #         # 检测从端发送并切割的图像，然后提取其特征
    #         unknown_image = face_recognition.load_image_file(img_path)
    #         unknown_face_encoding = face_recognition.face_encodings(unknown_image)[0]
    #         # 匹配从云获取的图像特征和当前切割后的图像特征是否是同一人
    #         results = face_recognition.compare_faces(known_faces, unknown_face_encoding)
    #         print(results)
    #     except IndexError:
    #         print('没有识别到人脸！')

        # TODO 加入openpose姿势识别

    # return img_paths

        # 切分图像并进行保存
def divide_image(img_path, coords, img_name):
    # 切分图像并进行保存
    img = Image.open(img_path)
    detected_img = divide_detect_img.get_targets(img, coords)
    img_paths = []
    for i in range(len(detected_img)):
        path = 'result/' + img_name + '_' + str(i) + '.jpg'
        # path = 'result/temp_'+str(i)+'.jpg'
        detected_img[i].save(path)
        img_paths.append(path)

    os.remove(img_path)
    return img_paths


if __name__ == '__main__':

    unknown_image = face_recognition.load_image_file("result_2_0.jpg")
    unknown_face_encoding = face_recognition.face_encodings(unknown_image)[0]

    result_path = './result'
    # result如果之前存放的有文件，全部清除
    for i in os.listdir(result_path):
        path_file = os.path.join(result_path, i)
        if os.path.isfile(path_file):
            os.remove(path_file)

    name = receive_name()
    print('已完成接收名字，发送给云端训练模型')
    known_faces = send_name_to_server(name)  # 从云端 下载模型
    print('已完成接收模型，准备接收终端匹配图像')

    while True:  # GH添加
        print('开始接收图片，开始计时')
        img_path, coords, img_name = receive_img()  # 接受检测后的图像、分割坐标、图像名 # TODO 传输时间 transmission_time 需要记入excle
        img_paths = divide_image(img_path, coords, img_name)  # 切分图像并保存   # TODO 切割图像时间 divide_time 需要记入excle


        print('开始姿势识别')  # 进入姿势识别
        openpose_start_time =time.time()
        # TODO 加入姿势识别  并需要姿势识别时间openpose_time 记入excle
        openpose_end_time = time.time()
        openpose_time = openpose_start_time - openpose_end_time


        print('成功接受检测后的图像，开始人脸识别')
        for img_path in img_paths:
            print('当前识别的图片路径为：%s' % img_path)
            try:
                # 检测从端发送并切割的图像，然后提取其特征
                unknown_image = face_recognition.load_image_file(img_path)
                unknown_face_encoding = face_recognition.face_encodings(unknown_image)[0]
                # 匹配从云获取的图像特征和当前切割后的图像特征是否是同一人
                results = face_recognition.compare_faces(known_faces, unknown_face_encoding)
                print(results)
            except IndexError:
                print('没有识别到人脸！')
        print('结束图片处理，结束计时')
