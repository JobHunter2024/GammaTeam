from SPARQLWrapper import SPARQLWrapper, JSON
from sentence_transformers import SentenceTransformer, util

WIKIDATA_ENDPOINT = "https://query.wikidata.org/sparql"
MODEL_NAME = "sentence-transformers/all-MiniLM-L6-v2"
model = SentenceTransformer(MODEL_NAME)


def wikidata_query(technology_name: str):
    technology_name_formatted = technology_name.strip().lower().capitalize()

    programming_language_query = f"""
    SELECT DISTINCT ?entity
    WHERE {{
      ?entity rdfs:label|skos:altLabel "{technology_name_formatted}"@en .
      ?entity wdt:P31/wdt:P279* wd:Q9143 .  # Programming Language
    }}
    LIMIT 1
    """

    sparql = SPARQLWrapper(WIKIDATA_ENDPOINT)
    sparql.setQuery(programming_language_query)
    sparql.setReturnFormat(JSON)

    try:
        result = sparql.query().convert()
        bindings = result['results']['bindings']

        if bindings:
            entity_uri = bindings[0]['entity']['value']
            entity_id = entity_uri.split("/")[-1]
            return True, {
                "entityLabel": technology_name.capitalize(),
                "programmedIn": technology_name.capitalize(),
                "typeOfTechnology": "ProgrammingLanguage",
                "wikidataUrl": f"https://www.wikidata.org/wiki/{entity_id}"
            }
    except Exception as e:
        print(f"Warning: Failed to query programming language. Defaulting to software. {str(e)}")

    return False, f"""
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


def process_results(results):
    bindings = results["results"]["bindings"]
    if not bindings:
        return []

    entity_map = {}

    for row in bindings:
        entity_label = row.get("entityLabel", {}).get("value", "")
        type_label = row.get("typeLabel", {}).get("value", "")
        entity_uri = row.get("entity", {}).get("value", "")
        programming_language = row.get("programmingLanguageLabel", {}).get("value", "N/A")

        entity_id = entity_uri.split("/")[-1] if entity_uri else "N/A"

        if entity_label not in entity_map:
            entity_map[entity_label] = {
                "entityLabel": entity_label,
                "typeLabel": type_label,
                "languages": set(),
                "wikidataUrl": f"https://www.wikidata.org/wiki/{entity_id}"
            }
        if programming_language != "N/A":
            entity_map[entity_label]["languages"].add(programming_language)
    return list(entity_map.values())


def pick_language_for_entity(entity, job_description):
    print("entity", entity)
    if not entity["languages"]:
        return "N/A"

    job_lower = job_description.lower()
    possible_langs = list(entity["languages"])


    job_embedding = model.encode(job_description, convert_to_tensor=True)
    langs_embedding = model.encode(possible_langs, convert_to_tensor=True)

    cos_scores = util.pytorch_cos_sim(job_embedding, langs_embedding)[0]
    best_idx = cos_scores.argmax().item()

    return possible_langs[best_idx]


def select_best_entity(entities, job_description, tech_name):
    candidate_texts = []
    for ent in entities:
        candidate_str = (
            f"I am looking for the best match for the technology {tech_name} in the following job description:\n"
            f"{job_description}\n\n"
            f"Candidate: {ent['entityLabel']} ({ent['typeLabel']}). "
            f"Languages: {', '.join(sorted(ent['languages'])) if ent['languages'] else 'N/A'}.\n"
        )
        candidate_texts.append(candidate_str)

    if not candidate_texts:
        return None, None

    for e in entities:
        print(e)

    embeddings = model.encode([job_description] + candidate_texts, convert_to_tensor=True)
    job_embedding = embeddings[0]
    option_embeddings = embeddings[1:]
    scores = util.pytorch_cos_sim(job_embedding, option_embeddings)[0]
    best_idx = scores.argmax().item()

    return entities[best_idx], candidate_texts[best_idx]


def classify_type(type_label):
    tl = type_label.lower()
    if "framework" in tl:
        return "Framework"
    elif "library" in tl:
        return "Library"
    elif "programming language" in tl:
        return "ProgrammingLanguage"
    else:
        return "Software"


def classify_technology(technology_name, job_offer_text):
    sparql = SPARQLWrapper(WIKIDATA_ENDPOINT)
    is_programming_language, result = wikidata_query(technology_name)
    if is_programming_language:
        return result
    else:
        sparql.setQuery(result)
        sparql.setReturnFormat(JSON)

        try:
            results = sparql.query().convert()
        except Exception as e:
            return {"error": f"Failed to result WikiData: {str(e)}"}

        entities = process_results(results)

        if not entities:
            return {"error": "No relevant technology found"}

        best_entity, _ = select_best_entity(entities, job_offer_text, technology_name)
        if best_entity is None:
            return {"error": "No best entity could be determined"}

        chosen_lang = pick_language_for_entity(best_entity, job_offer_text)

        return {
            "entityLabel": best_entity["entityLabel"],
            "programmedIn": chosen_lang,
            "typeOfTechnology": classify_type(best_entity["typeLabel"]),
            "wikidataUrl": best_entity["wikidataUrl"]
        }
