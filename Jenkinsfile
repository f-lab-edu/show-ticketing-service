pipeline {
   agent any

   tools {
        maven 'MAVEN'
        jdk 'JDK 1.8'
   }

   stages {
        stage('checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                script {
                    sh 'mvn clean package -DskipTests=true'
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

        stage('Deploy') {
            when {
                branch 'develop'
            }
            steps {
                sh 'scp -P ${DEST_SERVER_PORT} target/*.jar ${DEST_SERVER}:${DEST_PATH}'
            }
        }

   }

}