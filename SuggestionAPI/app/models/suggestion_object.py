from typing import List, Optional

# SuggestionAPI/app/models/suggestion_object.py

# This class uses the Factory design pattern to create a SuggestionObject instances
# with different configurations based on the technology and user's skill level

class SuggestionObject:
    def __init__(self, technology, relationToSkills, learningLinks=None, relevantSources=None):
        self.technology = technology
        self.relationToSkills = relationToSkills
        self.learningLinks = learningLinks
        self.relevantSources = relevantSources

class SuggestionFactory:
    @staticmethod
    def create_suggestion(technology, relationToSkills, learningLinks=None, relevantSources=None):
        return SuggestionObject(technology, relationToSkills, learningLinks, relevantSources)
