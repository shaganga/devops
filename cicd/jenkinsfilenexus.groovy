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
  stage('Stage: upload to nexus') {
         
            steps {
                script {
                // publish the artact into Nexus repository
                def mavenPom = readMavenPom file: 'pom.xml'
  
    nexusArtifactUploader artifacts: [
	[
		artifactId: 'ribhus',
		classifier: '',
		file: "target/hello-world-0.1.0.jar",
		type: 'jar'
		]
	],
    credentialsId: 'nexus-access',
    groupId: 'dev',
    nexusUrl: "54.145.82.82:8081",
    nexusVersion: 'nexus3',
    protocol: 'http',
    repository: 'mvn-repo',
    version: "${mavenPom.version}"
            }
            }
        }
	 stage('Stage: Deploy into web/app server') {
       
            steps {
                script {
			dir (/tmp) {
			
			     sh "curl -LO -u ${nexus_user}:${nexus_pwd} -X GET http://54.145.82.82:8081/repository/mvn-repo/dev/ribhus/0.1.0/ribhus-0.1.0.jar"
			}
		}}}

}

}
