import pygame
import CMDlist
import speech_recognition as sr

class music:
    def __init__(self):
        self.music_cmd = CMDlist.musicPrint
        self.filename = ['music/babyshark.mp3', 'music/birthday.mp3', 'music/pororo.mp3', 'music/tongtongtong.mp3','RecordPlay.mp3']
        self.initMixer()
        self.r = sr.Recognizer()
    def playmusic(self,soundfile):
        pygame.init()
        pygame.mixer.init()
        self.clock= pygame.time.Clock()
        pygame.mixer.music.load(self.filename[soundfile])
        pygame.mixer.music.play()
        print(self.filename[soundfile])
        
    def stopmusic(self):
        print(self.music_cmd[4])
        pygame.mixer.music.stop()
    
    def getmixerargs(self):
        pygame.mixer.init()
        freq, size, chan= pygame.mixer.get_init()
        return freq, size, chan
    
    
    def initMixer(self):
        BUFFER = 3072  # audio buffer size, number of samples since pygame 1.8.
        FREQ, SIZE, CHAN= self.getmixerargs()
        pygame.mixer.init(FREQ, SIZE, CHAN,BUFFER)

if __name__ == "__main__":
               
    music=music()
    pass




                        
