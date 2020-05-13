pipeline {
  agent {
    label 'equipo01'
  }
  environment {
    DOCKERHUB = credentials('jenkinsudc-dockerhub-account')
  }
  stages {
    stage('Limpiar elementos Docker huerfanos') {
      steps {
        sh 'docker container prune --force || true'
        sh 'docker volume prune --force || true'
        sh 'docker image prune -f'
      }
    }
    stage('Crear de imagen Docker') {
      steps {
        sh 'docker-compose build --force-rm'
      }
      post {
        failure {
          sh 'docker image prune -f'
        }
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
    stage('Publicar imagen Docker') {
      when {
          branch 'master'
      }
      steps {
          sh 'docker login --username $DOCKERHUB_USR --password $DOCKERHUB_PSW'
          sh 'docker tag equipo01-backend-java:latest $DOCKERHUB_USR/equipo01-backend-java:latest'
          sh 'docker push $DOCKERHUB_USR/equipo01-backend-java:latest'
          sh 'sudo apt-get install docker-compose -y -q'
      }
      post {
        failure {
          sh 'docker system prune --volumes --force || true'
        }
      }
    }
    stage('Deploy') {
      when {
          branch 'master'
      }
      post {
        failure {
          sh 'docker container prune --force || true'
          sh 'docker volume prune --force || true'
          sh 'docker image prune -f'
        }
      }
      steps {
        sh 'docker-compose up -d'
      }
    }
  }
}
