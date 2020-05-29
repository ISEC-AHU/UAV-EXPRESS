# Author:Han
# @Time : 2020/1/10 20:27

from PIL import Image
import matplotlib.pyplot as plt
import numpy as np

def cut_image(image, top, bottom, left, right):
    """
    :param image: PIL image object, not numpy array
    :param top:
    :param bottom:
    :param left:
    :param right:
    :return: sub image object
    """
    im_array = np.array(image).copy()
    im_array = im_array[top:bottom, left:right, :]
    return Image.fromarray(im_array)

def main():
    image = Image.open('./test/000001.jpg')
    image
    new_image = cut_image(image, 0, 200, 100, 300)
    plt.figure()
    plt.imshow(new_image)
    plt.show()
    new_image = cut_image(image, 100, 200, 100, 300)
    plt.figure()
    plt.imshow(new_image)
    plt.show()

if __name__=='__main__':
    main()