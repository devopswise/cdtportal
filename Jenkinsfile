node('jenkins-ssh-slave-docker-cli') {

    def app

    stage('Clone repository') {
        /* Let's make sure we have the repository cloned to our workspace */
        checkout scm
    }

    stage('Maven build'){
        withDockerContainer('maven:3.6-jdk-8-slim') {
          sh 'mvn package'
        }
    }
    
    stage('debug'){
        sh 'sleep infinity'
    }
    
    stage('Build image') {
        /* This builds the actual image; synonymous to
         * docker build on the command line */
        app = docker.build("devopswise/cdtportal")
    }

    stage('Push image') {
        /* Finally, we'll push the image with two tags:
         * First, the incremental build number from Jenkins
         * Second, the 'latest' tag.
         * Pushing multiple tags is cheap, as all the layers are reused. */
        docker.withRegistry('https://index.docker.io/v1/', 'devopswise-dockerhub') {
            app.push("0.8.${env.BUILD_NUMBER}")
            app.push("latest")
        }
    }
}