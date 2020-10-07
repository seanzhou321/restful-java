https://medium.com/swlh/restful-api-documentation-made-easy-with-swagger-and-openapi-6df7f26dcad
https://medium.com/thecodefountain/design-a-rest-api-with-spring-boot-and-mysql-a5572d94ccc7

switched to H2 database: https://www.baeldung.com/spring-boot-h2-database

Added OpenAPI from https://www.baeldung.com/spring-rest-openapi-documentation

Added HATEOAS https://spring.io/guides/tutorials/rest/ from https://spring.io/projects/spring-hateoas#samples

-- API docs:
in json format: http://localhost:8080/api-docs
download yaml file: http://localhost:8080/api-docs.yaml
Pretty swagger doc: http://localhost:8080/swagger-ui.html

Postman demo:
curl --location --request POST 'http://localhost:8080/hateoas/donor' \
--header 'Content-Type: application/json' \
--data-raw '{
    "firstName": "Fred",
    "lastName": "Cosby"
}'

curl --location --request GET 'http://localhost:8080/hateoas/donor/2'