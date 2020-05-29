import socket
import os
import struct
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


def send_img(img_path):
    tcp = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    edge_ip = '172.19.5.75'  # 边的 ip
    edge_port = 2000
    edge_addr = (edge_ip, edge_port)
    print('正在连接边缘服务器')
    tcp.connect(edge_addr)
    print('连接成功')
    #img_path = 'test/000001.jpg'  # 发送固定图片路径
    img_size = os.path.getsize(img_path)  # 经过yolo检测后发送到edge
    print('图片大小：', img_size)
    tcp.send(str(img_size).encode())
    signal = tcp.recv(1024).decode()

    if signal == 'edge_ok':
        img = open(img_path, 'rb')
        print('开始发送图片')
        while True:
            data = img.read(1024*1024)
            tcp.send(data)
            if len(data) == 0:
                print('图片发送完毕')
                break

    tcp.close()  # GH注释


if __name__ == '__main__':
    send_name()
    while True:# GH添加
        signal = input('>>>')
        if signal == '1':
            send_img()
        elif signal == '2':
            break


