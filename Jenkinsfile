pipeline {
    agent any
    tools {
        gradle "gradle-4.10.2"
    }
    stages {
        stage('Gradle v') {
            steps {
                sh 'gradle --version'
            }
        }
        stage('docker v') {
            steps {
                sh 'docker --version'
            }
        }
        stage('Clone repository') {
            steps {
                checkout scm
            }
        }
        stage('GradleBuild') {
            steps {
                sh 'gradle build'
            }
        }
        stage('Dockerize') {
            agent {
                dockerfile true
            }
            steps {
                sh 'gradle --version'
            }
        }
    }

}