pipeline {
    agent any
    
    environment {
        OS = script {
            if (isUnix()) {
                return 'linux'
            } else {
                return 'windows'
            }
        }
    }
    
    stages {
        stage('Build') {
           steps {
               git 'https://github.com/giosil/wcollections.git'
               
               script {
                  echo "OS = ${env.OS}"
                  
                  if (env.OS == 'windows') {
                      bat "mvn clean package"
                  } else {
                      sh "mvn clean package"
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