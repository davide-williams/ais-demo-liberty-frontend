pipeline {
	
  agent {
     label 'OCPNonProdAgent'
   }
     
  environment{
      NAMESPACE = 'ais-service-demo'
      APP_NAME = 'ais-demo-frontend-app'
      DEPLOYMENT_NAME='ais-demo-liberty'
      APP_SVC_NAME = 'ais-demo-frontend-app'
      EMAIL_RECIPENT ='DavidE.Williams@vita.virginia.gov'
      APP_BUILD_PATH = "${WORKSPACE}/front-end-service"
      APP_IMAGE = "image-registry.openshift-image-registry.svc:5000/${NAMESPACE}/${APP_NAME}"
   }
   
  stages {
    
    stage('Build Java Application') {
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
    
   stage('OWASP Dependency-Check Vulnerabilities') {
      steps {
		script{
         dir("${APP_BUILD_PATH}"){ 
        	dependencyCheck additionalArguments: ''' 
                    -o './'
                    -s './'
                    -f 'ALL' 
                     --disableKnownExploited 
                    --prettyPrint''', 
                    nvdCredentialsId: 'nvd-api-key-id',
                    odcInstallation: 'OWASP Dependency-Check Vulnerabilities',
                    debug : true
        
        	dependencyCheckPublisher pattern: 'dependency-check-report.xml'
          }
        }
      }
    }
 
    
    stage('Build Container Image') {
      steps {
        echo 'Building image'    
        sh "podman build --no-cache -f Dockerfile --tag ${APP_IMAGE}:"+env.BUILD_NUMBER
      }
    }
    
     stage('Image Security Scan (Trivy)') {
            steps {
                // Run Trivy as a Docker container to scan the built image
             /*   sh '''
                    docker run --rm \\
                      -v /var/run/docker.sock:/var/run/docker.sock \\
                      -v ${WORKSPACE}:/output \\
                      aquasec/trivy:latest image \\
                      --format template \\
                      --template "@/contrib/html.tpl" \\
                      --output /output/trivy-report.html \\
                      myapp:${BUILD_NUMBER}
                '''*/
                //Run Trivy against new image
                //	trivy image --input ${APP_NAME}.${BUILD_NUMBER}.tar --format template --template "@/usr/local/share/html.tpl -o trivy-report.html"
                sh '''
                	podman save --output ${APP_NAME}.${BUILD_NUMBER}.tar ${APP_IMAGE}:${BUILD_NUMBER}
                	ls -l
                 
                	rm -rf ${APP_NAME}.${BUILD_NUMBER}.tar 
                '''
                // Publish the HTML report (requires the Jenkins publishHTML plugin)
                publishHTML(target: [
                    reportName: 'Trivy Scan Report',
                    reportDir: '.',
                    reportFiles: 'trivy-report.html',
                    alwaysLinkToLastBuild: true,
                    keepAll: true
                ])
            }
        }
    
    
    
    
    
    
    
    stage('Authenticate to Internal OCP Registry'){
        steps{
            echo 'Authenticating to Openshift Registry'
            sh 'podman login -u jenkins -p $(oc whoami -t) image-registry.openshift-image-registry.svc:5000 --tls-verify=false'
        }
    }
    stage('Push to Internal OCP Registry') {
      steps {
        echo 'Pushing image'
        sh "podman push ${APP_IMAGE}:"+env.BUILD_NUMBER+ " --tls-verify=false"
      }
    }
    stage('Configure OCP deployment') {
      steps{
        script{
         dir("${WORKSPACE}"){ 
           
           def deploymentYaml = readYaml file: "front-end-deployment.yaml"
           
           echo "deployment yaml: " + deploymentYaml
           echo "image: "+deploymentYaml.spec.template.spec.containers[0].image
           deploymentYaml.spec.template.spec.containers[0].image = "${APP_IMAGE}:"+env.BUILD_NUMBER
           echo "image after update: "+deploymentYaml.spec.template.spec.containers[0].image
           
           writeYaml file: "front-end-deployment.yaml", data: deploymentYaml, overwrite: true
         }
        }
      }
    }
    
    stage('Deploy application on OCP') {
      steps {
       dir("${WORKSPACE}"){ 
         echo '---rolling out application'
         sh "oc apply -f front-end-configmap.yaml"
         sh "oc apply -f front-end-service.yaml"
	     sh "oc apply -f front-end-deployment.yaml"
	     echo '---testing application ...'
	     sh "oc rollout status -n ${NAMESPACE} deployment/${DEPLOYMENT_NAME} --timeout=1m"
       }
      }
    }
    
    stage('Test application on OCP') {
            steps {
                script {
                    def attempt = 0
                    def max_attempts = 5
                    retry(max_attempts) {
                        attempt = attempt+1
                        println "----[INFO] Attempting to connect to demo app, will try for a maximum of ${max_attempts} times and sleep for 30 seconds in between"
                        sleep(time: 30, unit: 'SECONDS')
                        httpRequest url: "https://ais-demo-frontend-ais-service-demo.apps.ais-ocp-nonprod.cov.virginia.gov/ais-demo-app/",
                        validResponseCodes: '200',
                        ignoreSslErrors: true,
                        timeout: 10
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
           mail body: "<b>BUILD SUCCESS</b><br><br>Project: ${env.JOB_NAME}<br><br>Build Number: ${env.BUILD_NUMBER} <br><br> URL of build: <a href=\"${env.BUILD_URL}\">${env.BUILD_URL}</a>", charset: 'UTF-8', from: 'ais-jenkis<no-reply@cov.virginia.gov>', mimeType: 'text/html', subject: "Jenkins BUILD SUCCESS: ${env.JOB_NAME}", to: "${EMAIL_RECIPENT}";
         }  
         failure {  
           echo 'Sending failure build message'  
           mail body: "<b>BUILD FAILURE</b><br><br>Project: ${env.JOB_NAME}<br><br>Build Number: ${env.BUILD_NUMBER} <br><br> URL of build: <a href=\"${env.BUILD_URL}\">${env.BUILD_URL}</a>", charset: 'UTF-8', from: 'ais-jenkis<no-reply@cov.virginia.gov>', mimeType: 'text/html', subject: "Jenkins BUILD FAILURE: ${env.JOB_NAME}", to: "${EMAIL_RECIPENT}";
         }   
     }
  
  
 }