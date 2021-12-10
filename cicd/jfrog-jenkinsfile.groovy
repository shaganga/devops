pipeline {
    agent any
    
    stages {
        stage('Stage: SCM') { 

            steps {
                // Get some code from a GitLab repository
        git branch: 'master', url: 'https://github.com/dstar55/docker-hello-world-spring-boot.git'
            }
        }
    stage('Stage: Maven Build'){

            steps {
                //Generate Artifact through maven build
                
            sh "mvn clean install"
                  
        }
    }
   stage('Stage: upload to jfrog') {
         
            steps {
                script {
                // publish the artact into jfrog repository
                 sh 'curl -u $jfrog_user:$jfrog_pwd -X PUT "https://veeshadevops.jfrog.io/artifactory/mvn-repo/$BUILD_NUMBER/hello-world-0.1.0.jar" -T target/hello-world-0.1.0.jar'

                }
            }
        }

    stage('Stage: Deploy into web/app server') {
       
            steps {
                script {
                def remote = [:]
        remote.name = "localhost"
        remote.host = "172.31.13.45"
        remote.user = "${remote_user}"
        remote.password = "${remote_pwd}"
        remote.allowAnyHosts = true
			//sshCommand remote: remote, command: "wget --user=$jfrog_user --password=$jfrog_pwd https://veeshadevops.jfrog.io/artifactory/mvn-repo/$BUILD_NUMBER/hello-world-0.1.0.jar -P /tmp"
			sshCommand remote: remote, command: "curl -u $jfrog_user:$jfrog_pwd -X GET 'https://veeshadevops.jfrog.io/artifactory/mvn-repo/${BUILD_NUMBER}/hello-world-0.1.0.jar' --output /tmp/helloworld.jar"
	        }
            }
        }
    }
       
}
