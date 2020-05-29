
import os

import posedetect_test

if __name__ == '__main__':

    # 创建创建一个存储检测结果的dir
    result_path = './result'
    if not os.path.exists(result_path):
        os.makedirs(result_path)  # 如果没有就创建文件夹

    # result如果之前存放的有文件，全部清除
    for i in os.listdir(result_path):
        path_file = os.path.join(result_path, i)
        if os.path.isfile(path_file):
            os.remove(path_file)



    # img_path = "right_arm.jpg"  # 接受端的图像
    dir_path = os.path.dirname(os.path.realpath(__file__))
    path = dir_path+'/video_frame/'  # 待检测图片的位置
    ls = os.listdir(path)
    ls.sort()
    for filename in ls:
        print(filename)
        image_path = path + '/' + filename
        print('开始姿势识别')
        portion = os.path.split(image_path)
        save_path = './result/result_pose_'+portion[1]
        #full_img_path = os.path.split(image_path)
        pose_return_result = posedetect_test.pose_detect(image_path, save_path)  # 赋值image_path被识别图片路径 save_path保存图片路径
        print("pose_return_result = %s" % pose_return_result)