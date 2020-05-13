pipeline {
  agent any
 
  environment {
    DEPLOY_CREDS = credentials('deploy-anypoint-user')
    MULE_VERSION = '4.3.0'
    BG = "Netrovert Softwares"
    WORKER = "Micro"

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
        APP_NAME =dev-$APPNAME
		
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
            sendEmail("Successful");
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