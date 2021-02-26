## VinBigData Chest X-ray Abnormalities Detection

시작 날짜 : 2020, 12, 30 / 2021, 02, 08
종료 날짜 : 2021, 03, 30 / 2021, 02, 26

+ 1차 발표 pdf : https://github.com/Leejunho123/5G_project/blob/main/XrayDetection/X_ray_detection_1%EC%B0%A8%EB%B0%9C%ED%91%9C.pdf
+ 최종 발표 pdf : 
+ Overview
    + 팔이 부러졌을 때 방사선 전문의들이 도움을 준다. 의사들은 CT, PET 스캔, MRI, X-ray 와 같은 기법으로 진단하고 치료한다. 하지만 흉부 엑스레이 쪽에서 힘든 부분이 있다. 최고의 의사들도 흉부 엑스레이 해석이 힘들 수 있고, 오진을 할 수 있다. 컴퓨터 지원 감지 및 진단 시스템(CADe/CADx)는 의사들의 부담을 줄이고 열악한 지역의 진단 품질을 개선하는 데 도움이 될 것이다.
    + 기존의 흉부 엑스레이 해석하는 방법은 소견 목록으로 분류한다. 현재 이미지에는 이상점의 위치에 대한 정보가 없으므로 다른 결과가 초래할 수 있다. 의사들에게 의미있는 지원을 하기 위해 엑스레이에 소견을 표시하기 위한 해결책이 필요하다.
    + 2018년 8월에 설립되어 Vinggroup JSC의 자금 지원을 받는 Vinggroup 빅데이터 연구소(VinBig Data Institute)는 근본적인 연구를 촉진하고 새로운 기술 및 적용 가능성이 높은 기술을 조사하는 것을 목표로 한다. 이 연구소는 전산 생물 의학, 자연어 처리, 컴퓨터 비전, 의료 이미지 처리 등 데이터 과학과 인공지능의 핵심 분야에 초점을 맞추고 있다. VinBig Data의 의료 영상팀은 의료 데이터의 수집, 처리, 분석 및 이해에 관한 연구를 수행한다. 그들은 효과적인 임상 작업 흐름을 용이하게 하기 위해 인공 지능의 최신 발전을 바탕으로 대규모의 고정밀 의료 영상 솔루션을 구축하기 위해 노력하고 있다.
    + 이 대회에서 흉부 엑스레이 촬영에서 14가지 흉부 이상을 자동으로 표시하고 분류한다. 숙련된 방사선사가 주석을 단 18000개의 이미지 데이터를 사용할 수 있다. 15000의 이미지로 모델을 교육하고 3000의 이미지로 테스트를 한다. 주석들은 VinBigData의 웹 기반 플랫폼 VinLab으로 수집되었다. 데이터 구축에 대한 자세한 내용은 "VinDr-CXR: 방사선 전문의 주석이 포함된 흉부 X선의 오픈 데이터 세트"에서 확인할 수 있다.
    + 성공하면 전문의에게 유용한 제2의 의견을 제시할 수 있다. 자동으로 소견을 정확하게 식별하고 위치를 지정해주는 시스템은 의사의 스트레스를 해소하고 정확한 진단을 제공할 수 있다.

+ Data
    + 총 200GB의 dicom 이미지
    + Train : 15000
    + Test : 3000
    + 평가 방법 : mAP
    + 실제 SubMission에 들어가야할 것:
    + ID , 증상번호, 사진에 box위치(xmin ymin xmax ymax)

    + ex : ID,TARGET
        - 004f33259ee4aef671c2b95d54e4be68,14 1 0 0 1 1
        - 004f33259ee4aef671c2b95d54e4be69,11 0.5 100 100 200 200 13 0.7 10 10 20 20
        etc.

+ 목표 : object detection and classification problem
    + 예측할 때 bounding box 와 증상을 예측해야한다.  
    + 만약 증상이 없다면 예측 값을 14 1 0 0 1 1 로 만들어야한다.

+ 증상
    + 0 - Aortic enlargement
    + 1 - Atelectasis
    + 2 - Calcification
    + 3 - Cardiomegaly
    + 4 - Consolidation
    + 5 - ILD
    + 6 - Infiltration
    + 7 - Lung Opacity
    + 8 - Nodule/Mass
    + 9 - Other lesion
    + 10 - Pleural effusion
    + 11 - Pleural thickening
    + 12 - Pneumothorax
    + 13 - Pulmonary fibrosis
    + 14 - No finding

+ train_data
    + image_id : 이미지 id
    + class_name : 실제 증상 이름, 없으면 No finding
    + class_id : 증상들의 id
    + rad_id : 실제 찍은 방사선사의 ID
    + x_min : x_min
    + y_min : y_min
    + x_max : x_max
    + y_max : y_max


