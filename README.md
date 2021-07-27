# book-details

Example on how to use GraphQL

## How to test

Firstly, we should use Docker Compose to run PostgreSQL container:

```shell
docker-compose up -d
```

then, run the following commands:

```shell
gradle clean build
java -jar build/libs/book-details-0.0.1-SNAPSHOT.jar -Dspring.profiles.active=local
```

finally, run the cURL command to test:

```shell
curl 'http://localhost:8080/graphql' -H 'Content-Type: application/json' -H 'Accept: application/json' --data @test-graphql-insert.txt
```

for the insert and:

```shell
curl 'http://localhost:8080/graphql' -H 'Content-Type: application/json' -H 'Accept: application/json' --data '{"query":"{\n  bookById(isbn: \"123-1234567890\"){\n    isbn\n    title\n    pageCount\n}}"}'
```

for the retrieve.

## In Progress

There is a simple React application form to insert data using this backend. You must have the latest Node LTS version.
To start it, please do:

```shell
cd src/client
npm start
```