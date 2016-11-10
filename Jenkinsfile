node {
stage('SCM') {
git 'https://github.com/aasdkl/RMS.git/'
}
stage('QA') {
sh 'sonar-scanner'
}
stage('build') {
def mvnHome = tool 'M3'
sh "${mvnHome}/bin/mvn -B clean package"
}
stage('deploy') {
sh "docker restart db001 || true"
sh "docker stop tomcat001 || true"
sh "docker rm tomcat001 || true"
sh "docker run --name tomcat001 -p 11111:8080 -d --link db001:tomysql tomcat:8.5"
sh "docker cp target/RMS.war tomcat001:/usr/local/tomcat/webapps"
}
stage('results') {
archiveArtifacts artifacts: '**/target/*.war', fingerprint: true
}
}
