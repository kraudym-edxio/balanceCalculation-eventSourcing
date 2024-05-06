Real-time Transaction Service
===============================
## Schema
The [included service.yml](service.yml) is the OpenAPI 3.0 schema to the services that have been implemented.

## Details
The service accepts two types of transactions:
1) Loads: Add money to a user (credit)
2) Authorizations: Conditionally remove money from a user (debit)

## Bootstrap instructions
To run this server locally, do the following:
1. Clone the repo: git clone <repo>
2. Install dependencies: mvn install
3. Setup the environment: Setup environment variables, if any
4. Run the application: mvn spring-boot:run
5. When the project is successfully bootstrapped and running, you should be able to access it locally at URL "http://localhost:8080/{endpoint}",
   where {endpoint} is replaced with /ping, /authorization/{messageId}, /load/{messageId}, or /event-store/events

## Design considerations
1. Event-Sourcing Architecture: Event sourcing is applied realizing its benefits including accuracy and traceability of events in historical order.
2. RESTful API: Provided to accept two types of transactions (load and authorizations) and used for simplicity, scalability, and wide acceptance.
3. Model-View-Controller: This architecture was applied to achieve better separation of concerns, which led to easier testing, scaling, and maintenance.
4. In-Memory Data Store: Using an in-memory data structure for storing events which reduces overhead of database setup, lighter application and faster response.
5. Spring Boot App: Built on Spring Boot framework for dependency injections, ease of setup and configs, and run tests.
6. Unit and Integration Testing: Comprehensive testing was done to ensure accuracy and reliability of components.
7. Maven Project Structure: Standardized project structure of code, tests, scripts, et. to ensure code readability and maintainability.

## Deployment considerations
1. Containerization with Docker: Docker file based on Java image that would contain instructions to copy JAR files into the Docker image, defining commands
   to run the application.
2. Security Protocols: Use of HTTPS for secure communication, implementing authentication and authorization systems, updating dependencies, and protecting against
   SQL injections.
3. Sever Distribution: Allocated servers across various regions to reduce latency or scaling a data centre to handle user load.
4. CI/CD: Pipeline such as Jenkins to automate building of Docker images, keep deployment smooth and consistent.
5. Persistent Store: Use of SQL databases like MySQL to allow independent data survival past the application's runtime - storing data to 
   be accessed over a long period of time, and ensuring database is regularly backed up and can be easily restored in case of failures.
6. Auto-scaling: Application should be able to scale to more instances or more resources per instance to ensure smooth performance under high traffic.