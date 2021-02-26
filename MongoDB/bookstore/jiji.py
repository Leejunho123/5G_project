from flask import Flask, render_template, request



app = Flask(__name__)


@app.route('/jiji', methods=['GET', 'POST'])
def jiji():
    jiji = ""
    if request.method == 'POST':
        year = int(request.form['year'])
        jiji_list = ['자', '축', '인', '묘', '진', '사', '오', '미', '신', '유', '술', '해']
        jiji_index = (year - 4) % 12
        jiji=jiji_list[jiji_index]
    return render_template('jiji.html', jiji=jiji)

