# -*- coding: utf-8 -*-
import socket
import cv2
import pickle
import face_recognition
import divide_detect_img
from PIL import Image
import os
import csv
import time

from posedetect_expr import *

'''
训练得到训练特征

计时1
读入文件夹（包含图片数据集X2）
对图片数据集进行姿势识别，调用posedetect_test.XXXXX
输出图片数据集到指定文件夹（图片数据集X3）
保留识别正确的图片的名称true_name
计时1结束

计时2
读入图片数据集X2和true_name进行人脸匹配（提取待匹配人脸特征和对比）
输出对应compare为true的图片名称
计时2结束
'''

TRAINING_DATA_PATH = 'training_data/'
KNOWN_NAME = 'gaohan'
CUT_IMAGE_PATH = '2500/'
POSE_IMAGE_PATH = '200_pose_image/'
POSE_TRUE_IMAGE_PATH = '200_pose_true_image/'
CSV_NAME = '2500_edge_over_1_and_2.csv'


def init_folder(path, is_data_set=False):
    """
    :param path: 文件夹路径
    :param is_data_set: 是否是数据集
    """
    if os.path.exists(path):
        if not is_data_set:
            for file in os.listdir(path):
                path_file = os.path.join(path, file)
                if os.path.isfile(path_file):
                    os.remove(path_file)
    else:
        os.makedirs(path)


# 获取训练特征列表
def train_face_recognition(training_data_path, known_name):
    """
    :param training_data_path: 训练集的路径
    :param known_name: 所需检测对象的姓名
    :return: 训练得出的特征列表
    """
    known_image = face_recognition.load_image_file(training_data_path + known_name + ".jpg")
    known_faces = []
    try:
        known_face_encoding = face_recognition.face_encodings(known_image)[0]
        known_faces.append(known_face_encoding)
    except IndexError:
        print('找不到姓名为 %s 的人脸！' % known_name)
    return known_faces


# 识别全部姿势
def pose_detect_all(cut_image_path, pose_image_path, pose_true_image_path):
    """
    :param cut_image_path: 切割后的数据集路径
    :param pose_image_path: 姿势识别后的数据集路径
    :param pose_true_image_path: 姿势识别正确的数据集路径
    :return filename_list: 姿势识别正确的文件名列表
    :return pose_detect_time: 姿势识别时间
    :return cut_image_size: 切割后的数据集大小
    :return pose_true_image_size: 姿势识别正确的数据集大小
    """
    start_time = time.time()
    ls = os.listdir(cut_image_path)
    filename_list = []

    for filename in ls:
        image_path = cut_image_path + filename
        result = pose_recognition(image_path, pose_image_path, pose_true_image_path, filename)
        if result:
            filename_list.append(filename)

    pose_detect_time = time.time() - start_time
    cut_image_size = get_folder_size(cut_image_path)
    pose_image_size = get_folder_size(pose_image_path)
    pose_true_image_size = get_folder_size(pose_true_image_path)
    return filename_list, pose_detect_time, cut_image_size, pose_image_size, pose_true_image_size


# 人脸匹配
def unknown_face_recognition(cut_image_path, filename_list, known_faces, known_name):
    """
    :param cut_image_path: 切割后的图像文件夹
    :param filename_list: 正确姿势的文件名列表
    :param known_faces: 训练集特征列表
    :param known_name: 收货人姓名
    :return result_list: 人脸匹配结果列表
    :return face_recognition_time: 人脸匹配时间
    """
    start_time = time.time()
    result_list = []

    for filename in filename_list:
        try:
            image_path = cut_image_path + filename
            unknown_image = face_recognition.load_image_file(image_path)
            unknown_face_encoding = face_recognition.face_encodings(unknown_image)[0]
            result = face_recognition.compare_faces(known_faces, unknown_face_encoding)
            if len(result) > 0 and result[0]:
                result_list.append([filename, known_name])
            else:
                result_list.append([filename, 'unknown'])
        except IndexError:
            print('文件 %s 未识别到人脸！' % filename)

    face_recognition_time = time.time() - start_time
    return result_list, face_recognition_time

