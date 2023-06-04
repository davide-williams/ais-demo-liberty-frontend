cat <<EOF |oc apply -f - 
kind: ConfigMap
apiVersion: v1
metadata:
  name: aid-demo-frontend-configmap
  namespace: ais-service-demo
data:
  # Configuration values can be set as key-value properties
  BES_ODM_SERVER: "http://odm-eval-ibm-odm-dev:9060"
  BES_ODM_RULESET: bes_deployment/bes_decision_service
  BES_MQ_SERVER: ais-demo-ibm-mq
  BES_MQ_PORT: "1414"
  BES_MQ_QMGR: DEMO_QMGR
---
apiVersion: v1
kind: Service
metadata:
  name: $SVC_NAME
  namespace: ais-service-demo
  labels:
    app: ais-demo-frontend
spec:
  ports:
  - name: http
    port: 9080
    protocol: TCP
    targetPort: 9080
  - name: https
    port: 9443
    protocol: TCP
    targetPort: 9443
  selector:
    app: ais-demo-frontend-app
---
apiVersion: apps/v1
kind: Deployment
metadata:
  namespace: ais-service-demo
  name: ais-demo-frontend-app
  labels:
    app: ais-demo-frontend-app
spec:
  replicas: 1
  selector:
    matchLabels:
      app: ais-demo-frontend-app
  template:
    metadata:
      labels:
        app: ais-demo-frontend-app
    spec:
      containers:
        - name: ais-demo-frontend-app-container
          image: image-registry.openshift-image-registry.svc:5000/ais-service-demo/$APP_NAME:$BUILD_NUMBER
          imagePullPolicy: IfNotPresent
          envFrom:
          - configMapRef:
              name: aid-demo-frontend-configmap
          ports:
          - name: https
            containerPort: 9443
          - name: http
            containerPort: 9080
          resources:
            limits:
              cpu: 500m
              memory: 512Mi
            requests:
              cpu: 250m
              memory: 128Mi