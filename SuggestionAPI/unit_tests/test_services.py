import requests
from app.services.sparql_service import call_external_api, generate_sparql_query


def test_call_external_api_success(mocker):
    """Testam functia call_external_api pentru un răspuns de succes."""
    endpoint = "http://localhost:8888/query"
    namespace = "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#"
    iri = "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#Spring"

    mock_post = mocker.patch("requests.post")
    mock_response = mocker.Mock()
    mock_response.status_code = 200
    mock_response.json.return_value = {"results": "mocked_response"}
    mock_post.return_value = mock_response

    response = call_external_api(endpoint, namespace, iri)

    mock_post.assert_called_once_with(
        endpoint,
        json={
            "namespace": namespace,
            "query": generate_sparql_query(iri, namespace)
        },
        headers={
            "Accept": "application/sparql-results+json",
            "Content-Type": "application/json"
        }
    )

    assert response == {"results": "mocked_response"}


def test_call_external_api_failure(mocker):
    """Testam functia call_external_api pentru un raspuns cu eroare."""
    endpoint = "http://localhost:8888/query"
    namespace = "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#"
    iri = "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#Spring"

    mocker.patch("requests.post", side_effect=requests.RequestException("Server Error"))

    response = call_external_api(endpoint, namespace, iri)

    assert "error" in response
    assert "Server Error" in response["error"]

def test_generate_sparql_query():
    """Testam functia generate_sparql_query pentru corectitudinea query-ului."""
    iri = "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#Spring"
    namespace = "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#"

    expected_query = f"""
    PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>

    SELECT DISTINCT ?node ?relation ?intermediateNode ?relationIntermediate
    WHERE {{
      {{
        ?node ?relation <{iri}> .
        ?node a ?type .
        ?type rdfs:subClassOf* <{namespace}TechnicalSkill> .
        BIND(<{iri}> AS ?origin)
        BIND(?node AS ?intermediateNode)
        BIND(?relation AS ?relationIntermediate)
      }}
      UNION
      {{
        ?intermediateNode ?relationIntermediate <{iri}> .
        ?intermediateNode a ?typeIntermediate .
        ?typeIntermediate rdfs:subClassOf* <{namespace}TechnicalSkill> .
        ?node ?relation ?intermediateNode .
        ?node a ?type .
        ?type rdfs:subClassOf* <{namespace}TechnicalSkill> .
        FILTER(?node != <{iri}>) 
      }}
    }}
    """

    query = generate_sparql_query(iri, namespace)

    assert query.strip() == expected_query.strip()
