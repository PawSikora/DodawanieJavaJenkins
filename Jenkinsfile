pipeline {
  agent any

  tools {
    maven 'Maven 3.9.6'
  }

  environment {
    MAVEN_OPTS = "-Dmaven.repo.local=.m2/repository"
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
          sh 'mvn sonar:sonar -Dsonar.projectKey=DodawanieJava -Dsonar.projectName=DodawanieJava'
        }
      }
    }

    stage('Quality Gate') {
      steps {
        timeout(time: 1, unit: 'MINUTES') {
          waitForQualityGate abortPipeline: true
        }
      }
    }
  }

  post {
    success {
      echo 'Pipeline zakończony sukcesem.'
    }
    failure {
      echo 'Pipeline nie powiódł się.'
    }
    always {
      echo 'Pipeline zakończony.'
    }
  }
}
