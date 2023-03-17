# Fizzbuzz Game

## How to run
### In docker container

- To run application on 8081 port use
```
./mvnw clean package docker:build docker:run
```
- To run application on a specific port use
  ```-Dservice.port=```

Example:
```
./mvnw clean package docker:build docker:run -Dservice.port=9870
```

It definitely works for the **amd64** architecture. There may be problems with running on the **M1**.

### In local
Java 17 is required
```
./mvnw clean package
java -jar fizzbuzz-app-0.0.1-SNAPSHOT.jar
```

## Query example
```
curl -X POST -H "Content-Type: application/json" \
    -d '{"numbers": [1, 2, 3, 4, 5, 14, 15]}' \
    http://localhost:8081/fizzbuzz
```

## Implementation note
1. The application purposely uses a BigInteger to handle any number.
2. The application and code are as close to production-level