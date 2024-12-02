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
@main_bp.route('/api/query', methods=['GET'])
def forward_data():
    skill = request.args.get('skill')
    if not skill:
        return jsonify({'error': 'Skill parameter is required'}), 400

    response = call_external_api(EXTERNAL_SPARQL_API, NAMESPACE, skill)
    formatted_response = formatter.format_response(response)

    return jsonify(formatted_response)
