from flask import request, jsonify, Blueprint
from app.services.services import call_external_api
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

