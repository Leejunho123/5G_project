import RPi.GPIO as GPIO
import time
import threading
GPIO.setwarnings(False)
GPIO.setmode(GPIO.BCM)


RIGHT_FORWARD = 5
RIGHT_BACKWARD = 6
RIGHT_PWM = 0
LEFT_FORWARD = 27
LEFT_BACKWARD = 17
LEFT_PWM = 22
M_PWM=1
M_F=8
M_B=7

RS_F=19
RS_B=13
RS_PWM=26
LS_F=20
LS_B=16
LS_PWM=21
#lArm_F=
#lArm_B=
#lArm_PWM=
#rArm_F=
#rArm_B=
#rArm_PWM=

#GPIO.setup(lArm_F,GPIO.OUT)
#GPIO.setup(lArm_B,GPIO.OUT)
#GPIO.setup(lArm_PWM,GPIO.OUT)
#lArm_MOTOR = GPIO.PWM(lArm_PWM,300)
#lArm_MOTOR.start(0)
#lArm_MOTOR.ChangeDutyCycle(0)

#GPIO.setup(rArm_F,GPIO.OUT)
#GPIO.setup(rArm_B,GPIO.OUT)
#GPIO.setup(rArm_PWM,GPIO.OUT)
#rArm_MOTOR = GPIO.PWM(rArm_PWM,300)
#rArm_MOTOR.start(0)
#rArm_MOTOR.ChangeDutyCycle(0)


GPIO.setup(RS_F,GPIO.OUT)
GPIO.setup(RS_B,GPIO.OUT)
GPIO.setup(RS_PWM,GPIO.OUT)
RS_MOTOR = GPIO.PWM(RS_PWM,300)
RS_MOTOR.start(0)
RS_MOTOR.ChangeDutyCycle(0)
GPIO.setup(LS_F,GPIO.OUT)
GPIO.setup(LS_B,GPIO.OUT)
GPIO.setup(LS_PWM,GPIO.OUT)
LS_MOTOR = GPIO.PWM(LS_PWM,300)
LS_MOTOR.start(0)
LS_MOTOR.ChangeDutyCycle(0)

GPIO.setup(M_F,GPIO.OUT)
GPIO.setup(M_B,GPIO.OUT)
GPIO.setup(M_PWM,GPIO.OUT)
M_MOTOR = GPIO.PWM(M_PWM,300)
M_MOTOR.start(0)
M_MOTOR.ChangeDutyCycle(0)

GPIO.setup(RIGHT_FORWARD,GPIO.OUT)
GPIO.setup(RIGHT_BACKWARD,GPIO.OUT)
GPIO.setup(RIGHT_PWM,GPIO.OUT)
RIGHT_MOTOR = GPIO.PWM(RIGHT_PWM,300)
RIGHT_MOTOR.start(0)
RIGHT_MOTOR.ChangeDutyCycle(0)
GPIO.setup(LEFT_FORWARD,GPIO.OUT)
GPIO.setup(LEFT_BACKWARD,GPIO.OUT)
GPIO.setup(LEFT_PWM,GPIO.OUT)
GPIO.output(LEFT_PWM,0)
LEFT_MOTOR = GPIO.PWM(LEFT_PWM,300)
LEFT_MOTOR.start(0)
LEFT_MOTOR.ChangeDutyCycle(0)



def RIGHTMotor(forward, backward, pwm):
    GPIO.output(RIGHT_FORWARD,forward)
    GPIO.output(RIGHT_BACKWARD,backward)
    RIGHT_MOTOR.ChangeDutyCycle(pwm)
def LEFTMotor(forward, backward, pwm):
    GPIO.output(LEFT_FORWARD,forward)
    GPIO.output(LEFT_BACKWARD,backward)
    LEFT_MOTOR.ChangeDutyCycle(pwm)
def MMotor(forward, backward, pwm):
    GPIO.output(M_F,forward)
    GPIO.output(M_B,backward)
    M_MOTOR.ChangeDutyCycle(pwm)
def RSMotor(forward, backward, pwm):
    GPIO.output(RS_F,forward)
    GPIO.output(RS_B,backward)
    RS_MOTOR.ChangeDutyCycle(pwm)
def LSMotor(forward, backward, pwm):
    GPIO.output(LS_F,forward)
    GPIO.output(LS_B,backward)
    LS_MOTOR.ChangeDutyCycle(pwm)
