pipeline {
    agent any

    environment {
        SONAR_TOKEN = credentials('sonar-token') // add this in Jenkins > Manage Credentials
    }

    tools {
        maven 'Maven 3.9.5'    // or whatever your Jenkins tool config name is
        jdk 'JDK 17'           // must match your Jenkins JDK installation name
    }

    stages {

        stage('Checkout') {
            steps {
                git 'https://github.com/your-username/task-management-api.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Code Quality - SonarQube') {
            steps {
                withSonarQubeEnv('My SonarQube Server') {
                    sh 'mvn sonar:sonar -Dsonar.projectKey=task-api -Dsonar.host.url=$SONAR_HOST_URL -Dsonar.login=$SONAR_TOKEN'
                }
            }
        }

        stage('Security Scan - Trivy') {
            steps {
                sh '''
                    docker build -t task-api .
                    trivy image --exit-code 0 --severity MEDIUM,HIGH task-api
                '''
            }
        }

        stage('Deploy to Test Env') {
            steps {
                sh 'docker-compose up -d --build'
            }
        }

        stage('Release (Git Tag)') {
            steps {
                script {
                    def tag = "release-${env.BUILD_NUMBER}"
                    sh "git tag ${tag}"
                    sh "git push origin ${tag}"
                }
            }
        }

        stage('Monitoring Check') {
            steps {
                sh 'curl -f http://localhost:8080/actuator/health'
            }
        }
    }

    post {
        always {
            echo 'Cleaning up...'
            sh 'docker-compose down'
        }
        success {
            echo '🎉 Pipeline completed successfully!'
        }
        failure {
            echo '❌ Pipeline failed!'
        }
    }
}