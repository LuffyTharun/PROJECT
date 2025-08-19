pipeline {
    agent any

    stages {
        stage('Checkout Code') {
            steps {
                git branch: '<branch_name>', credentialsId: '<credentials_id>', url: '<repository_url>'
            }
        }

        stage('Build Project') {
            steps {
                dir('<path_to_your_project>') {
                    sh './build.sh'
                }
            }
        }

        stage('Run Tests') {
            steps {
                dir('<path_to_your_project>') {
                    sh './test.sh'
                }
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: '**/*.log', fingerprint: true
        }
        success {
            echo 'Build and Tests successful! ✅'
            // Example: mail to team
            // emailext subject: 'Build Successful', body: 'The pipeline completed successfully', to: 'team@example.com'
        }
        failure {
            echo 'Build or Tests failed ❌'
            // Example: mail to team
            // emailext subject: 'Build Failed', body: 'Please check Jenkins logs', to: 'team@example.com'
        }
    }
}
