from flask import request, jsonify, Blueprint
from flasgger import swag_from
from app.services.sparql_service import call_external_api, generate_suggestion_sparql_query
from app.services.suggestion_service import get_suggestions
from app.utils.response_formatter import ResponseFormatter
from app.LogTools.logTools import log_aspect, exception_handling_aspect
from app.exceptions.exception_handler import BadRequestException

sparql_query_bp = Blueprint('sparql_query', __name__)

EXTERNAL_SPARQL_API = "http://localhost:8887/api/v1/query"
NAMESPACE = "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#"

formatter = ResponseFormatter(NAMESPACE)


@log_aspect
@exception_handling_aspect
@sparql_query_bp.route('/api/v1/sparql/query', methods=['POST'])
@swag_from({
    "tags": ["SPARQL Queries"],
    "parameters": [
        {
            "name": "body",
            "in": "body",
            "required": True,
            "schema": {
                "type": "object",
                "properties": {
                    "iri": {"type": "string", "example": "http://example.org/iri"}
                },
            },
        }
    ],
    "responses": {
        200: {
            "description": "Query response formatted",
            "schema": {"type": "object"},
        },
        400: {
            "description": "Invalid input",
            "schema": {"type": "object", "properties": {"error": {"type": "string"}}},
        },
    },
})
def forward_data():
    data = request.get_json()
    if not data or 'iri' not in data:
        raise BadRequestException("IRI parameter is required in request body")

    iri = data['iri']

    response = call_external_api(EXTERNAL_SPARQL_API, NAMESPACE, iri, generate_suggestion_sparql_query)

    formatted_response = formatter.format_response(response)

    return jsonify(formatted_response)


@sparql_query_bp.route('/api/v1/sparql/suggestions', methods=['POST'])
@swag_from({
    "tags": ["Technology Suggestions"],
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
                        "example": [
                            "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#Python",
                            "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#Java"
                        ]
                    }
                }
            }
        }
    ],
    "responses": {
        200: {
            "description": "List of suggested technologies based on input.",
            "schema": {
                "type": "object",
                "properties": {
                    "technologies": {
                        "type": "array",
                        "items": {"type": "string"},
                        "example": [
                            "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#Python",
                            "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#Java"
                        ]
                    }
                }
            }
        },
        400: {
            "description": "Invalid request, missing 'technologies' parameter",
            "schema": {
                "type": "object",
                "properties": {
                    "error": {"type": "string", "example": "Technologies parameter is required in request body"}
                }
            }
        }
    }
})
def get_technologies_classification():
    data = request.get_json()
    if not data or 'technologies' not in data:
        raise BadRequestException("Technologies parameter is required in request body")

    technologies = data.get('technologies', [])
    response = get_suggestions(EXTERNAL_SPARQL_API, NAMESPACE, technologies)

    return jsonify(response)
