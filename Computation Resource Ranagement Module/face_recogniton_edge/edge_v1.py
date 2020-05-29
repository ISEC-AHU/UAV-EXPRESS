import socket
import cv2
import os
import pickle

# 边 - 端 接收姓名
def receive_name():
    tcp_client1 = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    # 先与终端连接 接收 收货人姓名
    print('正在连接终端')
    tcp_client1.bind(('172.19.5.75', 2000))  # 边自己的ip
    tcp_client1.listen(128)
    sock_client, client_addr = tcp_client1.accept()
    print('连接成功')
    print('终端地址', client_addr)
    # 获得收货人姓名
    name = sock_client.recv(1024).decode()
    print(name)
    sock_client.send('client_ok'.encode())
    return name


# 边- 云  下载云的模型
def send_name_to_server(name):
    tcp = socket.socket(socket.AF_INET, socket.SOCK_STREAM) # 使用IPv4、TCP套接字类型
    server_ip = '172.19.5.75'  # 云的ip  172.19.5.75  122.51.166.174
    server_port = 1000          # 云的端口
    server_addr = (server_ip, server_port)
    tcp.connect(server_addr)  # 连接到远程socket
    print('与服务器连接成功')
    # 将姓名发送给服务器
    tcp.send(name.encode())
    # 接收模型大小
    model_size = tcp.recv(1024 * 1024 * 32).decode()
    print('model文件大小：', model_size)
    tcp.send('edge_ok'.encode())
    # 循环接收 模型
    model_path = 'edge_model'
    count = 0
    flag1 = True
    while True:
        data = tcp.recv(1024 * 1024 * 32).decode()
        if data == 'complete':
            print('complete')
            break
        count += len(data)
        print(count)  # 输出已经到达模型数据大小
        # 第一次新建model文件
        if flag1:
            model_txt = open(model_path, 'w+')
            model_txt.write(data)
            model_txt.close()
            flag1 = False
        else:
            new_txt = open(model_path, 'a+')
            new_txt.write(data)
            new_txt.close()
        print('已传输', count * 100 / int(model_size), '%')
        if count == int(model_size):
            print('count == model_size 已经传输完毕')
            break

    tcp.send('edge_ok'.encode())


# 边-端 接收端的图片
def receive_img():
    tcp_client1 = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    # 与终端连接 接收 收货人照片
    print('等待连接终端')
    tcp_client1.bind(('172.19.5.75', 2000))  # 边自己的ip #172.19.5.18
    tcp_client1.listen(128)
    sock_client, client_addr = tcp_client1.accept()  # 返回一对（conn socket对象，可以在该连接上发送和接受数据，address 另一端地址）
    print('连接成功')
    print('终端地址', client_addr)
    # 接收图片大小
    img_size = sock_client.recv(1024*1024)  # 从socket接收数据： bufsize 一次性接收最大数据量
    print('图片size:', int(img_size))
    sock_client.send('edge_ok'.encode())  # 将数据发送到socket
    count = 0

    img_path = 'test_img.jpg'
    while True:
        data = sock_client.recv(1024 * 1024 * 32)
        count += len(data)
        print(count)

        # 第一次新建model文件  , ab 接收二进制文件
        model_txt = open(img_path, 'ab')
        model_txt.write(data)
        model_txt.close()

        print('已传输', count * 100 / int(img_size), '%')
        if count == int(img_size):
            break
    print(img_path)
    return img_path


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


# 根据给定的（x，y）坐标和宽度高度在图像上绘制矩形
def draw_rectangle(img, rect):
    (x, y, w, h) = rect
    cv2.rectangle(img, (x, y), (x + w, y + h), (128, 128, 0), 2)


# 根据给定的（x，y）坐标标识出人名
def draw_text(img, text, x, y):
    cv2.putText(img, text, (x, y), cv2.FONT_HERSHEY_COMPLEX, 1, (128, 128, 0), 2)


# 此函数识别传递的图像中的人物并在检测到的脸部周围绘制一个矩形及其名称
def predict(test_img):
    subjects = ['0']
    # 生成图像的副本，这样就能保留原始图像
    img = test_img.copy()
    # 检测人脸
    face, rect = detect_face(img)
    # 预测人脸
    label = face_recognizer.predict(face)
    print('predict,偏离度', label)
    if label[1] >= 50:  #偏离度过大 打印worry
        return True
    # 获取由人脸识别器返回的相应标签的名称
    label_text = subjects[label[0]]

    # 在检测到的脸部周围画一个矩形
    draw_rectangle(img, rect)
    # 标出预测的名字
    draw_text(img, label_text, rect[0], rect[1] - 5)
    # 返回预测的图像
    return img


if __name__ == '__main__':
    name = receive_name()
    print('已完成接收名字，发送给云端训练模型')
    send_name_to_server(name)  # 从云端 下载模型
    print('已完成接收模型，准备接收终端匹配图像')

    while True:   # GH添加
        img_path = receive_img()  # 接受端的图像
        print('开始人脸识别')
        face_recognizer = cv2.face.LBPHFaceRecognizer_create()
        face_recognizer.read('edge_model')

        # 加载测试图像
        test_img1 = cv2.imread(img_path)
        #os.remove(img_path)
        # 执行预测
        predicted_img1 = predict(test_img1)

        #os.remove('edge_model')
        if predicted_img1 is True:  #偏离度过大 打印worry
            print('wrong 不是收货人身份\n')
        else:
            print('right 确认收货人身份\n')
            # 显示图像
            cv2.namedWindow('', cv2.WINDOW_NORMAL)
            cv2.imshow('', predicted_img1)
            cv2.waitKey(0) # 等待键盘输入时间 0代表无限等待
            cv2.destroyAllWindows()


