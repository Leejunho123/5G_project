import motor
import CMDlist
from socket import *
from time import ctime
import RPi.GPIO as GPIO
import threading
import sys
from subprocess import call
import VoiceMode
import music
import RecordPlay
from servoMotorModule import servoMotor


def WEBCAM():
    call('sudo systemctl stop motion', shell=True)
    call('mjpg_streamer -i "input_uvc.so -d /dev/video0" -o "output_http.so -p 8090 -w /usr/local/share/mjpg-streamer/www/"', shell=True)

threading.Thread(target=WEBCAM).start()
MUSIC=music.music()
ctrCmd = CMDlist.ctrCmd
HOST = ''
PORT = 21569
BUFSIZE = 1024
ADDR = (HOST,PORT)

tcpSerSock = socket(AF_INET, SOCK_STREAM)
tcpSerSock.bind(ADDR)
tcpSerSock.listen(5)
voicecheck=0
lrMotor=servoMotor(12,50)
udMotor=servoMotor(11,50)


def STOPING():
    data = ''
    data = tcpCliSock.recv(BUFSIZE)
    if data == ctrCmd[1]:
        motor.STOP()
while True:
        print('Waiting for connection')
        tcpCliSock,addr = tcpSerSock.accept()
        print('...connected from :', addr)
        try:
            while True:
                data = ''
                data = tcpCliSock.recv(BUFSIZE)
                data = data.decode('utf-8')
                print(data)
                if not data:
                    break
                if data == ctrCmd[0]:
                    motor.CLEAN()
                    call("sudo killall mjpg_streamer", shell=True) 
                    sys.exit()
                if data == ctrCmd[1]:
                    motor.LeftHandup()
                if data == ctrCmd[2]:
                    motor.LeftHanddown()
                if data == ctrCmd[3]:
                    motor.RightHandup()
                if data == ctrCmd[4]:
                    motor.RightHanddown()
                if data == ctrCmd[5]:
                    motor.Left()
                if data == ctrCmd[6]:
                    motor.Right()
                if data == ctrCmd[7]:
                    motor.MoveFoward(5)
                if data == ctrCmd[8]:
                    motor.MoveBackward(5)
                if data == ctrCmd[9]:
                    motor.Greeting()
                if data == ctrCmd[10]:
                    print("led on")
                if data == ctrCmd[11]:
                    print("led off")
                if data == ctrCmd[12]:
                    motor.Cheerup()
                if data == ctrCmd[13]:
                    motor.LSup()
                if data == ctrCmd[14]:
                    motor.LSdown()
                if data == ctrCmd[15]:
                    motor.RSup()
                if data == ctrCmd[16]:
                    motor.RSdown()
                if data == ctrCmd[17]:
                    motor.Left2()
                if data == ctrCmd[18]:
                    motor.Right2()
                if data == ctrCmd[19]:
                    motor.STOP()
                if data == ctrCmd[20]:
                    VM = threading.Thread(target=VoiceMode.VoiceMode)
                    VM.start()
                if data == ctrCmd[21]:
                    MUSIC.playmusic(0)
                if data == ctrCmd[22]:
                    MUSIC.stopmusic()
                if data == ctrCmd[23]:
                    MUSIC.playmusic(1)
                if data == ctrCmd[24]:
                    MUSIC.playmusic(2)
                if data == ctrCmd[25]:
                    MUSIC.playmusic(3)
                if data == ctrCmd[26]:
                    RP = threading.Thread(target=RecordPlay.RecordPlay)
                    RP.start()
                if ctrCmd[27] in data:
                    data2=data.split("T")
                    for i in data2[1]:
                        call('irsend SEND_ONCE samsung KEY_{}'.format(i),shell=True)
                    call('irsend SEND_ONCE samsung KEY_OK',shell=True) 
                if data==ctrCmd[28]:
                    call('irsend SEND_ONCE samsung KEY_POWER',shell=True)
                if data==ctrCmd[29]:
                    call('irsend SEND_ONCE samsung KEY_POWER',shell=True)
                if ctrCmd[30] in data:
                    print("W")
                    data = data.split()
                    try:
                        lrMotor.workT(int(data[1]))
                    except:
                        print("신호 불량")
                if ctrCmd[31] in data:
                    print("X")
                    data = data.split()
                    try:
                        udMotor.workT(int(data[1])) 
                    except:
                        print("신호 불량")
                        
        except KeyboardInterrupt:
                GPIO.cleanup()
                call("sudo killall mjpg_streamer", shell=True)
call("sudo killall mjpg_streamer", shell=True) 
tcpSerSock.close();
