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
        script{
         dir("${APP_BUILD_PATH}"){ 
           sh "mvn --version"
           echo "--- Update maven POM.xml"
           def pom = readMavenPom file: 'pom.xml'
           echo "pom->appName: " +pom.build.finalName
           echo "pom->version: " +pom.version
           pom.build.finalName = "${APP_NAME}"+"-"+env.BUILD_NUMBER
           pom.version = env.BUILD_NUMBER
           echo "after change"
           echo "pom->appName: " +pom.build.finalName
           echo "pom->version: " +pom.version
           writeMavenPom model: pom
           
           sh "mvn clean package"
         }
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