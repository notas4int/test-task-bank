# Mail Delivery Application
>The system contains a service API that allows you to create new customers, tools for changing customer information, as well as a customer search API. Authentication via JWT is present

## Used technologies
* **Java**
* Spring Boot 3, Spring Data JPA (to work with entities)
* **PostgreSQL** (main DBMS for this project)
* Swagger
* JWT Auth
* Maven (package manager to manipulate with dependecies)
* Lombok
* MapStruct (for mapping models to and from dto)
* JUnit5 and Mockito (for testing)

## Steps to Setup

**1. Clone the application**

```bash
git clone https://github.com/notas4int/test-task-mail-delivery.git
```

**2. Create PostgreSQL database**
```bash
create database bank-system
```

**3. Change PostreSQL username and password as per your installation**

+ open `src/main/resources/application.properties`
+ change `spring.datasource.username` and `spring.datasource.password` as per your PostgreSQL installation

**4. Run the app using maven**

```bash
mvn spring-boot:run
```
The app will start running at <http://localhost:8080>

## Explore Rest APIs

### Users
| Method | Url                                            | Description                             | Sample Request                                                                 | Sample Response           |  
|--------|------------------------------------------------|-----------------------------------------|--------------------------------------------------------------------------------|---------------------------|
| POST   | /api/v1/staff/create-client                    | create new client                       | RequestRegisterDTO                                                             | ResponseAuthenticationDTO |
| POST   | /api/v1/auth/refresh-token                     | refresh token                           |                                                                                | ResponseAuthenticationDTO |
| POST   | /api/v1/auth/authenticate                      | authenticate                            | RequestAuthenticationDTO                                                       | ResponseAuthenticationDTO |
| DEL    | /api/v1/client/delete-info                     | delete email or phone                   | requestParams(email, phone)                                                    |                           |
| POST   | /api/v1/client/add-info                        | add email or/and phone                  | requestParams(email, phone)                                                    |                           |
| PATCH  | /api/v1/client/update-info                     | change email or/and phone               | requestParams(email, phone)                                                    |                           |
| PATCH  | /api/v1/client/transfer-money                  | transfer money                          | requestParams(login, amountOfFunds)                                            |                           |
| GET    | /api/v1/search-client/get-client               | get client info by phone/initials/email | requestParams(phone, email, initials)                                          | ResponseClientDTO         |
| GET    | /api/v1/search-client/get-clients-by-birthdate | get clients greater than entered date   | requestParams(dateOfBirthday) and (offset, limit, sort for pagination/sorting) | List of ResponseClientDTO |


##### <a>RequestRegisterDTO</a>
```json
{
  "login": "superlogin",
  "password": "superpassword",
  "balance": 1000.4,
  "phone": "89222341982",
  "email": "client@mail.com"
}
```

##### <a> RequestAuthenticationDTO </a>
```json
{
  "login": "superlogin",
  "password": "superpassword"
}
```

##### <a>ResponseAuthenticationDTO</a>

```json
{
  "access_token": "sdaifygvwesdi.lfujgchbdwseaikl.fhbsdf",
  "refresh_token": "ewil.gfvbwe.ifcgbwsedik.fgbwe.kdfj"
}
```

##### <a>ResponseClientDTO</a>

```json
{
  "id": 1,
  "name": "Oleg",
  "surname": "Sidorov",
  "middleName": "Olegovich",
  "dateOfBirthday": "21.01.2000",
  "phone": "89222341982",
  "email": "client@mail.com"
}
```