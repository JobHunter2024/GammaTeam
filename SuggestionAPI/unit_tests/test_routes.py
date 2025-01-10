import pytest
from flask import Flask
from app.routes.routes import main_bp
from app.services.sparql_service import call_external_api


@pytest.fixture
def client(mocker):
    """Setup Flask test client and mock external API."""
    app = Flask(__name__)
    app.register_blueprint(main_bp)

    mocker.patch("app.services.services.call_external_api", return_value={"results": "mocked_response"})

    mock_formatter = mocker.patch("app.utils.response_formatter.ResponseFormatter.format_response")
    mock_formatter.return_value = {"formatted": "response"}

    with app.test_client() as client:
        yield client


def test_forward_data_success(client):
    """Test /api/query cand IRI este valid."""
    iri = "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#Spring"

    response = client.post('/api/query', json={"iri": iri})

    assert response.status_code == 200
    json_data = response.get_json()
    assert json_data == {"formatted": "response"}


def test_forward_data_missing_iri(client):
    """Test /api/query cand lipseste IRI din request body."""
    response = client.post('/api/query', json={})

    assert response.status_code == 400
    json_data = response.get_json()
    assert json_data == {'error': 'IRI parameter is required in request body'}


def test_forward_data_mock_call(client, mocker):
    """Testam ca functia call_external_api este apelata corect."""
    iri = "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#Spring"

    mock_call_api = mocker.patch("app.routes.routes.call_external_api", return_value={"mocked": "response"})

    response = client.post('/api/query', json={"iri": iri})

    mock_call_api.assert_called_once_with(
        "http://localhost:8888/query",
        "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#",
        iri
    )

    assert response.status_code == 200
    json_data = response.get_json()
    assert json_data == {"formatted": "response"}
