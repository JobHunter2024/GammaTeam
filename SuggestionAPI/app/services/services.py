import requests


def call_external_api(endpoint, namespace, skill):
    try:
        data = {
            "namespace": namespace,
            "query": generate_sparql_query(skill, namespace)
        }
        response = requests.post(endpoint, json=data)
        response.raise_for_status()

        return response.json()
    except requests.RequestException as e:
        return {'error': str(e)}


def generate_sparql_query(skill, namespace):
    return f"""
    PREFIX : <{namespace}>
    SELECT ?relatedSkill ?relation
    WHERE {{
      {{
        :{skill} ?relation ?relatedSkill .
      }}
      UNION
      {{
        ?relatedSkill ?relation :{skill} .
      }}
    }}
    """
