class TechnologyDTO:
    def __init__(self, entity_label: str, programmed_in: str, type_of_technology: str, wikidata_url: str):
        self.entity_label = entity_label
        self.programmed_in = programmed_in
        self.type_of_technology = type_of_technology
        self.wikidata_url = wikidata_url
