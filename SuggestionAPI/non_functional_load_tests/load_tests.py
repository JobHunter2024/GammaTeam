from locust import HttpUser, task, between


class APITestUser(HttpUser):
    wait_time = between(1, 2)

    @task
    def test_post_query(self):
        headers = {"Content-Type": "application/json"}
        payload = {
            "iri": "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#Spring"
        }

        with self.client.post("/api/query", json=payload, headers=headers, catch_response=True) as response:
            if response.status_code == 200:
                response.success()
            else:
                response.failure(f"Request failed! Status: {response.status_code}, Response: {response.text}")
