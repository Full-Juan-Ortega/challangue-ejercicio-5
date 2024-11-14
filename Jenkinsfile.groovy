pipeline {
    agent any  // Esto indica que el pipeline se ejecutará en cualquier agente disponible

    environment {
        DOCKER_REGISTRY = "juanortegait"
        IMAGE_NAME = "ej-05-node"
        IMAGE_TAG = "v1"
        ID_GIT_CREDENTIALS = "659a92cb-73ca-4e99-b11d-9a616e512bd1"
    }

    stages {
        
        stage('versiones') {
            steps {
                script {
                    // Ejecuta el comando para conectar Docker al Daemon de Minikube
                    sh 'docker --version'
                    sh 'kubectl version'
                }
            }
        }

        stage('git clone') {
            steps {
                withCredentials([usernamePassword(credentialsId: ID_GIT_CREDENTIALS, usernameVariable: 'GIT_USERNAME', passwordVariable: 'GIT_PASSWORD')]) {
                    git(url: 'https://github.com/Gonveliz/node-app.git', credentialsId: ID_GIT_CREDENTIALS)
                }
            }
        }
    
        stage('Login to Docker Hub') {
            steps {
                script {
                    echo "docker login step start"
                    withCredentials([
                        usernamePassword(
                            credentialsId: 'DOCKER-HUB-CREDENTIALS', 
                            passwordVariable: 'dockerHubPassword', 
                            usernameVariable: 'dockerHubUser')]) {
                                // Ejecuta el login sin `env.`
                                sh "docker login -u $dockerHubUser -p $dockerHubPassword"
                    }
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    sh 'ls'
                    sh 'pwd'
                    sh "docker build -t ${DOCKER_REGISTRY}/${IMAGE_NAME}:${IMAGE_TAG} ."
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    // Push de la imagen a Docker Hub
                    sh "docker push ${DOCKER_REGISTRY}/${IMAGE_NAME}:${IMAGE_TAG}"
                }
            }
        }
        stage('levantar el pod'){
            steps {
                script {
                    sh "kubectl run pod-desde-jenkins --image=juanortegait/ej-05-node:v1"
                }
            }
            
        }
    }
}