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
                git credentialsId: ID_GIT_CREDENTIALS , url: 'https://github.com/Gonveliz/node-app.git'
            }
        }
    
        stage('Login to Docker Hub') {
            environment {
                DOCKER_HUB = credentials('DOCKER-HUB-CREDENTIALS')
            }
            steps {
                script {
                    // Login de Docker usando password-stdin
                    sh """
                        echo $DOCKER_HUB_PSW | docker login -u $DOCKER_HUB_USR --password-stdin
                    """
                }
            }
        }
    
        stage('Build Docker Image') {
            steps {
                script {

                    sh "docker build -t $DOCKER_REGISTRY/$IMAGE_NAME:$IMAGE_TAG ."
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    // Push de la imagen a Docker Hub
                    sh "docker push $DOCKER_REGISTRY/$IMAGE_NAME:$IMAGE_TAG"
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