#def lAMotor(forward, backward, pwm):
#    GPIO.output(lArm_F,forward)
#    GPIO.output(lArm_B,backward)
#    lArm_MOTOR.ChangeDutyCycle(pwm)
#def rAMotor(forward, backward, pwm):
#    GPIO.output(rArm_F,forward)
#    GPIO.output(rArm_B,backward)
#    rArm_MOTOR.ChangeDutyCycle(pwm)

    

def STOP():
    threading.Thread(target=RIGHTMotor(0,0,0)).start()
    threading.Thread(target=LEFTMotor(0,0,0)).start()
    threading.Thread(target=MMotor(0,0,0)).start()
    threading.Thread(target=RSMotor(0,0,0)).start()
    threading.Thread(target=LSMotor(0,0,0)).start()

#앞으로 걷기
def MoveFoward(count):
    while count!=0:
        threading.Thread(target=LEFTMotor(1,0,100)).start()
        threading.Thread(target=RIGHTMotor(0,1,100)).start()
        threading.Thread(target=MMotor(1,0,100)).start()
        time.sleep(0.5)        
        threading.Thread(target=LEFTMotor(0,1,100)).start()
        threading.Thread(target=RIGHTMotor(1,0,100)).start()
        threading.Thread(target=MMotor(0,1,100)).start()
        time.sleep(0.5)
        count=count-1
    STOP()

#뒤로 걷기
def MoveBackward(count):
    while count!=0:
        threading.Thread(target=RIGHTMotor(1,0,100)).start()
        threading.Thread(target=LEFTMotor(0,1,100)).start()
        threading.Thread(target=MMotor(1,0,100)).start()
        threading.Thread(target=RSMotor(0,1,100)).start()
        threading.Thread(target=LSMotor(0,1,100)).start()
        time.sleep(0.6)
        threading.Thread(target=RIGHTMotor(0,1,100)).start()
        threading.Thread(target=LEFTMotor(1,0,100)).start()
        threading.Thread(target=MMotor(0,1,100)).start()
        threading.Thread(target=RSMotor(1,0,100)).start()
        threading.Thread(target=LSMotor(1,0,100)).start()
        time.sleep(0.6)
        count=count-1
    STOP()

#상체 왼쪽 이동
def Left():
    MMotor(1,0,100)
    time.sleep(1)
    STOP()

#상체 왼쪽 이동
def Right():
    MMotor(0,1,100)
    time.sleep(1)
    STOP()

#왼손 앞으로 들기
def LeftHandup():
    LSMotor(1,0,100)
    time.sleep(1.5)
    STOP()

#오른손 앞으로 들기
def RightHandup():
    RSMotor(1,0,100)
    time.sleep(1.5)
    STOP()

#왼손 뒤로 돌기
def LeftHanddown():
    LSMotor(0,1,100)
    time.sleep(1)
    STOP()
    
#오른손 뒤로 돌기
def RightHanddown():
    RSMotor(0,1,100)
    time.sleep(1)
    STOP()

def CLEAN():
    GPIO.cleanup()


def Greeting():
    threading.Thread(target=RSMotor(1,0,100)).start()
    time.sleep(1.6)
    threading.Thread(target=RSMotor(0,0,0)).start()
    for i in range(2):
        threading.Thread(target=MMotor(1,0,100)).start()
        time.sleep(1)
        threading.Thread(target=MMotor(0,1,100)).start()
        time.sleep(0.5)
    threading.Thread(target=RSMotor(0,1,100)).start()
    time.sleep(1.2)
    STOP()

def Cheerup():
    threading.Thread(target=LSMotor(1,0,100)).start()
    threading.Thread(target=RSMotor(1,0,100)).start()
    time.sleep(1.8)
    LEFTMotor(0,0,0)
    RSMotor(0,0,0)
    for i in range(3):
        threading.Thread(target=MMotor(1,0,100)).start()
        time.sleep(0.3)
        threading.Thread(target=MMotor(0,1,100)).start()
        time.sleep(0.3)
    threading.Thread(target=RSMotor(0,1,100)).start()
    threading.Thread(target=LSMotor(0,1,100)).start()
    time.sleep(1)
    
def RSup():
    RSMotor(1,0,100)

def RSdown():
    RSMotor(0,1,100)
 
def LSup():
    LSMotor(1,0,100)
    
def LSdown():
    LSMotor(0,1,100)
   
def Left2():
    MMotor(1,0,100)
   
def Right2():
    MMotor(0,1,100)


    
if __name__ == '__main__':
    try:
        Left()
        time.sleep(0.5)
        Right()
        time.sleep(0.5)
    except KeyboardInterrupt:
        GPIO.cleanup()
    GPIO.cleanup()
