import base64

import numpy as np
from bson import ObjectId
from flask import Flask, render_template, request, flash, redirect, url_for, session
from pymongo import MongoClient
from datetime import datetime
import random
import cv2
import requests
import json


app = Flask(__name__)
app.config['SECRET_KEY'] = 'secret_key'
filename = 'atlas_connect_info.txt'
filename2 = 'connect_info.txt'

class MyMongoClient(object):
    def __init__(self, collection):
        with open(filename, encoding='utf-8') as f:
            self.atlas_connection_info = f.read()
        self.client = MongoClient(self.atlas_connection_info)
        self.database = self.client["song-db"]
        self.collection = self.database[collection]


@app.route('/lol_list')
def lol_list():
    with open(filename, encoding='utf-8') as f:
        atlas_connection_info = f.read()
    client = MongoClient(atlas_connection_info)
    database = client["LOL"]
    stats = database["stats"]
    cursor = stats.find()
    count = stats.find().count()
    return render_template('lol_list.html', cursor=cursor, count=count)


@app.route('/weather', methods=['GET'])
def weather():
    city = 'daegu'
    appid = 'e5d4ba22d1c0aae4130753ea87c69eec'
    url = f'http://api.openweathermap.org/data/2.5/weather?q={city}&appid={appid}'
    res = requests.get(url)
    #weather_data = json.loads(res.text)
    weather_data = res.json()

    description = weather_data["weather"][0]["description"]
    icon = weather_data["weather"][1]["icon"]
    temp = weather_data["main"]["temp"]-273

    return render_template('weather.html',
                           description=description,
                           icon=icon,
                           temp=temp)


@app.route('/card', methods=['GET'])
def card():
    return render_template('card.html')


@app.route('/')
def home():
    if 'username' in session:
        message = session['username'] + '로그인 되었습니다'
    else:
        message = '로그인 되지 않았습니다'

    city = 'daegu'
    appid = 'e5d4ba22d1c0aae4130753ea87c69eec'
    url = f'http://api.openweathermap.org/data/2.5/weather?q={city}&appid={appid}'
    res = requests.get(url)
    # weather_data = json.loads(res.text)
    weather_data = res.json()

    description = weather_data["weather"][0]["description"]
    icon = weather_data["weather"][0]["icon"]
    temp = weather_data["main"]["temp"] - 273
    temp = round(temp, 1)
    return render_template('index.html', description=description,
                            icon=icon, temp=temp,
                           message=message
                           )


@app.route('/connect_info', methods=['GET', 'POST'])
def connect_info():
    if request.method == 'POST':
        atlas_connect_info = request.form['atlas_connect_info']
        host = request.form['host']
        port = request.form['port']
        print(atlas_connect_info)
        with open(filename2, 'r', encoding='utf-8') as f:
            atlas = f.readline().split('tlas=')[0]
            B = f.readline().split('=')[0]
        with open(filename2, 'w', encoding='utf-8') as f:
            f.write(atlas + 'tlas=' + atlas_connect_info + '\n' + B + '=' + host + ':' + port)
    with open(filename2, encoding='utf-8') as f :
        atlas = f.readline().split('tlas=')[1]
        A = f.readline().split('=')[1]
        host = A.split(':')[0]
        port = A.split(':')[1]
    return render_template('connect_info.html', atlas=atlas, host=host, port=port)


@app.route('/connect_info_test', methods=['POST'])
def connect_test():
    check = request.form['flexRadioDefault']
    if check == "1":
        with open(filename2, encoding='utf-8') as f:
            atlas = f.readline().split('tlas=')[1]
        try:
            clienttest = MongoClient(atlas)
            print(clienttest['song-db']['books'].find())
            result = "성공"
        except:
            result = "실패"
    else:
        with open(filename2, encoding='utf-8') as f:
            AAA = f.readline()
            local = f.readline().split('=')[1]
        try:
            clienttest = MongoClient(local)
            print(clienttest['song-db']['books'].find())
            result = '성공'
        except :
            result = '실패'
    return render_template('connect_info_test.html',result=result)


@app.route('/atlas_connect_info')
def atlas_connect_info():
    with open(filename, encoding='utf-8') as f:
        atlas_connection_info = f.read()
    return render_template('atlas_connect_info.html',
                           atlas_connection_info=atlas_connection_info)


@app.route('/atlas_connect_info_update', methods=['POST'])
def atlas_connect_info_update():
    atlas_connect_info = request.form['atlas_connect_info']
    with open(filename, 'w', encoding='utf-8') as f:
        f.write(atlas_connect_info)
    return render_template('atlas_connect_info_update_result.html')


@app.route('/login', methods=['GET', 'POST'])
def login():
    if request.method == 'GET':
        return render_template('login.html')
    else:
        username = request.form['username']
        password = request.form['password']
        myclient = MyMongoClient('users')
        user = myclient.collection.find_one({'username': username, 'password': password})
        print(user['username'])
        session['username'] = user['username']
        return render_template('index.html')



@app.route('/book_add', methods=['GET'])
def book_add():
    return render_template('book_add.html')



