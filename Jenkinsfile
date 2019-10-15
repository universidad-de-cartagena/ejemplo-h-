pipeline {
  agent {
    label 'equipo01'
  }
  environment {
    DOCKERHUB = credentials('jenkinsudc-dockerhub-account')
  }
  stages {
    stage('Kill everything') {
      steps {
        sh 'docker-compose down -v --remove-orphans || true'
        sh 'docker container kill $(docker ps -a -q) || true'
        sh 'docker rmi --force $(docker images -a -q) || true'
        sh 'docker system prune --volumes --force || true'
      }
    }
    stage('Build image') {
      post {
        success {
          sh 'sudo apt-get remove golang-docker-credential-helpers -y -q'
          sh 'docker login --username $DOCKERHUB_USR --password $DOCKERHUB_PSW'
          sh 'docker tag equipo01-backend-java:latest $DOCKERHUB_USR/equipo01-backend-java:latest'
          sh 'docker push $DOCKERHUB_USR/equipo01-backend-java:latest'
          sh 'sudo apt-get install docker-compose -y -q'
        }
        failure {
          sh 'docker system prune --volumes --force || true'
        }
      }
      steps {
        sh 'docker-compose build'
      }
    }
    stage('Tests') {
      steps {
        sh 'docker-compose -f docker-compose.test.yml up'
        junit(testResults: 'reports/*.xml', allowEmptyResults: true)
        sh 'docker-compose -f docker-compose.test.yml down -v --remove-orphans'
        sh 'docker system prune --volumes --force'
      }
    }
    stage('Deploy') {
      post {
        failure {
          echo 'A execution failed'
          sh 'docker-compose down -v --remove-orphans || true'
          sh 'docker system prune --volumes --force || true'
          sh 'docker rmi --force $(docker images --quiet)'
        }
      }
      steps {
        sh 'docker-compose up -d'
      }
    }
  }
}