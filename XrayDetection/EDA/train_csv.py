import pandas as pd
import numpy as np
from matplotlib import pyplot as plt
primary_dir = 'C:/Users/w/Desktop/coding/'
train_csv = pd.read_csv(primary_dir +'train.csv')

# class_id = pd.Series(train_csv['class_id'], dtype='category')
# n, bins, patches = plt.hist(class_id, range=(-0.5, 14.5), bins=15, alpha=0.5)
# print(class_id.dtype)
# print(n)
# plt.show()
# ci2 = class_id[class_id != 14]
# print(ci2)
#
# n, bins, patches = plt.hist(class_id, range=(-0.5, 13.5), bins=14, alpha=0.5)
# print(n)
# plt.show()
#
# class_name = pd.Series(train_csv['class_name'], dtype='category')
# print(class_name.unique())

image_id = pd.Series(train_csv['image_id'], dtype='category')
print(image_id)
desc = image_id.describe()
print(desc)
print(sum(image_id=='03e6ecfa6f6fb33dfeac6ca4f9b459c9'))