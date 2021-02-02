import RPi.GPIO as GPIO
import threading
import time

GPIO.setmode(GPIO.BCM)
GPIO.setwarnings(False)

class servoMotor:
    def __init__(self,pin,frequence):
        self.pin=pin
        self.frequence=frequence
        GPIO.setup(pin,GPIO.OUT)
        self.pwm=GPIO.PWM(self.pin,self.frequence)       
        self.check=0
        self.angle=50

    def work(self,angle):
        if angle > 180 or angle < 0:
            return False
        start = 2.5
        end = 12
        ratio = (end-start)/180
        angle = start + angle*ratio
        self.pwm.start(angle)
        time.sleep(0.1)
        self.pwm.ChangeDutyCycle(angle)
        time.sleep(0.1)
        self.pwm.ChangeDutyCycle(0)
 
   
    def workT(self,angle):
        threading.Thread(target=self.work,args=[angle]).start()
        
    def workTwhile(self,angle):
        while angle < 180:
            threading.Thread(target=self.work,args=[angle]).start()
            angle+=1
    

    def stop(self):
        self.pwm.ChangeDutyCycle(0)


    def WORK(self,angle):
        self.angle = angle
        while self.angle <100:
            self.pwm.start(angle)
            self.pwm.ChangeDutyCycle(self.angle)
            time.sleep(1)
            self.angle+=1
            if self.check == 1:
                self.pwm.ChangeDutyCycle(0)
                break

    
    def WORKleft(self,angle):
        self.angle = angle
        while self.angle >0:
            self.pwm.start(angle)
            self.pwm.ChangeDutyCycle(self.angle)
            time.sleep(1)
            self.angle-=1
            if self.check == 1:
                self.pwm.ChangeDutyCycle(0)
                break

    def getAngle(self):
        return self.angle
            


if __name__ == "__main__":    
    a = servoMotor(24,50)
    b = servoMotor(25,50)
    

    try:
        
            a.workT(0)
    
            
                 
          
           
        
            print("a")

    except KeyboardInterrupt:
        GPIO.cleanup()

    
