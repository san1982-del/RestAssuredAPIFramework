pipeline {
    agent any
    
    tools {
        maven 'maven'
    }
    
    stages {
        stage('Build') {
            steps {
                dir('build-repo') {
                    git 'https://github.com/jglick/simple-maven-project-with-tests.git'
                    bat 'mvn -Dmaven.test.failure.ignore=true clean package'
                }
            }
            post {
                success {
                    junit 'build-repo/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'build-repo/target/*.jar'
                }
            }
        }
        
        stage('Deploy to Dev') {
            steps {
                echo 'deploy to Dev'
            }
        }
        
        stage('Sanity API Automation Test on DEV') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    dir('api-tests') {
                        git branch: 'main', url: 'https://github.com/san1982-del/RestAssuredAPIFramework.git'
                        bat 'mvn clean install -DsuiteXmlFile=src/test/resources/testrunners/testng_sanity.xml'
                    }
                }
            }
        }
        
        stage('Deploy to QA') {
            steps {
                echo 'deploy to qa done'
            }
        }
        
        stage('Regression API Automation Tests on QA') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    dir('api-tests') {
                        git branch: 'main', url: 'https://github.com/san1982-del/RestAssuredAPIFramework.git'
                        bat 'mvn clean install -DsuiteXmlFile=src/test/resources/testrunners/testng_regression.xml'
                    }
                }
            }
        }
        
        stage('Publish Allure Reports') {
            steps {
                script {
                    allure([
                        includeProperties: false,
                        jdk: '',
                        properties: [],
                        reportBuildPolicy: 'ALWAYS',
                        results: [[path: 'api-tests/allure-results']]
                    ])
                }
            }
        }
        
        stage('Publish ChainTest Report') {
            steps {
                publishHTML([
                    allowMissing: true,
                    alwaysLinkToLastBuild: false,
                    keepAll: true,
                    reportDir: 'api-tests/target/chaintest',
                    reportFiles: 'index.html',
                    reportName: 'HTML API Regression ChainTest Report',
                    reportTitles: ''
                ])
            }
        }
        
        stage('Deploy to Stage') {
            steps {
                echo 'deploy to Stage'
            }
        }
        
        stage('Sanity API Automation Test on Stage') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    dir('api-tests') {
                        git branch: 'main', url: 'https://github.com/san1982-del/RestAssuredAPIFramework.git'
                        bat 'mvn clean install -DsuiteXmlFile=src/test/resources/testrunners/testng_sanity.xml'
                    }
                }
            }
        }
        
        stage('Publish Sanity ChainTest Report') {
            steps {
                publishHTML([
                    allowMissing: true,
                    alwaysLinkToLastBuild: false,
                    keepAll: true,
                    reportDir: 'api-tests/target/chaintest',
                    reportFiles: 'index.html',
                    reportName: 'HTML API Sanity ChainTest Report',
                    reportTitles: ''
                ])
            }
        }
        
        stage('Deploy to PROD') {
            steps {
                echo 'deploy to PROD'
            }
        }
        
        stage('Sanity API Automation Test on PROD') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    dir('api-tests') {
                        git branch: 'main', url: 'https://github.com/san1982-del/RestAssuredAPIFramework.git'
                        bat 'mvn clean install -DsuiteXmlFile=src/test/resources/testrunners/testng_sanity.xml'
                    }
                }
            }
        }
    }
}