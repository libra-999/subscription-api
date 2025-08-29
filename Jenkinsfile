pipeline{
    agent any;

    tools{
        maven "maven-3.9.10"
        jdk "jdk-21"
    }
    environment{
        DOCKER_HUB_CREDENTIALS = credentials("DOCKER_HUB_CREDENTIAL")
        TELEGRAM_BOT_TOKEN = credentials("TELEGRAM_TOKEN")
        TELEGRAM_CHAT_ID = credentials("CHAT_ID")
        DOCKER_HUB_ID = "xemon99"
        IMAGE_NAME = "xemon99/subscribe-auto"
        APP = "Subscribe Automation System"
        BRANCH_NAME = "main"
    }

    stages{

        stage("Checkout Code") {
            steps{
                echo "Checking out code from ${BRANCH_NAME} branch"
                checkout scm
            }
        }

        stage("Determine Version") {
            steps {
                script {
                    env.VERSION = sh(script: "git describe --tags --exact-match 2>/dev/null || echo ''", returnStdout: true).trim()
                    echo "VERSION=${env.VERSION}"
                }
            }
        }

        stage("Build Artifact") {
            when {
                expression {
                    return TagBuild()
                }
            }
            steps{
                echo "Building the artifact"
                sh "mvn clean package -DskipTests"
            }
        }
//         stage("Test Unit") {
//             when {
//                 expression {
//                     return TagBuild()
//                 }
//             }
//             steps{
//                 echo "Running Unit Tests"
//                 sh "mvn test"
//             }
//
//         }
        stage("Build Docker Image && Push to Docker Hub") {
            when {
                expression {
                    return TagBuild()
                }
            }
            steps{

                echo "Building Docker Image with version: ${env.VERSION}"
                sh "docker build -t ${IMAGE_NAME}:${env.VERSION} ."
                sh "echo ${DOCKER_HUB_CREDENTIALS}| docker login -u ${DOCKER_HUB_ID} --password-stdin"
                sh "docker push ${IMAGE_NAME}:${env.VERSION}"
            }
        }
    }
    post{
        success {
            script{
                def msg = """✅ *Deployed Successfully!* 🚀
                *Image:* ${IMAGE_NAME}:${env.VERSION}
                *Project:* ${APP}
                """

                sendMessage(msg)
            }

        }
        failure {
            script {
                def msg = """*Deployed Failed!*
                *Project:* ${APP}
                """
                sendMessage(msg)
            }

        }
    }

}
def TagBuild(){
    echo "Tagging the build"
    def version = sh(script: "git describe --tags --exact-match 2>/dev/null || echo ''", returnStdout: true).trim()
    return version != ''
}
def sendMessage(message){
    sh """
        curl -s -X POST "https://api.telegram.org/bot${TELEGRAM_BOT_TOKEN}/sendMessage" \\
        -d chat_id="${TELEGRAM_CHAT_ID}" \\
        -d text="${message}" \\
        -d parse_mode=Markdown
    """

}

