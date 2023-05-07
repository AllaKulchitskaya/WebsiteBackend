# Teamwork with Java.
# Development of the online store website's backend according to the given frontend

## Description of the project:
ADS-ONLINE is a resale online-platform. The frontend part of the project has already been developed, so the main object of this graduate work is to create the backend part.
The project has to meet the following requirements:
- User authorization and authentication.
- Assigning a role to the website's users: USER or ADMINISTRATOR (the task of increased complexity).
- CRUD-queries for ads on the website: the administrator can delete or edit all ads, while users can only edit their own.
- Users can leave comments for all the ads.
- Users can search for ads by the title in the header of the website.
- Uploading and saving ads' images (the task of increased complexity).

## Requirements specification:
- [Technical specification](https://skyengpublic.notion.site/4509dd17f5f840f1ba6807fe83aa9c15)


## The developers involved in the project:
- [Dmitriy Kuzeev](https://github.com/Asdemian)
- [Vyacheslav Kravchuk](https://github.com/Sla777Veg)
- [Andrey Klimov](https://github.com/klai365631)
- [Alla Kulchitskaya](https://github.com/AllaKulchitskaya)
- [Tatyana Alekseeva](https://github.com/AthenaPallada)
- [Damir Obukhov](https://github.com/Damir294)

## The technologies used in the project:
- Java 17
- Maven
- Spring Boot
- Spring Security
- Spring Web
- Spring JPA
- Hibernate
- PostgreSQL
- Liquibase
- Mapstruct
- Swagger

## Requirements for running the project:
- Clone this project from the repository to your development environment
- An empty database (Postgresql) and the following connection parameters in application.properties filled with your values:
  - spring.datasource.url=jdbc:postgresql://{database server IP address}/{database name}
  - spring.datasource.username={database username}
  - spring.datasource.password={database user password}
- Run [Docker](https://www.docker.com/)
- Run [Docker image](https://drive.google.com/file/d/1UZTpeTAQpC4ANkHEFAGK2yjTFzZhXLPz/view)
- Run _main_ in WebsiteBackendApplication.java
- Use http://localhost:3000 in the browser address bar to check the website