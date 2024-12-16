import requests


def call_external_api(endpoint, namespace, iri):
    try:
        response = requests.post(endpoint, json={
            "namespace": namespace,
            "query": generate_sparql_query(iri, namespace)
        }, headers={
            "Accept": "application/sparql-results+json",
            "Content-Type": "application/json"
        })

        response.raise_for_status()

        return response.json()
    except requests.RequestException as e:
        return {'error': str(e)}


def generate_sparql_query(iri, namespace):
    return f"""
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
