## 로봇 제어
- 프로그램 설명
    + 라즈베리파이3에서 개발
    + AndroidControl : main 파일
        + TCP 통신을 통해 앱으로 로봇 제어
        + 음성인식 모드로 변경가능
        + 웹캠과 call를 이용 터미널 쉘 실행으로 웹에 웹캠 영상 전송
    + CMDlist : 명령어 파일
        + CMDread.txt : 명령어에 관한 간단한 설명
    + motor : 모터 제어 파일
        + 일반적인 움직임과 디테일한 모드 둘다 제어
    + music : music파일에 저장된 mp3 재생
    + RecordPlay : 녹음 재생
        + 앱으로 녹음된 mp3를 서버로 보낸 뒤 그 서버에서 라즈베리파이로 mp3 저장
        + 저장된 mp3를 재생
    + servoMotorModule : 웹캠이 설치된 모터 제어
        + 머리쪽 위/아래, 왼/오 서보모터 2개 제어
    + VoiceMode : 음성모드 변경
        + 음성으로 로봇 제어
        

