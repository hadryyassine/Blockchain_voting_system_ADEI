pipeline{
    agent any
    stages {
        stage('Clone the project'){
            steps{
                git branch: 'main', url: 'https://github.com/HossameTor/VotingSystemBackend.git'
            }
        }
        stage('Build'){
            steps {
                sh "chmod 777 ./mvnw"
                sh "./mvnw clean install -DskipTests"
            }
        }
    }
}
