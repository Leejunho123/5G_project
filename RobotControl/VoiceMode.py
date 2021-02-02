import speech_recognition as sr
import CMDlist
import music
import motor
import sys
from subprocess import call
class VoiceMode:
    def __init__(self):
        self.r = sr.Recognizer()
        self.MUSIC=music.music()
        self.LeftHand=0
        self.RightHand=0
        self.TVcheck=0
        self.VMP()
       

    def VMP(self):
        try :
            while True:
                with sr.Microphone() as source:
                    self.r.adjust_for_ambient_noise(source)
                    print("speak")
                    audio_text = self.r.listen(source)
                    try:
                        print("loading")
                        self.r2=self.r.recognize_google(audio_text,language='ko-KR')
                        print(self.r2)
                        if self.r2 == CMDlist.VoiceCmd[0]:
                            if self.LeftHand==0:
                                motor.LeftHandup()
                                self.LeftHand=1
                            elif self.LeftHand==1:
                                motor.LeftHanddown()
                                self.LeftHand=0
                        if self.r2 == CMDlist.VoiceCmd[1]:
                            if self.RightHand==0:
                                motor.RightHandup()
                                self.RightHand=1
                            elif self.RightHand==1:
                                motor.RightHanddown()
                                self.RightHand=0
                        if self.r2 == CMDlist.VoiceCmd[2]:
                            motor.MoveFoward(5)
                        if self.r2 == CMDlist.VoiceCmd[3]:
                            motor.MoveBackward(5)
                        if self.r2 == CMDlist.VoiceCmd[4]:
                            motor.Greeting()
                        if self.r2 == CMDlist.VoiceCmd[5]:
                            motor.Cheerup()
                        if self.r2 == CMDlist.VoiceCmd[6]:
                            if self.TVcheck == 0:
                                call('irsend SEND_ONCE samsung KEY_POWER',shell=True)
                                self.TVcheck = 1
                            elif self.TVcheck ==1:
                                call('irsend SEND_ONCE samsung KEY_POWER',shell=True)
                                self.TVcheck = 0
                        if self.r2 == CMDlist.VoiceCmd[7]:
                            call('irsend SEND_ONCE samsung KEY_1 KEY_9 KEY_5 KEY_OK',shell=True)
                        if self.r2 == CMDlist.VoiceCmd[8]:
                            break
                        if self.r2 == CMDlist.VoiceCmd[9]:
                            self.MUSIC.stopmusic()
                        if self.r2 == CMDlist.VoiceCmd[10]:
                            self.MUSIC.playmusic(0)
                        if self.r2 == CMDlist.VoiceCmd[11]:
                            self.MUSIC.playmusic(1)
                        if self.r2 == CMDlist.VoiceCmd[12]:
                            self.MUSIC.playmusic(2)
                        if self.r2 == CMDlist.VoiceCmd[13]:
                            self.MUSIC.playmusic(3)
                        if self.r2 == CMDlist.VoiceCmd[14]:
                            print("불 켜")
                        if self.r2 == CMDlist.VoiceCmd[15]:
                            print("불 꺼")

                            
                    except:
                        print(sys.exc_info())
        


        except KeyboardInterrupt:
            print("finish")
    
        

if __name__ == "__main__":
    Voice = VoiceMode()
