from app.dto.query_response_dto import QueryResponseDTO


class ResponseFormatter:
    def __init__(self, namespace):
        self.namespace = namespace

    def simplify_uri(self, uri):
        if uri.startswith(self.namespace):
            return uri.replace(self.namespace, "")
        elif uri.startswith("http://www.w3.org/1999/02/22-rdf-syntax-ns#"):
            return uri.replace("http://www.w3.org/1999/02/22-rdf-syntax-ns#", "rdf:")
        elif uri.startswith("http://www.w3.org/2002/07/owl#"):
            return uri.replace("http://www.w3.org/2002/07/owl#", "owl:")
        return uri

    def format_response(self, api_response):
        formatted_results = []
        for result in api_response.get("resultList", []):
            query_response = QueryResponseDTO(
                relatedSkill=self.simplify_uri(result.get("node", "")),
                relation=self.simplify_uri(result.get("relation", "")),
                intermediateRelatedSkill=self.simplify_uri(result.get("intermediateNode", "")),
                intermediateRelation=self.simplify_uri(result.get("relationIntermediate", ""))
            )
            formatted_results.append(query_response.__dict__)
        return formatted_results
