# -*- coding: utf-8 -*-
import socket
import os
import pickle
import time

EDGE_ID = '192.168.31.108'
EDGE_PORT = 4587


# 传输收货人姓名
def send_name():
    tcp = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    edge_ip = EDGE_ID  # 边的ip
    edge_port = EDGE_PORT
    edge_addr = (edge_ip, edge_port)
    print('正在连接边缘服务器')
    tcp.connect(edge_addr)
    print('连接成功')

    name = input('起飞前，得到收货人姓名：')
    tcp.send(name.encode())
    tcp.close()


# 传输切割后图片到边
def send_img(img_path, img_name):
    """
    发送图像和目标检测的坐标信息
    @param img_path: 图片路径
    @param img_name：图片名称
    """
    tcp = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    edge_ip = EDGE_ID  # 边的 ip
    edge_port = EDGE_PORT
    edge_addr = (edge_ip, edge_port)
    print('正在连接边缘服务器')
    tcp.connect(edge_addr)
    print('连接成功')
    # img_path = 'test/000001.jpg'  # 发送固定图片路径
    img_size = os.path.getsize(img_path)  # 经过yolo检测后发送到edge
    print('图片大小：', img_size)
    print(time.time())
    tcp.send(str(img_size).encode())
    signal = tcp.recv(1024).decode()

    if signal == 'edge_ok':
        img = open(img_path, 'rb')
        print('开始发送图片')
        while True:
            data = img.read(1024 * 1024)
            tcp.send(data)
            if len(data) == 0:
                print('图片发送完毕')
                break

        # 发送图片名称
        signal_img_name = tcp.recv(1024).decode()
        if signal_img_name == 'coords_recv':
            print(img_name)
            data_img_name = img_name.encode()
            # data_img_name = pickle.dumps(img_name)
            tcp.send(data_img_name)

    # # while(True):  # 确认边处理结束
    #     signal_face_ok = tcp.recv(1024).decode()
    #     print('=======')
    #     if signal_face_ok == 'recognition_recv':
    #         print('recognition_recv')
    #         # break

    tcp.close()
