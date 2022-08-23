# prisma-task

## steps to run prisma-service and MongoDB inside a container with volume already persisted
* run the follwing command: `docker-compose up`
    - this command will download mongo image and run mongoDB with data, then start rabbitMQ and the 2 jar file inside containers using config from dockerFile
* API:
    - there is a collection postman with 5 API and some examples