# 🏹 JobHunter


## 📔 Table of Contents

- [About the Project](#about-the-project)
- [Screenshots](#screenshots)
- [Tech Stack](#tech-stack)
- [Features](#features)
- [Environment Variables](#environment-variables)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Run Locally](#run-locally)
  - [Deployment](#deployment)
- [Usage](#usage)
- [Contact](#contact)
- [Acknowledgements](#acknowledgements)

---

## 🌟 About the Project

A web 3.0 (semantic web) base application implementing the conceptsof open data, linked data and HATEOAS with live data from job offer boards. The goal of the project is to offer users an intuitive tool that would allow for advanced querying, statistical charting as well as offer classification and archival in order to monitor the activity of the IT Market.

Our team was responsible of smart querying, suggestions, and query standardization to allow for intuitive API usage in the context of a web application.
---

## 📷 Screenshots

![Screenshot](https://your-screenshot-url.com)

---

## Resources (Deliverables)

* [Technical Report](https://docs.google.com/document/d/10gybDoKLvTFstlCbGf6qe2ru0_LxUlQknnEn_zBr910)
* OpenAPI Specification Directory
* Demo Video

## 👾 Tech Stack

**Frontend:**
- ⚛️ **React** - The main UI library.
- 🛣 **React Router** - Handles page navigation.
- 🎨 **Material UI** - Predefined UI components.
- 🎭 **Bootstrap & React Bootstrap** - Responsive styling and components.
- 🌐 **Axios** - HTTP request handling.
- ⚡ **Vite** - Fast development build tool.
- 🏗 **TypeScript** - Type-safe and scalable code.

**Back-end:**

- 🔥 **Apache Jena Fuseki** - A SPARQL server used to store, query, and manage RDF data for semantic reasoning.
- 🐍 **Flask** - Lightweight and efficient Python web framework for building APIs.
- 📜 **SPARQLWrapper** - Used for querying RDF data from **Wikidata** and other semantic sources.
- 📌 **SPARQL & RDF** - Used to retrieve and manipulate **semantic data** from the ontology.
- ⚙️ **Java & Spring Boot** - Provides a scalable backend service for managing and processing job-related data.g.
- 🗃 **PostgreSQL** - The relational database management system used for storing structured data.
- 📝 **Flask-Swagger** - API documentation and interactive testing.

---

## 🎯 Features

- Feature 1
  
- **SuggestionAPI**: The Suggestion API is a microservice that identifies technologies or skills related to a specified technology by leveraging SPARQL queries against a defined ontology. It retrieves both direct and intermediate relationships within two connection nodes. The API has been enhanced to include an active job validation step, ensuring that suggested technologies are relevant based on their association with currently available job offers. By filtering out technologies that are only linked to removed job offers, this API provides more accurate and market-relevant technology recommendations.
  
- **WikiLinkAPI**: This API retrieves information about a given technology or entity based on its ontology IRI. It queries a SPARQL endpoint to fetch the corresponding Wikidata URI, then retrieves additional metadata such as the official logo and Wikipedia link from Wikidata. If a Wikipedia link is found, it also fetches a short description of the entity from Wikipedia. This allows users to get relevant contextual information about technologies, programming languages, or other entities referenced in the ontology.

---

## 🧰 Getting Started

### Prerequisites

This project uses Node.js npm as package manager for the Frontend Part

* [Node.js Download Page](https://nodejs.org/en/download)

It also requires Java 17 JDK as well as a compatible version of Maven 3.9.9
 
* [Java 17 Archive Downloads](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
* [Maven Download Page](https://maven.apache.org/download.cgi)
* [Maven Configuration Guide](https://phoenixnap.com/kb/install-maven-windows)

Lastly we should install Python 3.11 as well as 3.12

* [Python 3.11 Download Page](https://www.python.org/downloads/release/python-3110/)
* [Python 3.12 Download Page](https://www.python.org/downloads/release/python-3120/)

### Installation


#### Configuring Frontend :

Clone the project from :

We recommend opening the cloned project root directory within Visual Studio Code and running in Command Line the following:

```bash
npm install
```

#### Java Backend APIs 
* Querying API (Gamma team Repository)
* Standard Queries API (Gamma Team Repository)
* Authentication API (Authentication API Repository)
* User Data Management API (User Data Management API Repository)

For all of the above one must clone the project and open the project via **IntelliJ Community Edition** (recommended).

For IntelliJ ensure that the Lombok IDE plugin is installed.

Afterwards we must add the application.properties in each of the services :

1. Querying API && Standard Queries API

```properties
server.port=8888
PAPERTRAIL_HOST = your_papertrail_host
PAPERTRAIL_PORT = your_papertrail_port
SPARQL_ENDPOINT = your_apache_fuseki_dataset_address
ONTOLOGY_URI_REGEX = http://www\.semanticweb\.org/ana/ontologies/[A-Za-z0-9/_#-.]+
FUSEKI_USERNAME = username_if_applicable
FUSEKI_PASSWORD = password_if_applicable
```

2. Authentication API && User Data Management API

```properties
spring.datasource.url=jdbc:postgresql://{address_of_your_postgres_db}:5432/JobHunter
spring.datasource.username=your_db_user
spring.datasource.password=your_db_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

jwt.secret=your_JWT_secret
jwt.expiration=3600000
jwt.issuer=jobhunter2024
```

#### Python Backend APIs


---

## 👀 Usage

Use this space to tell more about your project and how it can be used. Include additional screenshots, code samples, demos, or links to other resources.

```jsx
import Component from 'my-project';

function App() {
  return <Component />;
}
```

---

## 🤝 Contact

* Harton Amalia MSD1
* Habasescu Andrei MSD1

Project Link: [https://github.com/JobHunter2024/GammaTeam](https://github.com/JobHunter2024/GammaTeam)

---

## 💎 Acknowledgements

[Sabin Buraga WADE](https://profs.info.uaic.ro/sabin.buraga/teach/courses/wade/index.html)


[Iftene Adrian TAIP](https://edu.info.uaic.ro/tehnici-avansate-ingineria-programarii/)


[Markdown Template](https://github.com/Louis3797/awesome-readme-template)


[Alpha Team - Job Scraping](https://github.com/JobHunter2024/AlphaTeam)


[Beta Team - Data Processing](https://github.com/JobHunter2024/BetaTeam)
