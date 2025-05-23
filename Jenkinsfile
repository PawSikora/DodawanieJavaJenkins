pipeline {
  agent any

  tools {
    maven 'Maven 3.9.6'
  }

  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Build & Test') {
      agent {
        docker {
          image 'maven:3.9.6-eclipse-temurin-17'
          args '-v /root/.m2:/root/.m2'
        }
      }
      steps {
        sh 'mvn clean test'
        stash includes: 'target/surefire-reports/*.xml', name: 'test-results'
      }
    }

    stage('Publish Test Results') {
      steps {
        unstash 'test-results'
        junit 'target/surefire-reports/*.xml'
      }
    }

    stage('SonarQube Analysis') {
      steps {
        withSonarQubeEnv('SonarQube') {
          sh 'mvn sonar:sonar -Dsonar.projectKey=DodawanieJava'
        }
      }
    }
  }

  post {
  success {
    mail to: 'test@example.com',
         subject: "Build ${env.JOB_NAME} #${env.BUILD_NUMBER} SUKCES",
         body: "Build zakończony powodzeniem."
  }
  failure {
    mail to: 'test@example.com',
         subject: "Build ${env.JOB_NAME} #${env.BUILD_NUMBER} NIEPOWODZENIE",
         body: "Build nie powiódł się. Sprawdź logi."
  }
}

}
