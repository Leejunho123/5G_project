import time
import os
import RPi.GPIO as GPIO
import threading
import requests
import json
from google.cloud import storage
from subprocess import call
import music
MUSIC=music.music()
os.environ["GOOGLE_APPLICATION_CREDENTIALS"] = "/home/pi/Downloads/robo-cc0c2-dcd11e1765d5.json"
storage_client = storage.Client()
bucket = storage_client.bucket("robo-cc0c2.appspot.com")
def RecordPlay():
    print("녹음 파일 다운로드 시작")
    blob2=bucket.blob("RecordPlay.mp3")
    destination_uri = '/home/pi/Desktop/robosapiens/{}'.format(blob2.name) 
    blob2.download_to_filename(destination_uri)
    print("녹음 파일 재생")
    call('cvlc /home/pi/Desktop/robosapiens/RecordPlay.mp3',shell=True)




    
