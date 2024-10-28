from typing import List, Optional
from SuggestionAPI.app.models.suggestion_object import SuggestionObject


class ResponseFormatter:
    @staticmethod
    def formatResponse(suggestions):
        return {"suggestions": [s.__dict__ for s in suggestions]}
