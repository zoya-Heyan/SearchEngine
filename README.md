# MySearchEngine

A simple search engine built with Java, Spring Boot, and MySQL, implementing the core ideas of information retrieval including tokenization, inverted index, and keyword-based search with a web interface.



 Features
•	Document storage using MySQL
•	Custom tokenizer (supports Chinese & English text)
•	Inverted index (word → document → frequency)
•	Keyword-based search
•	Highlighted search results
•	Search history (recent queries)
•	Web interface for searching and adding documents



 Architecture Overview
```
Browser
↓
Spring Boot (Controller)
↓
Search Engine Logic
↓
MySQL (Document & Inverted Index)
```

Project Structure

```
src/main/java/com/searchengine/websearch
├── controller   # Web controllers
├── dao          # Database access
├── db           # DB utilities
├── index        # Inverted index builder
├── search       # Search logic
├── util         # Tokenizer
```

Tech Stack
•	Java 17
•	Spring Boot
•	Thymeleaf
•	MySQL
•	JDBC

How to Run
1.	Configure database in application.properties

```
spring.datasource.url=jdbc:mysql://localhost:3306/search_engine
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
```

2.  Run WebsearchApplication.java
3.  Open browser:

http://localhost:8080
