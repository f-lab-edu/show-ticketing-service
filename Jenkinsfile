pipeline {
   agent any

   tools {
        maven 'MAVEN'
        jdk 'JDK 11'
   }

   stages {
        stage('checkout') {
            steps {
                checkout scm
                echo 'git checkout success'
            }
        }

        stage('Build') {
            steps {
                script {
                    sh 'mvn clean package -DskipTests=true'
                    echo 'build success'
                }
            }
        }

        stage('Unit Test') {
            steps {
                script {
                    sh 'mvn surefire:test'
                    junit '**/target/surefire-reports/TEST-*.xml'
                }
            }
        }

   }

}