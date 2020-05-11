pipeline {
  agent any
  environment {
    DEPLOY_CREDS = credentials('deploy-anypoint-user')
    MULE_VERSION = '4.2.2'
    BG = "Netrovert\\Pradeep"
    WORKER = "Micro"

    APPNAME = "mule-cicd"

    DEPLOY_BAT = "true"
  }
  stages {
   
    
    stage('Build') {
      steps {
        withMaven(
          mavenSettingsConfig: 'public-maven-config.xml') {
            sh 'mvn -B -U -e -V clean -DskipTests package'
          }
      }
    }

    stage('Test') {
      steps {
	      withMaven(
          mavenSettingsConfig: 'public-maven-config.xml') {
           sh "mvn -B -Dmule.env=dev test"
       }
     }
      post {
        always {
          publishHTML (target: [
                        allowMissing: false,
                        alwaysLinkToLastBuild: false,
                       keepAll: true,
                        reportDir: 'target/site/munit/coverage',
                        reportFiles: 'summary.html',
                        reportName: "Code coverage"
                    ]
                  )
        }
      }
    }

    stage('Deploy Development') {
      environment {
        ENVIRONMENT = 'Development'
        APP_NAME = 'dev-${APPNAME}'
      }
      steps {
        withMaven(
          mavenSettingsConfig: 'public-maven-config.xml') {
            sh 'mvn -U -V -e -B -DskipTests deploy -DmuleDeploy -Dmule.version=$MULE_VERSION -Danypoint.username=$DEPLOY_CREDS_USR -Danypoint.password=$DEPLOY_CREDS_PSW -Dcloudhub.app=$APP_NAME -Dcloudhub.environment=$ENVIRONMENT -Dcloudhub.bg="$BG" -Dcloudhub.worker=$WORKER -Denv.name=dev'
          }
      }
    }

    stage('Integration Test') {
        steps {
            sh 'sed -i -e "s/url:.*$/url: \'http:\\/\\/dev-${APPNAME}.us-e2.cloudhub.io\\/api\',/g" integration-tests/config/devx.dwl'
            sh 'bat integration-tests --config=devx'
        }
        post {
          always {
            publishHTML (target: [
                            allowMissing: false,
                            alwaysLinkToLastBuild: true,
                            keepAll: true,
                            reportDir: '/tmp',
                            reportFiles: 'index.html',
                            reportName: "Integration Test",
                            includes: '**/index.html'
                        ]
                      )       
          }
        }
    }
    stage('Deploy Production') {
        environment {
          ENVIRONMENT = 'Production'
          APP_NAME = '${APPNAME}'
        }
        steps {
          withMaven(
          mavenSettingsConfig: 'public-maven-config.xml') {
              sh 'mvn -U -V -e -B -DskipTests deploy -DmuleDeploy -Dmule.version=$MULE_VERSION -Danypoint.username=$DEPLOY_CREDS_USR -Danypoint.password=$DEPLOY_CREDS_PSW -Dcloudhub.app=$APP_NAME -Dcloudhub.environment=$ENVIRONMENT -Dcloudhub.bg="$BG" -Dcloudhub.worker=$WORKER -Denv.name=prod'
          }
        }
    }

   
  tools {
    maven 'M3'
  }
}