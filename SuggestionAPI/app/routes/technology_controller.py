from flask import request, jsonify, Blueprint
from flasgger import swag_from
from app.services.technologies_search_service import classify_technology, classify_technologies
from app.utils.response_formatter import ResponseFormatter
from app.LogTools.logTools import log_aspect, exception_handling_aspect
from app.exceptions.exception_handler import BadRequestException, InternalServerErrorException

technology_bp = Blueprint('technology', __name__)

EXTERNAL_SPARQL_API = "http://localhost:8888/query"
NAMESPACE = "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#"

formatter = ResponseFormatter(NAMESPACE)


@log_aspect
@exception_handling_aspect
@technology_bp.route('/api/v1/technology/classify', methods=['POST'])
@swag_from({
    "tags": ["Technology Classification"],
    "parameters": [
        {
            "name": "body",
            "in": "body",
            "required": True,
            "schema": {
                "type": "object",
                "properties": {
                    "technology": {"type": "string", "example": "Java"},
                    "job_offer_text": {
                        "type": "string",
                        "example": "Looking for a Java developer to build scalable applications."
                    },
                },
            },
        }
    ],
    "responses": {
        200: {
            "description": "Classification result",
            "schema": {
                "type": "object",
                "properties": {
                    "entityLabel": {"type": "string", "example": "Java"},
                    "programmedIn": {"type": "string", "example": "Java"},
                    "typeOfTechnology": {"type": "string", "example": "ProgrammingLanguage"},
                    "wikidataUrl": {"type": "string", "example": "https://www.wikidata.org/wiki/Q251"},
                },
            },
        },
        400: {
            "description": "Invalid input",
            "schema": {"type": "object", "properties": {"error": {"type": "string"}}},
        },
        500: {
            "description": "Internal Server Error",
            "schema": {"type": "object", "properties": {"error": {"type": "string"}}},
        },
    },
})
def classify_technology_endpoint():
    data = request.get_json()
    if not data or 'technology' not in data or 'job_offer_text' not in data:
        raise BadRequestException("Technology and job_offer_text are required in request body")

    technology_name = data['technology']
    job_offer_text = data['job_offer_text']

    result = classify_technology(technology_name, job_offer_text)

    if 'error' in result:
        raise InternalServerErrorException(result['error'])

    return jsonify(result)


@log_aspect
@exception_handling_aspect
@technology_bp.route('/api/v1/technology/classify/multiple', methods=['POST'])
@swag_from({

    "tags": ["Technology Classification"],
    "parameters": [
        {
            "name": "body",
            "in": "body",
            "required": True,
            "schema": {
                "type": "object",
                "properties": {
                    "technologies": {
                        "type": "array",
                        "items": {"type": "string"},
                        "example": ["Java", "Python"]
                    },
                    "job_offer_text": {
                        "type": "string",
                        "example": "Looking for a Java developer to build scalable applications."
                    },
                },
            },
        }
    ],
    "responses": {
        200: {
            "description": "Classification result",
            "schema": {
                "type": "object",
                "properties": {
                    "entityLabel": {"type": "string", "example": "Java"},
                    "programmedIn": {"type": "string", "example": "Java"},
                    "typeOfTechnology": {"type": "string", "example": "ProgrammingLanguage"},
                    "wikidataUrl": {"type": "string", "example": "https://www.wikidata.org/wiki/Q251"},
                },
            },
        },
        400: {
            "description": "Invalid input",
            "schema": {"type": "object", "properties": {"error": {"type": "string"}}},
        },
        500: {
            "description": "Internal Server Error",
            "schema": {"type": "object", "properties": {"error": {"type": "string"}}},
        },
    },
})
def classify_technologies_endpoint():
    data = request.get_json()
    if not data or 'technologies' not in data or 'job_offer_text' not in data:
        raise BadRequestException("Technologies and job_offer_text are required in request body")

    technologies = data['technologies']
    job_offer_text = data['job_offer_text']

    result = classify_technologies(technologies, job_offer_text)

    if 'error' in result:
        raise InternalServerErrorException(result['error'])

    return jsonify(result)
