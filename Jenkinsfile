pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps{
                checkout scm
            }
        }

        stage('Build') {
            steps{

                sh 'echo doneVar = $doneVar'
                sh 'gradle clean install'

             }
        }

        stage('Image') {
            steps{
                def app = docker.build("localhost:5000/letsfind-repo:${env.BUILD_ID}")

            }
        }

        stage ('Run') {
            steps{
                def apsp = docker.image("letsfind-repo:${env.BUILD_ID}").run('-p 8085:8080 -h discovery --name discovery')
                sh 'echo doneVar = $doneVar'
            }
        }

        stage ('Final') {
            steps{
                build job: 'account-service-pipeline', wait: false
            }
        }
    }
}