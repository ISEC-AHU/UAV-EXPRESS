# -*- coding: utf-8 -*-
# Author: ZBW
# @Time : 2020/1/11 22:31

from PIL import Image
import numpy as np

# 根据方框坐标切割图像
def cut_image(image, coord):
    """
    根据方框的位置切分图像
    :param image: 待识别的图像对象
    :param coord: 一个方框的坐标集
    :return:
    """
    im_array = np.array(image).copy()
    im_array = im_array[coord[0]:coord[1], coord[2]:coord[3], :]
    return Image.fromarray(im_array)

# 获取单个图像分割出的所有目标
def get_targets(image, coords):
    """
    获取单个图像分割出的所有目标
    :param img: 图像对象
    :param coords: 分割线坐标，二维数组，第一维是框的个数，第二维是4个坐标值
    :return: 若干个图像组成的列表，表示分割后的图像
    """
    result = []
    for coord in coords:
        img = cut_image(image, coord)
        result.append(img)
    return result
