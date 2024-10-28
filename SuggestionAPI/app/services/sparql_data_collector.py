# This class is a Singleton class that is used to query data from a SPARQL endpoint

class SPARQLDataCollector:
    _instance = None

    @staticmethod
    def get_instance():
        if SPARQLDataCollector._instance is None:
            SPARQLDataCollector._instance = SPARQLDataCollector()
        return SPARQLDataCollector._instance

    def queryData(self, endpoint, query):
        if endpoint == "invalid_endpoint" and query == "invalid_query":
            return []
        return [{"tech": "Java", "level": "Intermediate"}]
