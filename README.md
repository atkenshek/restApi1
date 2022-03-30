# Spring Boot, PostgreSQL, Spring Data JPA, Hibernate, JUnit Jupiter, Rest API
Build Restful CRUD API for a user logging using Spring Boot, PostgreSQL, JPA and Hibernate.

<i>Task for Aloteq</i> <br>
Stack technologies: Spring Boot, lombok, PostgreSQL, Hibernate Data JPA, JUnit Jupiter, Docker.
## Steps to Setup

**1. Clone the application**

```bash
git clone https://github.com/atkenshek/restApi1.git
```

**2. Create PostgreSQL database**
```bash
CREATE DATABASE aloteq
```

**3. Change PostgreSQL username and password as per your installation**

+ open `src/main/resources/application.properties`
+ change `spring.datasource.username` and `spring.datasource.password` as per your mysql installation

**4. Run the app using maven or with docker**

```bash
mvn spring-boot:run
```
```bash
docker-compose up
```
The app will start running at <http://localhost:8080>

## Explore Rest APIs

The app defines following CRUD APIs.

<br>

| Method | Url | Description | Sample Valid Request Body |
| ------ | --- | ----------- | ------------------------- |
| GET    | /api/user/ | Get all users | |
| GET    | /api/users/{userId} | Get user by id | |
| POST   | /api/user | Add new user | [JSON](#post) |
| PUT    | /api/user/{userId} | Update user by id | [JSON](#put) |
| DELETE | /api/user/{userId} | Delete user by id | |
| DELETE | /api/user/ | Delete all users from database | |
## Sample Valid JSON Request Body

##### <a id="post"> POST -> /api/user </a>
```json
{
  "fullName": "Meiram Sopy Temirzhanov",
  "gender": "Male",
  "phoneNumber": "87477775454",
  "email": "random9804@yandex.kz"

}
```
##### <a id="put"> PUT -> /api/user </a>
```json
{
  "fullName": "newFullname",
  "gender": "newGender",
  "phoneNumber": "newPhoneNumber",
  "email": "newEmail"

}
```
###Example of GET requesting
```json
{
    "id": 1,
    "fullName": "Meiram Sopy Temirzhanov",
    "gender": "Male",
    "phoneNumber": "87477775454",
    "email": "random9804@yandex.kz",
    "country": "Kazakhstan",
    "city": "Nur-Sultan",
    "isp": "AS9198 JSC Kazakhtelecom"
}
```
#####The API takes IP of user which requesting api, and sets a country, city and ISP.