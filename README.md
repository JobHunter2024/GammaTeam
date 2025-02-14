# 🏹 JobHunter 2025

\#wade \#infoiasi \#project \#web

## 📔 Table of Contents

- [About the Project](#about-the-project)

  
- [Resources](#resources)
- [Tech Stack](#tech-stack)
- [Features](#features)
- [Environment Variables](#environment-variables)
- [Getting Started](#getting-started) 
- [Installation](#installation)
- [Usage](#usage)
- [Contact](#contact)
- [Acknowledgements](#acknowledgements)

---

## 🌟 About the Project

A web 3.0 (semantic web) base application implementing the conceptsof open data, linked data and HATEOAS with live data from job offer boards. The goal of the project is to offer users an intuitive tool that would allow for advanced querying, statistical charting as well as offer classification and archival in order to monitor the activity of the IT Market.
This **#project** is part of a larger collaborative initiative that we built together with our colleagues for the **#Wade** class at Alexandru Ioan Cuza University of Iași **(#infoiasi)**. As part of our **#Web Development** course, we combined our skills and ideas to create a cutting-edge **#web** application that addresses real-world challenges in the job listing domain. 

**Our team was responsible of smart querying, suggestions, and query standardization to allow for intuitive API usage in the context of a web application.**

---

## Resources (Deliverables)

* [Technical Report](https://docs.google.com/document/d/10gybDoKLvTFstlCbGf6qe2ru0_LxUlQknnEn_zBr910)
* [OpenAPI Specification Directory](https://drive.google.com/drive/folders/1uSS05h5fiN9k7Mkg6fygrdGnVegllIV5)
* [Demo Video](https://youtu.be/rRL88SmhZLc)

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

### Harton Amalia
  
- **Suggestion API**: The Suggestion API is a microservice that identifies technologies or skills related to a specified technology by leveraging SPARQL queries against a defined ontology. It retrieves both direct and intermediate relationships within two connection nodes. The API has been enhanced to include an active job validation step, ensuring that suggested technologies are relevant based on their association with currently available job offers. By filtering out technologies that are only linked to removed job offers, this API provides more accurate and market-relevant technology recommendations.
  
- **Wiki Link API**: This API retrieves information about a given technology or entity based on its ontology IRI. It queries a SPARQL endpoint to fetch the corresponding Wikidata URI, then retrieves additional metadata such as the official logo and Wikipedia link from Wikidata. If a Wikipedia link is found, it also fetches a short description of the entity from Wikipedia. This allows users to get relevant contextual information about technologies, programming languages, or other entities referenced in the ontology.

### Habasescu Andrei

- **Standard Queries API**: An API which houses a set of generic and preset queries that help retrieve the details of any entity from the SPARQL endpoint of our Apache Fuseki server, which in turn contains an ontology in RDF format.

- **Querying API**: An API which dynamically generates SPARQL queries based on a specific Data Object structure which is intended to allow users to build their own queries with the help of out frontend implementation.

- **Authentication API**: Standard user entity management API offering a CRUD as well as proper authentication methods Register/Login. The API works based on secret signed JWT tokens, the API itself exerting Role Based Access Control via the Java Security Filter Chain.

- **User Data Management API**: API meant to allow manipulation of user data, such as favourite technologies or companies, mainly used for frontend interaction enrichment.
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
* [Querying API (Gamma team Repository)](https://github.com/JobHunter2024/GammaTeam)
* [Standard Queries API (Gamma Team Repository)](https://github.com/JobHunter2024/GammaTeam)
* [Authentication API (Authentication API Repository)](https://github.com/JobHunter2024/AuthenticationAPI)
* [User Data Management API (User Data Management API Repository)](https://github.com/JobHunter2024/UserDataManagementAPI)

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

* Suggestion API (Gamma team Repository)
* WikiLink API ( WikiLinkAPI Repository)

For all of the above one must clone the project and open the project via **PyCharm Community Edition** (recommended).

Afterwards we must add the application.properties in each of the service:

1. Suggestion API && Wiki Link API

```properties
PAPERTRAIL_HOST = your_papertrail_host
PAPERTRAIL_PORT = your_papertrail_port
WIKIDATA_ENDPOINT=https://query.wikidata.org/sparql
```

# Python Virtual Environment Setup

This guide explains how to create a virtual environment using **Python 3.11**, install dependencies from a `requirements.txt` file, and verify the installation.

---

## 🔧 **Prerequisites**
Ensure that you have **Python 3.11** installed. Check your Python version by running:

```bash
python3 --version
```

If you do not have Python 3.11 installed, download it from [python.org](https://www.python.org/downloads/).

---

## 🚀 **Step 1: Create a Virtual Environment**
To create a virtual environment, run the following command:

```bash
python3.11 -m venv venv
```

This will create a directory named `venv` containing the virtual environment files.

---

## ⚡ **Step 2: Activate the Virtual Environment**
After creating the virtual environment, activate it:

- **Windows (Command Prompt / PowerShell):**
  ```powershell
  venv\Scripts\activate
  ```
- **macOS/Linux:**
  ```bash
  source venv/bin/activate
  ```

Once activated, you should see `(venv)` at the beginning of your terminal prompt.

---

## 📦 **Step 3: Install Dependencies**

### 📌 **Option 1: Using an Existing `requirements.txt` File**
If you already have a `requirements.txt` file, install the dependencies with:

```bash
pip install -r requirements.txt
```

### 📌 **Option 2: Creating a `requirements.txt` File and Installing**
If you don’t have a `requirements.txt` file, create one using the following command:

```bash
echo "aniso8601==9.0.1
blinker==1.8.2
click==8.1.7
colorama==0.4.6
Flask==3.0.3
Flask-RESTful==0.3.10
Flask-SQLAlchemy==3.1.1
greenlet==3.1.1
iniconfig==2.0.0
itsdangerous==2.2.0
Jinja2==3.1.4
MarkupSafe==3.0.2
packaging==24.1
pluggy==1.5.0
pytest==8.3.3
python-dotenv==1.0.1
pytz==2024.2
six==1.16.0
SQLAlchemy==2.0.36
typing_extensions==4.12.2
Werkzeug==3.0.6
transformers
sentence-transformers
torch
python-decouple
flask-cors
flasgger
aspectlib
sparqlwrapper" > requirements.txt
```

Then install the dependencies:

```bash
pip install -r requirements.txt
```

---

## ✅ **Step 4: Verify Installation**
To check if all dependencies were installed correctly, run:

```bash
pip freeze
```

This will list all installed packages and their versions.

---

## 👀 Usage

In order to test our application one must run the following APIs

* React Frontend
* Authentication API
* User Data Management API
* Standard Queries API
* Queries API
* Suggestions API
* Wiki Link API

Ensure that Fuseki Server is correctly configured and that you have supplied a valid PostgreSQL server address.

---

## 🤝 Contact

* Harton Amalia MSD1 - [hartonamalia99@yahoo.com](mailto:hartonamalia99@yahoo.com)
* Habasescu Andrei MSD1 - [habasescuandrei1234@gmail.com](mailto:habasescuandrei1234@gmail.com) 

Project Link: [https://github.com/JobHunter2024/GammaTeam](https://github.com/JobHunter2024/GammaTeam)

---

## 💎 Acknowledgements

[Sabin Buraga WADE](https://profs.info.uaic.ro/sabin.buraga/teach/courses/wade/index.html)


[Iftene Adrian TAIP](https://edu.info.uaic.ro/tehnici-avansate-ingineria-programarii/)


[Markdown Template](https://github.com/Louis3797/awesome-readme-template)


[Alpha Team - Job Scraping](https://github.com/JobHunter2024/AlphaTeam)


[Beta Team - Data Processing](https://github.com/JobHunter2024/BetaTeam)
