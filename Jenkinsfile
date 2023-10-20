pipeline {
    agent any
    
    stages {
        stage('Build') {
           steps {
               git 'https://github.com/giosil/wcollections.git'
               
               mvn clean package
           }
           
           post {
               always {
                   junit '**/target/surefire-reports/TEST-*.xml'
               }
           }
        }
    }
}