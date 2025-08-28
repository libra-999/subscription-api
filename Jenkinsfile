pipeline{
    agent any;

    tools{
        maven "maven-3.9.10"
        jdk "jdk-21"
    }
    environment{
        DOCKER_HUB_CREDENTIALS = credentials("DOCKER_HUB_CREDENTIAL")
        DOCKER_HUB_ID = "xemon99"
        IMAGE_NAME = "xemon99/subscribe-auto"
    }

    stages{
        stage("Build") {}
        stage("Test Unit") {}
        stage("Environment Setup") {}
        stage("Build Docker Image && Push to Docker Hub") {}
    }
}