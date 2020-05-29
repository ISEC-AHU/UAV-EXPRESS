"""
端的流程
1. 目标检测
2. 切割
3. 计算
"""

from yolo_expr import *
import csv

INIT_IMAGE_PATH = '2500/'  # 初始数据集路径
DETECT_IMAGE_PATH = '2500_detect_image/'  # 检测后含人的数据集路径
CUT_IMAGE_PATH = '2500_cut_image/'  # 切割后仅含一人的数据集
CSV_NAME = '2500.csv'


# 初始化文件夹，没有则创建，有则删除原有文件（不含数据集）
def init_folder(path, is_data_set=False):
    """
    @param path: 文件夹路径
    @param is_data_set: 是否是数据集，默认不是
    """
    if os.path.exists(path):
        if not is_data_set:
            for file in os.listdir(path):
                path_file = os.path.join(path, file)
                if os.path.isfile(path_file):
                    os.remove(path_file)
    else:
        os.makedirs(path)


# 目标检测
def target_detect(init_image_path, detect_image_path):
    """
    @param init_image_path: 初始数据集文件夹
    @param detect_image_path: 检测后含人的数据集文件夹
    @return init_image_size: 初始数据集大小
    @return detect_image_size: 检测后含人的数据集大小
    @return target_detect_time: 目标检测的时间
    @return filename_list: 初始文件名列表（含人）
    @return coords_list: 坐标信息列表（含人）
    """
    start_time = time.time()
    yolo = YOLO()
    ls = os.listdir(init_image_path)
    ls.sort()  # 将文件名按顺序排列
    coords_list = []
    filename_list = []
    # 图像的检测
    for filename in ls:
        image_path = init_image_path + filename
        portion = os.path.split(image_path)
        file.write(portion[1] + ' detect_result：\n')
        image = Image.open(image_path)
        # r_image: 方框标记后的图像, coords：标记框的属性值，上下左右像素位置
        r_image, reason_number, coords = yolo.detect_image(image)  # GH 这里添加返回值 检测人数 以判断有无人
        file.write('\n')
        # 保存检测图像到指定位置
        image_save_path = detect_image_path + 'result_' + portion[1]  # 为标记处理后图片路径
        print('detect result save to....:' + image_save_path)
        print('检测出{}人'.format(reason_number))  # GH 输出人数 对这里进行判断>0进行上传edge，进一步姿势识别openpose
        if reason_number > 0:
            r_image.save(image_save_path)  # 只有有人的图像才保存
            coords_list.append(coords)  # 将坐标信息加入列表当中
            filename_list.append(filename)  # 将文件名信息加入列表中

    # 记录总时间
    time_sum = time.time() - start_time
    file.write('time sum: ' + str(time_sum) + 's')
    print('time sum:', time_sum)
    yolo.close_session()

    # 返回需要记录的数据
    init_image_size = get_folder_size(init_image_path)
    detect_image_size = get_folder_size(detect_image_path)
    target_detect_time = time_sum
    return init_image_size, detect_image_size, target_detect_time, \
        filename_list, coords_list


# 切割
def cut_image(init_image_path, cut_image_path, filename_list, coords_list):
    """
    @param init_image_path: 初始数据集文件夹
    @param cut_image_path: 切割后仅含一人的数据集文件夹
    @param filename_list: 初始文件名列表（含人）
    @param coords_list: 坐标信息列表（含人）
    @return cut_image_size: 切割后仅含一人的数据集大小
    @return cut_image_time: 切割时间
    """
    start_time = time.time()
    # 同时遍历文件名列表和坐标信息列表
    for filename, coords in zip(filename_list, coords_list):
        image = Image.open(init_image_path + filename)  # 打开当前含有人的图片
        for coord in coords:
            # 对每个图片进行切割
            im_array = np.array(image).copy()
            im_array = im_array[coord[0]:coord[1], coord[2]:coord[3], :]
            img = Image.fromarray(im_array)
            id = coords.index(coord)  # 判断是一张图片的第几个人
            img.save(cut_image_path + filename[:-4] + '_' + str(id) + '.jpg')

    # 返回需要记录的数据
    cut_image_time = time.time() - start_time
    cut_image_size = get_folder_size(cut_image_path)
    return cut_image_size, cut_image_time


# 获取文件夹的大小，单位B
def get_folder_size(path):
    ls = os.listdir(path)
    size = 0
    for filename in ls:
        size += os.path.getsize(path+filename)
    return size


def main():
    with open(CSV_NAME, 'w', newline='') as f:
        f_log = csv.writer(f)  # 创建csv文件对象
        f_log.writerow([
            '初始数据集大小',
            '目标检测后的数据集大小',
            '切割后的数据集大小',
            '含人率',
            '平均含人率',
            '目标检测时间',
            '切割时间'
        ])
        # 第一步：目标检测
        init_image_size, detect_image_size, target_detect_time, \
            filename_list, coords_list = target_detect(INIT_IMAGE_PATH, DETECT_IMAGE_PATH)
        # 第二步：切割
        cut_image_size, cut_image_time = \
            cut_image(INIT_IMAGE_PATH, CUT_IMAGE_PATH, filename_list, coords_list)
        # 第三步：计算
        people_rate = detect_image_size / init_image_size  # 含人率
        avg_people_rate = cut_image_size / detect_image_size  # 平均含人率
        f_log.writerow([
            init_image_size,
            detect_image_size,
            cut_image_size,
            people_rate,
            avg_people_rate,
            target_detect_time,
            cut_image_time
        ])


def test():
    pass


if __name__ == '__main__':
    init_folder(INIT_IMAGE_PATH, True)
    init_folder(DETECT_IMAGE_PATH)
    init_folder(CUT_IMAGE_PATH)
    main()
    # test()
