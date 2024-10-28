import pytest
from SuggestionAPI.app.services.data_fetcher import DataFetcher

class TestDataFetcher:
    def test_execute_query(self):
        fetcher = DataFetcher()
        result = fetcher.executeQuery("invalid_endpoint", "invalid_query")
        assert result == [], "Expected an empty list for an invalid query"

    def test_get_suggestions_empty(self):
        fetcher = DataFetcher()
        suggestions = fetcher.getSuggestions()
        assert suggestions == [], "Expected no suggestions before fetching data"
