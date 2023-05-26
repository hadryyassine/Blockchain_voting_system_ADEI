pipeline{
    agent any
    stages {
        stage('Clone the project'){
            steps{
                git branch: 'main', url: 'https://github.com/hadryyassine/Blockchain_voting_system_ADEI.git'
            }
        }
        stage('Build'){
            steps {
                dir("BackendofADEIVotechain"){
                    sh "chmod 777 ./mvnw"
                    sh "./mvnw clean install -DskipTests"
                }
                
            }
        }
    }
}
