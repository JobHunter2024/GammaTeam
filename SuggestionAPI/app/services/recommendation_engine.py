from typing import List, Optional
from SuggestionAPI.app.services.data_fetcher import DataFetcher
from SuggestionAPI.app.models.suggestion_object import SuggestionFactory


class RecommendationEngine:
    def __init__(self, userProfile):
        self.userProfile = userProfile
        self.dataFetcher = DataFetcher()

    def generateSuggestions(self):
        return []

    def processSuggestions(self, suggestions):
        return [{"technology": s.get("tech"), "level": s.get("level")} for s in suggestions]
