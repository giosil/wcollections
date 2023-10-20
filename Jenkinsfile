pipeline {
    agent any
    
    stages {
        stage('Build') {
           steps {
               git 'https://github.com/giosil/wcollections.git'
               
               // bat "mvn clean package"
               // sh "mvn clean package"
               sh "/opt/apache-maven-3.9.5/bin/mvn clean package"
           }
           
           post {
               always {
                   junit '**/target/surefire-reports/TEST-*.xml'
               }
           }
        }
    }
}