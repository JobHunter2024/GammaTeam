from app.services.sparql_service import call_external_api, generate_suggestion_sparql_query, \
    check_active_technologies_sparql_query


def get_suggestions(EXTERNAL_SPARQL_API, NAMESPACE, technologies: list):
    suggestions = set()
    for technology in technologies:
        print(technology)
        response = call_external_api(EXTERNAL_SPARQL_API, NAMESPACE, technology,
                                     generate_suggestion_sparql_query)
        for suggestion in response.get("resultList", []):
            technology_iri = suggestion.get("node")
            response = call_external_api(EXTERNAL_SPARQL_API, NAMESPACE, technology_iri,
                                         check_active_technologies_sparql_query)
            print(technology_iri)
            print(response)

            if technology_iri and response.get("resultList") and len(response.get("resultList")) > 0:
                suggestions.add(technology_iri)

    return list(suggestions)
