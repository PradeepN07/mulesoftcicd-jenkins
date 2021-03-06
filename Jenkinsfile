pipeline {
  agent any
 
  environment {
    DEPLOY_CREDS = credentials('deploy-anypoint-user')
    MULE_VERSION = '4.3.0'
    BG = "Netrovert Softwares"
    WORKER = "Micro"
    BRANCH_NAME="master"
    APPNAME = "mule-cicd"

    DEPLOY_BAT = "true"
	EMAIL_RECIPIENTS = 'Pradeep.N2019@gmail'

  }
  stages {
    stage('Build') {
      steps {
            bat 'mvn -B -U -e -V clean -DskipTests package'
      }
    }

    stage('Test') {
      steps {
          bat "mvn test"
      }
    }

     stage('Deploy Development') {
      environment {
        ENVIRONMENT = 'Sandbox'
        APP_NAME = 'dev-mule-cicd'
		
      }
      steps {
            bat 'mvn -U -V -e -B -DskipTests deploy -DmuleDeploy -Dmule.version="%MULE_VERSION%" -Danypoint.username="%DEPLOY_CREDS_USR%" -Danypoint.password="%DEPLOY_CREDS_PSW%" -Dcloudhub.app="%APP_NAME%" -Dcloudhub.environment="%ENVIRONMENT%" -Dcloudhub.bg="%BG%" -Dcloudhub.worker="%WORKER%"'
      }
    }
    
  }
  post {
        // Always runs. And it runs before any of the other post conditions.
        always {
            // Let's wipe out the workspace before we finish!
            deleteDir()
        }
        success {
           notifyBuild("${currentBuild.currentResult}")

        }
        unstable {
            sendEmail("Unstable");
        }
        failure {
            sendEmail("Failed");
        }
    }
	

  tools {
    maven 'M3'
  }
}


def sendEmail(status) {
   mail(bcc: '',
                     body: "Run ${JOB_NAME}-#${BUILD_NUMBER} succeeded. To get more details, visit the build results page: ${BUILD_URL}.",
                     cc: 'Pradeep.Kumar@netrovert.net',
                     from: 'jenkins-admin@gmail.com',
                     replyTo: '',
                     subject: "Build $BUILD_NUMBER - " + status + " (${currentBuild.fullDisplayName})",
                     to: '$EMAIL_RECIPIENTS')
}

def notifyBuild(String buildStatus = 'STARTED') {
    // build status of null means successful
    buildStatus = buildStatus ?: 'SUCCESS'
    // Default values
    def colorName = 'RED'
    def colorCode = '#FF0000'
    def subject = "${buildStatus}: '${env.JOB_NAME} [${env.BUILD_NUMBER}]'" 
	
  

    // Send notifications
	 mail(bcc: '',
                     body: "The jenkins CICD Pipeline executed with status ${buildStatus} and the Build Name:: ${env.JOB_NAME} \n " +
            "Build Number: ${env.BUILD_NUMBER} \n " +
            "Build URL: ${env.BUILD_URL} \n ",
                     cc: 'Pradeep.Kumar@netrovert.net',
                     from: 'jenkins-admin@gmail.com',
                     replyTo: '',
                     subject: "Jenkins Job Exceuted With Status ${buildStatus}: '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
                     to: 'Pradeep.N2019@gmail.com')
					 
    
}


