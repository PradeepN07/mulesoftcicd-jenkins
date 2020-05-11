pipeline {

  agent any
  environment {
    //adding a comment for the commit test
    DEPLOY_CREDS = credentials('deploy-anypoint-user')
    MULE_VERSION = '4.3.0'
    BG = "Netrovert\\Pradeep"
    WORKER = "Micro"
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
        APP_NAME = 'mulesoftcicd-dev'
      }
      steps {
            bat 'mvn -U -V -e -B -DskipTests deploy -DmuleDeploy -Dmule.version="4.3.0" -Danypoint.username="Pradeep_Kumar" -Danypoint.password="Deepu@1982" -Dcloudhub.app="muleCICD-dev" -Dcloudhub.environment="Sandbox" -Dcloudhub.bg="Pradeep" -Dcloudhub.worker="Micro"'
      }
    }
    
  }

  tools {
    maven 'M3'
  }
}