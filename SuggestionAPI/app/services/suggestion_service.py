from app.services.sparql_service import call_external_api, generate_suggestion_sparql_query, \
    check_active_technologies_sparql_query


def get_suggestions(EXTERNAL_SPARQL_API, NAMESPACE, technologies: list):
    suggestions = dict()

    for technology in technologies:
        technology_related_skills = dict()

        response = call_external_api(EXTERNAL_SPARQL_API, NAMESPACE, technology,
                                     generate_suggestion_sparql_query)

        suggestion_data = suggestions.get(technology, {})

        for suggestion in response.get("resultList", []):
            technology_iri = suggestion.get("node")
            relation_type = suggestion.get("relation", "unknownRelation")
            intermediate_relation = suggestion.get("relationIntermediate", relation_type)

            response = call_external_api(EXTERNAL_SPARQL_API, NAMESPACE, technology_iri,
                                         check_active_technologies_sparql_query)

            if technology_iri and response.get("resultList") and len(response.get("resultList")) > 0:
                if suggestion_data.get(technology_iri) is not None:
                    technology_iri_dict = suggestion_data.get(technology_iri)
                    relations_list = technology_iri_dict.get("relations")
                    if intermediate_relation not in relations_list:
                        relations_list.append(intermediate_relation)
                    technology_iri_dict["relations"] = relations_list
                    suggestion_data[technology_iri] = technology_iri_dict
                else:
                    suggestion_data[technology_iri] = {
                        "relations": [relation_type]
                    }

        technology_related_skills[technology] = suggestion_data
        suggestions.update(technology_related_skills)

    return suggestions
