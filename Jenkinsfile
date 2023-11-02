pipeline {
  agent {
     label 'OCPNonProdAgent'
   }  
  environment{
      NAMESPACE = 'ais-service-demo'
      APP_NAME = 'jenkins'
      APP_SVC_NAME = 'jenkins'
      APP_BUILD_PATH = "${WORKSPACE}/controller"
      APP_IMAGE = "image-registry.openshift-image-registry.svc:5000/${NAMESPACE}/${APP_NAME}"
   }
  stages {
    stage('Image Build') {
      steps{
      sh pwd    
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