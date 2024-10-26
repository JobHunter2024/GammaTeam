from flask import Flask
from LogTools import logTools
app = Flask(__name__)

@app.route('/')
def hello_world():  # put application's code here
    logTools.logInfo("Hello World!")
    return 'Hello World!'


if __name__ == '__main__':
    app.run(debug=True)