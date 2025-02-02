import requests
from app.exceptions.exception_handler import BadRequestException


def call_external_api(endpoint, namespace, iri, query):
    try:
        response = requests.post(endpoint, json={
            "namespace": namespace,
            "query": query(iri, namespace)
        }, headers={
            "Accept": "application/sparql-results+json",
            "Content-Type": "application/json"
        })

        response.raise_for_status()
        return response.json()
    except requests.RequestException as e:
        raise BadRequestException(str(e))


def check_active_technologies_sparql_query(technology_iri, namespace):
    return f"""
        PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>

        SELECT DISTINCT ?job
        WHERE {{
            ?job <http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#requiresSkill> 
                 <{technology_iri}> .

            FILTER NOT EXISTS {{
                ?job <http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#dateRemoved> ?dateRemoved .
            }}
        }}
        LIMIT 1
    """


def generate_suggestion_sparql_query(iri, namespace):
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
            BIND(?relation AS ?relationIntermediate)  # If direct, relation and intermediateRelation are the same
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

