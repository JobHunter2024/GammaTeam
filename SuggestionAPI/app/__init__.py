from flask import Flask, jsonify
from flasgger import Swagger
from app.routes.sparql_query_controller import sparql_query_bp
from app.routes.technology_controller import technology_bp
from app.exceptions.exception_handler import CustomException
from flask_cors import CORS


def create_app():
    app = Flask(__name__)
    CORS(app)

    swagger_config = {
        "headers": [],
        "specs": [
            {
                "endpoint": "apispec",
                "route": "/apispec.json",
                "rule_filter": lambda rule: True,
                "model_filter": lambda tag: True,
            }
        ],
        "static_url_path": "/flasgger_static",
        "swagger_ui": True,
        "specs_route": "/swagger/",
    }

    swagger_template = {
        "swagger": "2.0",
        "info": {
            "title": "Job Hunter API",
            "description": "API for classifying technologies based on job descriptions.",
            "version": "1.0.0",
        },
        "host": "localhost:5000",
        "basePath": "/",
    }

    Swagger(app, config=swagger_config, template=swagger_template)

    app.register_blueprint(sparql_query_bp)
    app.register_blueprint(technology_bp)

    @app.errorhandler(CustomException)
    def handle_custom_exception(error):
        response = jsonify({
            "error": error.message,
            "type": type(error).__name__,
            "status_code": error.status_code
        })
        response.status_code = error.status_code
        return response

    @app.errorhandler(Exception)
    def handle_general_exception(error):
        response = jsonify({
            "error": str(error),
            "type": type(error).__name__
        })
        response.status_code = 500
        return response

    return app
