pipeline {
    agent any
    
    environment {
        OS = script {
            def os = System.getProperty("os.name").toLowerCase()
            if (os.contains("win")) {
                return 'windows'
            } else {
                return 'linux'
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