# 人脸匹配(不经过姿势识别)
def unknown_face_recognition_over_openpose(cut_image_path, known_faces, known_name):
    """
    :param cut_image_path: 切割后的图像文件夹
    :param known_faces: 训练集特征列表
    :param known_name: 收货人姓名
    :return result_list: 人脸匹配结果列表
    :return face_recognition_time: 人脸匹配时间
    """
    start_time = time.time()
    result_list = []
    ls = os.listdir(cut_image_path)

    for filename in ls:
        try:
            image_path = cut_image_path + filename
            unknown_image = face_recognition.load_image_file(image_path)
            unknown_face_encoding = face_recognition.face_encodings(unknown_image)[0]
            result = face_recognition.compare_faces(known_faces, unknown_face_encoding)
            if len(result) > 0 and result[0]:
                result_list.append([filename, known_name])
            else:
                result_list.append([filename, 'unknown'])
        except IndexError:
            print('文件 %s 未识别到人脸！' % filename)

    face_recognition_time = time.time() - start_time
    return result_list, face_recognition_time

# 获取文件夹的大小，单位B
def get_folder_size(path):
    ls = os.listdir(path)
    size = 0
    for filename in ls:
        size += os.path.getsize(path + filename)
    return size


def main():
    with open(CSV_NAME, 'w', newline='') as f:
        f_log = csv.writer(f)
        f_log.writerow([
            '切割后的数据集大小',
            '姿势识别后的数据集大小',
            '姿势识别率',
            '姿势识别时间',
            '人脸匹配时间'
        ])
        # 第一步：获取训练特征列表
        known_faces = train_face_recognition(TRAINING_DATA_PATH, KNOWN_NAME)
        if not known_faces:
            quit()  # 找不到人脸则退出
        # 第二步：姿势识别
        filename_list, pose_detect_time, cut_image_size, pose_image_size, \
            pose_true_image_size = pose_detect_all(CUT_IMAGE_PATH, POSE_IMAGE_PATH, POSE_TRUE_IMAGE_PATH)
        # 第三步：人脸匹配
        result_list, face_recognition_time = \
            unknown_face_recognition(CUT_IMAGE_PATH, filename_list, known_faces, KNOWN_NAME)
        # 第四步：计算
        pose_true_rate = pose_true_image_size / pose_image_size
        # 第五步：写需要记录的数据
        f_log.writerow([
            cut_image_size,
            pose_true_image_size,
            pose_true_rate,
            pose_detect_time,
            face_recognition_time
        ])
        # 第六步：写人脸识别结果
        f_log.writerows(result_list)

def main_over_pose():
    with open(CSV_NAME, 'w', newline='') as f:
        f_log = csv.writer(f)
        f_log.writerow([
            '切割后的数据集大小',
            '姿势识别后的数据集大小',
            '姿势识别率',
            '姿势识别时间',
            '人脸匹配时间'
        ])
        # 第一步：获取训练特征列表
        known_faces = train_face_recognition(TRAINING_DATA_PATH, KNOWN_NAME)
        if not known_faces:
            quit()  # 找不到人脸则退出
        # 第三步：人脸匹配
        result_list, face_recognition_time = \
            unknown_face_recognition_over_openpose(CUT_IMAGE_PATH, known_faces, KNOWN_NAME)
        cut_image_size = get_folder_size(CUT_IMAGE_PATH)
        # 第五步：写需要记录的数据
        f_log.writerow([
            cut_image_size,
            face_recognition_time
        ])
        # 第六步：写人脸识别结果
        f_log.writerows(result_list)

if __name__ == '__main__':
    init_folder(CUT_IMAGE_PATH, True)

    # 1、2、3  结果=100_edge.csv
    # init_folder(POSE_IMAGE_PATH)
    # init_folder(POSE_TRUE_IMAGE_PATH)
    # main()

    # 2、3 CUT_IMAGE_PATH 修改为原数据数据路径  结果=100_edge_over_1.csv
    # init_folder(POSE_IMAGE_PATH)
    # init_folder(POSE_TRUE_IMAGE_PATH)
    # main()

    # 1、3  结果=100_edge_over_openpose.csv
    # main_over_pose()

    # 3 CUT_IMAGE_PATH 修改为原数据数据路径  结果=100_edge_over_1_and_2.csv
    main_over_pose()