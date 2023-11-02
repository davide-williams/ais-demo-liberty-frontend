pipeline {
  agent {
     label 'OCPNonProdAgent'
   }  
  environment{
      NAMESPACE = 'ais-service-demo'
      APP_NAME = 'ais-demo-frontend-app'
      APP_SVC_NAME = 'ais-demo-frontend-app'
      APP_BUILD_PATH = "${WORKSPACE}/front-end-service"
      APP_IMAGE = "image-registry.openshift-image-registry.svc:5000/${NAMESPACE}/${APP_NAME}"
   }
  stages {
    stage('Application Build') {
      steps{
       dir("${APP_BUILD_PATH}"){ 
        sh "mvn --version"
        sh "pwd"
       }
      }
    }
    stage('Image Build') {
      steps {
        echo 'Building'    
    //    sh "podman build --no-cache -f Dockerfile --tag ${APP_IMAGE}:"+env.BUILD_NUMBER
      }
    }
  }
 }