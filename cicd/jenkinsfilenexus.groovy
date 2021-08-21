pipeline {

environment {
repo_name = "cicd_repo"

dockerImage = ''
}
agent any
stages {
stage ('Cloning Git') {
  steps {
  git 'https://github.com/dstar55/docker-hello-world-spring-boot.git'
        }
    }

  stage('Stage: Maven Build'){    
            steps {
                //Generate Artifact through maven build       
            sh "mvn clean install"             
        }
    }
  
}

}
