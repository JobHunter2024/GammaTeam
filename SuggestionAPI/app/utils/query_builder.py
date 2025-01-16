class QueryBuilder:
    @staticmethod
    def programming_language_query(technology_name):
        return f"""
    SELECT DISTINCT ?entity
    WHERE {{
      ?entity rdfs:label|skos:altLabel "{technology_name}"@en .
      ?entity wdt:P31/wdt:P279* wd:Q9143 .  # Programming Language
    }}
    LIMIT 1
    """

    @staticmethod
    def general_technology_query(technology_name):
        return f"""
    SELECT DISTINCT
      ?entity ?entityLabel ?type ?typeLabel
      ?programmingLanguage ?programmingLanguageLabel
    WHERE {{
      ?entity rdfs:label|skos:altLabel ?entityLabel .
      ?entity wdt:P31 ?type .
      ?entity wdt:P277 ?programmingLanguage .

      FILTER(
        CONTAINS(LCASE(?entityLabel), LCASE("{technology_name}")) &&
        LANG(?entityLabel) = "en"
      )

      VALUES ?type {{
        wd:Q271680   # Software Framework
        wd:Q1172284  # Software Library
        wd:Q188860   # Software Library
        wd:Q7397     # Software
        wd:Q1639024  # Mathematical Software
        wd:Q1330336  # Web Framework
      }}

      SERVICE wikibase:label {{ bd:serviceParam wikibase:language "en". }}
    }}
    LIMIT 30
    """
