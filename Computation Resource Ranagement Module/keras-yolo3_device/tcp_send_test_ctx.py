# Author:Han
# @Time : 2020/1/12 13:44
# -*- coding: utf-8 -*-
import socket
import os

def send_str():
    tcp = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    edge_ip = '172.19.5.75'  # 边的ip
    edge_port = 2000
    edge_addr = (edge_ip, edge_port)
    print('正在连接边缘服务器')
    tcp.connect(edge_addr)
    print('连接成功')