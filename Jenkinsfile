pipeline {
    agent any
    tools {
        gradle "gradle"
    }
    stages {

        stage('Checkout') {
            steps{
                checkout scm
            }
        }

        stage('Gradle Build') {
            steps{
                sh 'echo doneVar = $doneVar'
                sh 'gradle clean build'
             }
        }

        stage('Docker build') {
            steps{
                sh 'docker build -t komprator/letsfindme:0.0.1 .'
            }
        }

        stage ('Run') {
            steps{
                script {
                    sh 'docker run -p 8050:8080 -d --name letscontain komprator/letsfindme:0.0.1'
                }
            }
        }

    }
}