# POST 방법으로 받는다.
@app.route('/book_add_result', methods=['POST'])
def book_add_process():
    # client = MongoClient("mongodb://localhost:27017/")
    # database = client["song-db"]
    # collection = database["books"]
    myclient = MyMongoClient('books')
    title = request.form['title']
    file = request.files['photo']
    author = request.form['author']
    price = request.form['price']
    isbn = request.form['isbn']
    encoded_data = base64.b64encode(file.read())
    document = {'title': title, 'photo': encoded_data, 'author': author, 'price': price, 'created_date' : datetime.now(),
                'isbn': isbn}
    result = myclient.collection.insert_one(document)
    if result.inserted_id is not None:
        book_add_result = "정상 등록"
    else :
        book_add_result = "등록 실패"
    return render_template('book_add_result.html', book_add_result=book_add_result)


@app.route('/book_id_search', methods=['GET'])
def book_id_search():
    return render_template('book_id_search.html')


@app.route('/book_id_search_process', methods=['POST'])
def book_search_process():

    item = request.form['item_to_search']
    data = request.form['data_to_search']
    myclient = MyMongoClient('books')
    if item == 'id':
        query = {'_id': ObjectId(data)}
    elif item == 'title':
        query = {'title': data}
    books = myclient.collection.find_one(query)

    return render_template('book_id_search_result.html', books=books)


@app.route('/jiji', methods=['GET', 'POST'])
def jiji():
    jiji = ""
    if request.method == 'POST':
        year = int(request.form['year'])
        jiji_list = ['자', '축', '인', '묘', '진', '사', '오', '미', '신', '유', '술', '해']
        jiji_index = (year - 4) % 12
        jiji=jiji_list[jiji_index]
    return render_template('jiji.html', jiji=jiji)


@app.route('/game',methods=['GET'])
def game():
    my_list = ['1-1', '1-2', '2-1', '2-2', '3-1', '3-2', '4-1', '4-2', '5-1', '5-2',
     '6-1', '6-2', '7-1', '7-2', '8-1', '8-2', '9-1', '9-2', '10-1', '10-2']
    random.shuffle(my_list)
    selected_list = my_list[0:4]
    result = []
    my = (int(my_list[0][0])+int(my_list[1][0])) % 10
    result.append(my)
    com = (int(my_list[2][0])+int(my_list[3][0])) % 10
    result.append(com)
    if my>com:
        result.append('승리')
    elif my<com:
        result.append('패배')
    else :
        result.append('무승부')
    return render_template('game.html', list=selected_list, result=result)


@app.route('/book_list', methods=['GET', 'POST'])
def book_list():
    # client = MongoClient()
    # database = client["song-db"]
    # collection = database["books"]
    myclient = MyMongoClient('books')
    if request.method == 'POST':
        doc_id = request.form['doc_id']
        myclient.collection.find_one_and_delete({'_id': ObjectId(doc_id)})
    count = myclient.collection.find().count()
    cursor = myclient.collection.find()
    return render_template('book_list.html', cursor=cursor, count=count)


@app.route('/book_details/<_id>', methods=['GET'])
def book_details(_id):
    myclient = MyMongoClient('books')
    cursor = myclient.collection.find_one({'_id': ObjectId(_id)})
    return render_template('book_details.html', doc=cursor)


@app.route('/lotto', methods=['GET'])
def lotto():
    return render_template('lotto.html')


@app.route('/lotto_result', methods=['POST'])
def lotto_result():
    mylist = []
    mylist.append(int(request.form['1']))
    mylist.append(int(request.form['2']))
    mylist.append(int(request.form['3']))
    mylist.append(int(request.form['4']))
    mylist.append(int(request.form['5']))
    mylist.append(int(request.form['6']))
    result = []
    lottolist = np.arange(1, 46, 1).tolist()
    count = 0
    for i in range(6):
        random.shuffle(lottolist)
        result.append(lottolist[0])
        if lottolist[0] in mylist:
            count+=1
        del lottolist[0]
    return render_template('lotto_result.html', mylist=mylist, com=result, result=count)


@app.route('/book_replace', methods=['POST'])
def book_replace():
    doc_id = request.form['doc_id']
    myclient = MyMongoClient('books')
    doc = myclient.collection.find_one({'_id': ObjectId(doc_id)})

    return render_template('book_replace.html', doc=doc)


@app.route('/book_replace_result', methods=['POST'])
def book_replace_result():
    doc_id = request.form['doc_id']
    title = request.form['title']
    created_date = request.form['created_date']
    author = request.form['author']
    price = request.form['price']
    isbn = request.form['isbn']
    photocheck = request.form['photocheck']
    myclient = MyMongoClient('books')
    try:
        file = request.files['A_photo']
        encoded_data = base64.b64encode(file.read())
        replacing = {'title': title, 'created_date': created_date, 'author': author,
                     'price': price, 'isbn': isbn, 'photo': encoded_data}
        print('try')

    except:
        print('except')
        if photocheck == "1":
            check = myclient.collection.find_one({'_id': ObjectId(doc_id)})
            photo = check['photo']
            replacing = {'title': title, 'created_date': created_date, 'author': author,
                         'price': price, 'isbn': isbn, 'photo': photo}
        else:
            replacing = {'title': title, 'created_date': created_date, 'author': author,
                         'price': price, 'isbn': isbn}

    myclient.collection.find_one_and_replace({'_id': ObjectId(doc_id)}, replacing)
    return render_template('book_replace_result.html')


if __name__ == '__main__':
    app.run(host='0.0.0.0')
