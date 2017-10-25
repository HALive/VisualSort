pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh '''chmod +x gradlew
./gradlew build -x test '''
      }
    }
    stage('Test') {
      steps {
        sh './gradlew test'
      }
    }
  }
}