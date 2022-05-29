# prisma-task

## steps to run prisma-service and MongoDB inside a container with volume already persisted
* run the following command : `mvn clean package -Dmaven.test.skip` to clean and build the jar file of the project maven.
* cd docker/
* run the follwing command: docker-compose up
    - this command will download mongo image and run mongoDB with data, then start the jar file inside a container using config from dockerFile
* API:
    - there is a collection postman with 5 API and some examples
