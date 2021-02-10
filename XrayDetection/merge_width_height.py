import pandas as pd
import os, shutil
from tqdm.notebook import tqdm
import PIL


primary_dir = 'C:/Users/w/Desktop/coding/'
train_csv = pd.read_csv(primary_dir + 'train.csv')
wh = pd.read_csv(primary_dir + 'width_height.csv')
train_csv2 = pd.merge(train_csv, wh, on='image_id')
train_df = train_csv2.copy()
train_df.head()
train_df = train_df[train_df.class_id!=14].reset_index(drop = True)

train_df['x_min'] = train_df.apply(lambda row: (row.x_min)/row.width, axis =1)
train_df['y_min'] = train_df.apply(lambda row: (row.y_min)/row.height, axis =1)

train_df['x_max'] = train_df.apply(lambda row: (row.x_max)/row.width, axis =1)
train_df['y_max'] = train_df.apply(lambda row: (row.y_max)/row.height, axis =1)

train_df['x_mid'] = train_df.apply(lambda row: (row.x_max+row.x_min)/2, axis =1)
train_df['y_mid'] = train_df.apply(lambda row: (row.y_max+row.y_min)/2, axis =1)

train_df['w'] = train_df.apply(lambda row: (row.x_max-row.x_min), axis =1)
train_df['h'] = train_df.apply(lambda row: (row.y_max-row.y_min), axis =1)

train_df['area'] = train_df['w']*train_df['h']
print(train_df.head())

