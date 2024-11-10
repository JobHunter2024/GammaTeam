from flask import Flask
from testare.logTools import log_aspect, exception_handling_aspect

app = Flask(__name__)


@app.route('/')
@log_aspect
@exception_handling_aspect
def hello_world():
    return 'Hello World!'


if __name__ == '__main__':
    app.run(debug=True)
