pipeline {
    agent any

    tools {
        maven 'maven'
        jdk 'JDK 17'
    }

    environment {
        SONAR_TOKEN = credentials('SONARQUBE_TOKEN')
    }

    stages {

        stage('Checkout') {
            steps {
                git 'https://github.com/nikhil-3210/sit753task7.3hd.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('MySonar') {
                    sh """
                        mvn sonar:sonar \
                        -Dsonar.projectKey=SIT753HD7.3 \
                        -Dsonar.host.url=http://sonarqube:9000 \
                        -Dsonar.login=$SONAR_TOKEN
                    """
                }
            }
        }

        stage('Docker Build & Deploy') {
            steps {
                sh 'docker-compose down || true'
                sh 'docker-compose up -d --build'
            }
        }

        stage('Health Check') {
            steps {
                sh 'curl -f http://localhost:8080/actuator/health || exit 1'
            }
        }
    }

    post {
        always {
            echo 'Cleaning up...'
            sh 'docker-compose down || true'
        }
        success {
            echo '✅ Pipeline completed successfully!'
        }
        failure {
            echo '❌ Pipeline failed!'
        }
    }
}