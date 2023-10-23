pipeline {
    agent any
    
    stages {
        stage('Build') {
           steps {
               // git 'https://github.com/giosil/wcollections.git'
               
               checkout scm
               
               script {
                  if (isUnix()) {
                      sh "mvn clean package"
                  } else {
                      bat "mvn clean package"
                  }
               }
           }
           
           post {
               always {
                   junit '**/target/surefire-reports/TEST-*.xml'
               }
           }
        }
    }
}