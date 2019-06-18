node('jenkins-jnlp-slave-docker-cli') {

    def app

    stage('Clone repository') {
        /* Let's make sure we have the repository cloned to our workspace */
        checkout scm
    }

    stage('Maven build'){
        withDockerContainer('maven:3.6-jdk-8-slim') {
          sh 'mvn install:install-file -q \
			   -Dfile=./tmp/java-gitea-api-1.7.0-SNAPSHOT.jar \
			   -DgroupId=com.github.zeripath \
			   -DartifactId=java-gitea-api \
			   -Dversion=1.7.0-SNAPSHOT \
			   -Dpackaging=jar \
			   -DgeneratePom=true'
          sh 'mvn package -q'
        }
    }

    //stage('debug'){
    //    sh 'sleep infinity'
    //}

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
            app.push("0.9.${env.BRANCH_NAME}.${env.BUILD_NUMBER}")
            if (env.BRANCH_NAME == 'master') {
                app.push("latest")
            } else {
                app.push(env.BRANCH_NAME)
            }
        }
    }
}
