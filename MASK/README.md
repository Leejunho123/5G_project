## yolov3를 이용한 마스크 착용/미착용 여부 판단
- 프로그램 설명
    + 기간 : 2주
    + 참여인원 : 2
    + 팀 내 업무 : image labeling, modeling (2인이라 전반적인 업무를 동시에 함)
    + 사용 언어
        + Python3 version : 3.7.3
        + yolov3 coding and training : google colab, darknet
        + other : visual studio code
        + 라이브러리 : cv2, numpy
    + 실행 순서 및 세부 설명
        + 실행 순서
            + 시작프로그램에 auto_start.bat을 이동 및 MASK_result/MASK 경로 설정
            + 컴퓨터 부팅 시 MASK_result/MASK.py 실행
        + maskyolo : train data 를 이용하여 yolov3 모델 모델링
            + 4000회 train
            + loss 0.0475
        + MASK_result/MASK : 실제 실행 파일
            + 마스크를 올바르게 착용 시 컴퓨터 사용가능
            + 마스크 미착용 시 unmask.jpg가 모니터 화면에 출력 컴퓨터 사용 불가
    + pdf : https://github.com/Leejunho123/project/blob/main/MASK/MOFsystem.pdf
