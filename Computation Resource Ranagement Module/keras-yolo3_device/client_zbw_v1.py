# -*- coding: utf-8 -*-
import socket
import pickle
import os

"""
发送模块
1、对收货人姓名发送
2、对图像进行发送
"""

def send_name():
    tcp = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    edge_ip = '172.19.5.75' # 边的ip
    edge_port = 2000
    edge_addr = (edge_ip, edge_port)
    print('正在连接边缘服务器')
    tcp.connect(edge_addr)
    print('连接成功')

    name = input('起飞前，得到收货人姓名：')
    tcp.send(name.encode())
    tcp.close()


def send_img(img, img_path):
    tcp = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    edge_ip = '172.19.5.75'  # 边的 ip
    edge_port = 2000
    edge_addr = (edge_ip, edge_port)
    print('正在连接边缘服务器')
    tcp.connect(edge_addr)
    print('连接成功')

    img.save(img_path) #临时文件存放路径
    img_size = os.path.getsize(img_path)
    print('图片大小：', img_size)
    tcp.send(str(img_size).encode())
    print("send img_size over")
    signal = tcp.recv(1024).decode()

    if signal == 'edge_ok':
        img_old = open(img_path, 'rb')
        print('开始发送图片')
        while True:
            data = img_old.read(1024*1024)
            tcp.send(data)
            if len(data) == 0:
                print('图片发送完毕')
                break

    tcp.close()  # GH注释