+ 주 업무
    + Yolov5 modeling
    + EfficientNet 2-class classification


## git 설명
- 
    + dicom.py
        + 기본 dicom 파일 jpg로 변환 및 라벨링 

    + merge_width_hight.py
        + width, hight를 합친 train_csv2 생성
    
## Yolov5
+ Yolo.ipynb
    + Yolov5s
    + 1024x1024 / train 80% val 20% / --img 832 --batch 16 --epochs 30 / --img 640 --conf 0.15 --iou 0.5 / 0.024
+ Yolo2.ipynb
    + Yolov5l
    + 1024x1024 / train 80% val 20% / --img 640 --batch 16 --epochs 200 / --img 640 --conf 0.15 --iou 0.4 / 0.048

+ Yolo3.ipynb
    + Yolov5s
    + 5-Fold 방식
    + 1024x1024 / 5-Fold train 80% val 20% / --img 640 --batch 16 --epochs 40 X5 / --img 640 --conf 0.15 --iou 0.4 / 0.098 

+ Yolov4.ipynb
    + Yolov5s
    + 5-Fold 방식
    + 1024x1024 / 5-Fold train 80% val 20% / --img 832 --batch 16 --epochs 40 X5 / --img 832 --conf 0.2 --iou 0.4 / 0.126
    + 1024x1024 / 5-Fold train 80% val 20% / --img 832 --batch 16 --epochs 40 X5 / --img 832 --conf 0.4 --iou 0.4 / 0.113
    + 1024x1024 / 5-Fold train 80% val 20% / --img 832 --batch 16 --epochs 40 X5 / --img 832 --conf 0.15 --iou 0.4 / 0.13


+ Yolo5.ipynb
    + Yolov5m
    + 1024x1024 / train 80% val 20% / --img 1024 --batch 16 --epochs 50 / --img 1024 --conf 0.15 --iou 0.4 / 0.135
    + 1024x1024 / train 80% val 20% / --img 1024 --batch 16 --epochs 50 / --img 1024 --conf 0.2 --iou 0.5 / 0.133
    + 1024x1024 / train 80% val 20% / --img 1024 --batch 16 --epochs 50 / --img 1024 --conf 0.1 --iou 0.5 / 0.133
    + 1024x1024 / train 80% val 20% / --img 1024 --batch 16 --epochs 50 / --img 1024 --conf 0.15 --iou 0.7 / 0.137
    + test 시간 4분 29초

+ Yolo6.ipynb
    + Yolo5 + 2-class Efficientnet
    + 최고치 0.185



+ 진행한 Yolo parameter
    + 이미지 / 전처리 / train / detect / result
    + 1024x1024 / train 80% val 20% / --img 832 --batch 16 --epochs 30 / --img 640 --conf 0.15 --iou 0.5 / 0.024
    + 1024x1024 / train 80% val 20% / --img 640 --batch 16 --epochs 200 / --img 640 --conf 0.15 --iou 0.4 / 0.048
    + 1024x1024 / 5-Fold train 80% val 20% / --img 640 --batch 16 --epochs 40 X5 / --img 640 --conf 0.15 --iou 0.4 / 0.098 
    + 1024x1024 / 5-Fold train 80% val 20% / --img 832 --batch 16 --epochs 40 X5 / --img 832 --conf 0.2 --iou 0.4 / 0.126
    + 1024x1024 / 5-Fold train 80% val 20% / --img 832 --batch 16 --epochs 40 X5 / --img 832 --conf 0.4 --iou 0.4 / 0.113
    + 1024x1024 / 5-Fold train 80% val 20% / --img 832 --batch 16 --epochs 40 X5 / --img 832 --conf 0.15 --iou 0.4 / 0.13
    + 1024x1024 / train 80% val 20% / --img 1024 --batch 16 --epochs 50 / --img 1024 --conf 0.15 --iou 0.4 / 0.135
    + 1024x1024 / train 80% val 20% / --img 1024 --batch 16 --epochs 50 / --img 1024 --conf 0.2 --iou 0.5 / 0.133
    + 1024x1024 / train 80% val 20% / --img 1024 --batch 16 --epochs 50 / --img 1024 --conf 0.1 --iou 0.5 / 0.133
    + 1024x1024 / train 80% val 20% / --img 1024 --batch 16 --epochs 50 / --img 1024 --conf 0.15 --iou 0.7 / 0.137

## 2_class Efficientnet 

+ classes-2.ipynb
    + Efficientnet
    + Accuracy : 0.9


## Team github and trello 자세한 내용
+ https://trello.com/b/bUIob804/kaggle
+ https://github.com/X-Ray-Project/main