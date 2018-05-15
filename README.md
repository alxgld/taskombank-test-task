# Test Task For Taskombank
**Simple client management API for Admin**

## Functional services
**This application consist of two independently deployable applications:**

#### Client Service
**Provides CRUD endpoints for clients management.**  

#### Admin Service
**Main service for Admin application entrypoint. Invokes Client Service endpoints under the hood.**
#### Notes
- Swagger: http://localhost:8080/swagger-ui.html#/

- For simplicity there was used in memory UserDetailsService with a single user: (admin:admin)



# How to run
Run the following commands from the root directory:

```
mvn clean install
```

```
docker-compose up

```