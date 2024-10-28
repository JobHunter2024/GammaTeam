import pytest
from SuggestionAPI.app.services.sparql_data_collector import SPARQLDataCollector

class TestSPARQLDataCollector:

    def test_query_data_invalid(self):
        collector = SPARQLDataCollector.get_instance()
        response = collector.queryData("invalid_endpoint", "invalid_query")
        assert response == [], "Expected an empty list for invalid query"