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
                sh 'pwd'
                sh 'ls'
                checkout scm
            }
        }
        stage('GradleBuild') {
            steps {
                sh 'gradle build'
                sh 'pwd'
                sh 'ls'
            }
        }
        stage('Dockerize') {
            agent {
                dockerfile true
            }
            steps {
                sh 'pwd'
                sh 'ls'
                sh 'gradle --version'
            }
        }
    }

}