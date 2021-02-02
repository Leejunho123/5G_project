## yolov3를 이용한 마스크 착용/미착용 여부 판단
- 프로그램 설명
    + 시작프로그램에 auto_start.bat을 이동
    + 컴퓨터 부팅 시 MASK.py 실행
    + maskyolo : train data 를 이용하여 yolov3 모델 모델링
        + 4000회 train
        + loss 0.058, 0.037
    + MASK_result/MASK : 실제 실행 파일
        + 마스크를 올바르게 착용 시 컴퓨터 사용가능
        + 마스크 미착용 시 unmask.jpg가 모니터 화면에 출력 컴퓨터 사용 불가