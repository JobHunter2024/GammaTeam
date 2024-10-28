import pytest
from SuggestionAPI.app.services.recommendation_engine import RecommendationEngine


class TestRecommendationEngine:
    def test_generate_suggestions_empty(self):
        engine = RecommendationEngine(userProfile=None)
        suggestions = engine.generateSuggestions()
        assert isinstance(suggestions, list), "Expected a list of suggestions"
        assert len(suggestions) == 0, "Expected no suggestions for empty user profile"

    def test_process_suggestions_format(self):
        engine = RecommendationEngine(userProfile="dummy_profile")
        raw_suggestions = [{"tech": "Python", "level": "Beginner"}]
        processed_suggestions = engine.processSuggestions(raw_suggestions)
        assert isinstance(processed_suggestions, list), "Expected a list of processed suggestions"
