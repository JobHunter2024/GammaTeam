from flask import request, jsonify, Blueprint
from app.services.services import call_external_api
from app.services.technologies_search_service import classify_technology
from app.utils.response_formatter import ResponseFormatter
from app.LogTools.logTools import log_aspect, exception_handling_aspect

main_bp = Blueprint('main', __name__)

EXTERNAL_SPARQL_API = "http://localhost:8888/query"
NAMESPACE = "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#"

formatter = ResponseFormatter(NAMESPACE)


@log_aspect
@exception_handling_aspect
@main_bp.route('/api/query', methods=['POST'])
def forward_data():
    data = request.get_json()
    if not data or 'iri' not in data:
        return jsonify({'error': 'IRI parameter is required in request body'}), 400

    iri = data['iri']

    response = call_external_api(EXTERNAL_SPARQL_API, NAMESPACE, iri)
    print(response)

    formatted_response = formatter.format_response(response)
    print(formatted_response)

    return jsonify(formatted_response)


@log_aspect
@exception_handling_aspect
@main_bp.route('/api/classify', methods=['POST'])
def classify_technology_endpoint():
    data = request.get_json()
    if not data or 'technology' not in data or 'job_offer_text' not in data:
        return jsonify({'error': 'Technology and job_offer_text are required in request body'}), 400

    technology_name = data['technology']
    job_offer_text = data['job_offer_text']

    result = classify_technology(technology_name, job_offer_text)

    if 'error' in result:
        return jsonify({'error': result['error']}), 500

    return jsonify(result)
