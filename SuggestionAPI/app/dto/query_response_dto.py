class QueryResponseDTO:
    def __init__(self, relatedSkill: str, relation: str, intermediateRelatedSkill: str, intermediateRelation: str, resourceUri: str):
        self.relatedSkill = relatedSkill
        self.relation = relation
        self.intermediateRelatedSkill = intermediateRelatedSkill
        self.intermediateRelation = intermediateRelation
        self.resourceUri = resourceUri
