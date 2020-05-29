import socket
import os
import struct


def send_name():
    tcp = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    edge_ip = '172.19.5.75' # 边的ip
    edge_port = 2000
    edge_addr = (edge_ip, edge_port)
    print('正在连接边缘服务器')
    tcp.connect(edge_addr)
    print('连接成功')

    name = input('请输入收货人姓名：')
    tcp.send(name.encode())
    tcp.close()


def send_img():
    tcp = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    edge_ip = '172.19.5.75'  # 边的 ip
    edge_port = 2000
    edge_addr = (edge_ip, edge_port)
    print('正在连接边缘服务器')
    tcp.connect(edge_addr)
    print('连接成功')
    img_path = 'test/0.jpg'
    img_size = os.path.getsize(img_path)
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

    tcp.close()


if __name__ == '__main__':
    send_name()
    signal = input('>>>')
    if signal == '1':
        send_img()


