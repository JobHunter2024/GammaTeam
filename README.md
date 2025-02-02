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

A smart web application that helps users track IT job market trends by aggregating job offers, classifying them semantically, and providing insights based on current demands.

---

## 📷 Screenshots

![Screenshot](https://your-screenshot-url.com)

---

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

## 🔑 Environment Variables

To run this project, you will need to add the following environment variables to your `.env` file:

```env
API_KEY=your-api-key
ANOTHER_API_KEY=another-key
```

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


Install my-project with npm:

```bash
npm install my-project
cd my-project
```

### Run Locally

Clone the project:

```bash
git clone https://github.com/JobHunter2024/GammaTeam.git
```

Navigate to the project directory:

```bash
cd my-project
```

Install dependencies:

```bash
npm install
```

Start the server:

```bash
npm run dev
```

### Deployment

To deploy this project:

```bash
npm run deploy
```

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

