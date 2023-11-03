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
           echo "POM.xml after change"
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
        sh "podman build --no-cache -f Dockerfile --tag ${APP_IMAGE}:"+env.BUILD_NUMBER
      }
    }
    
    stage('Authentication to Internal Registry'){
        steps{
            echo 'Authenticating to Openshift Registry'
            sh 'podman login -u jenkins -p $(oc whoami -t) image-registry.openshift-image-registry.svc:5000 --tls-verify=false'
        }
    }
    stage('Pushing to Internal Registry') {
      steps {
        echo 'Pushing'
        sh "podman push ${APP_IMAGE}:"+env.BUILD_NUMBER+ " --tls-verify=false"
      }
    }
    stage('Deploy OCP application') {
      steps{
        script{
         dir("${WORKSPACE}"){ 
           def deploymentYaml = readYaml file: "front-end-deployment.yaml"
           echo "deployment yaml: " + deploymentYaml
           echo "image: "+deploymentYaml.spec.spec
         
         }
        }
      }
    }
         
    
  }
  post {
         // Clean after build
         always {
            echo 'Cleaning up after build...'
             cleanWs(cleanWhenNotBuilt: false,
                     deleteDirs: true,
                     disableDeferredWipeout: true,
                     notFailBuild: true,
                     patterns: [[pattern: '.gitignore', type: 'INCLUDE'],
                                [pattern: '.propsfile', type: 'EXCLUDE']])
         }
         success {  
             echo 'Sending successful build message'              
        //     mail body: "<b>BUILD SUCCESS</b><br><br>Project: ${env.JOB_NAME}<br><br>Build Number: ${env.BUILD_NUMBER} <br><br> URL of build: <a href=\"${env.BUILD_URL}\">${env.BUILD_URL}</a>", charset: 'UTF-8', from: 'ais-jenkis<no-reply@cov.virginia.gov>', mimeType: 'text/html', subject: "Jenkins BUILD SUCCESS: ${env.JOB_NAME}", to: "${env.JENKINS_ADMINS_EMAIL}";
         }  
         failure {  
             echo 'Sending failure build message'  
        //     mail body: "<b>BUILD FAILURE</b><br><br>Project: ${env.JOB_NAME}<br><br>Build Number: ${env.BUILD_NUMBER} <br><br> URL of build: <a href=\"${env.BUILD_URL}\">${env.BUILD_URL}</a>", charset: 'UTF-8', from: 'ais-jenkis<no-reply@cov.virginia.gov>', mimeType: 'text/html', subject: "Jenkins BUILD FAILURE: ${env.JOB_NAME}", to: "${env.JENKINS_ADMINS_EMAIL}";
         }   
     }
  
  
 }