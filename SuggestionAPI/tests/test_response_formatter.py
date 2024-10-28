import pytest
from SuggestionAPI.app.utils.response_formatter import ResponseFormatter
from SuggestionAPI.app.models.suggestion_object import SuggestionObject


class TestResponseFormatter:
    def test_format_response_empty(self):
        formatted_response = ResponseFormatter.formatResponse([])
        assert formatted_response == {"suggestions": []}, "Expected an empty response"

    def test_format_response_with_data(self):
        suggestions = [SuggestionObject("Python", "Beginner")]
        formatted_response = ResponseFormatter.formatResponse(suggestions)
        assert "suggestions" in formatted_response, "Expected suggestions key in response"
        assert len(formatted_response["suggestions"]) == 1, "Expected one suggestion in response"
