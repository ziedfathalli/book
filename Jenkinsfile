pipeline {
    agent any

    environment {
        IMAGE_NAME = "book-api:latest"
    }

    stages {
        stage('Cloner le dépôt') {
            steps {
                git branch: 'main', url: 'https://github.com/ziedfathalli/book.git'
            }
        }

        stage('Build Maven') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker image') {
            steps {
                sh 'docker build -t ${IMAGE_NAME} .'
            }
        }

        stage('Test image localement') {
            steps {
                sh 'docker run -d -p 8081:8080 --name test-book ${IMAGE_NAME}'
                sh 'sleep 10 && curl -f http://localhost:8081/actuator/health || (docker logs test-book && exit 1)'
                sh 'docker rm -f test-book'
            }
        }

        stage('Déploiement Kubernetes') {
            steps {
                sh '''
                docker save ${IMAGE_NAME} | nerdctl -n k8s.io load || true
                kubectl apply -f k8s/deployment.yaml
                kubectl apply -f k8s/service.yaml
                kubectl apply -f k8s/ingress.yaml
                '''
            }
        }
    }
}
