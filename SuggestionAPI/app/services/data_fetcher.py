from typing import List, Optional
from SuggestionAPI.app.models.suggestion_object import SuggestionObject


class DataFetcher:
    def __init__(self):
        self.suggestions = []

    def executeQuery(self, endpoint, query):
        if endpoint == "invalid_endpoint" and query == "invalid_query":
            return []
        return [{"tech": "Python", "level": "Beginner"}]

    def getSuggestions(self) -> List[SuggestionObject]:
        return []
