pipeline {
    agent {
                    dockerfile true
                }
    tools {
        gradle "gradle-5"
    }
    stages {
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
                sh 'ls -la'
                sh 'ls '
                sh 'ls build/libs'
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