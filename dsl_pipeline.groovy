folder('DEVOPS_PROJECT') {
        description('hpsim project foloder created')
}
freeStyleJob('DEVOPS_PROJECT/compile') {
    logRotator(-1, 10)
    scm {
        github('snagarajudmm/myweb', 'master')
    }
    steps {
        maven('clean compile')
    }
    publishers {
        downstream('DEVOPS_PROJECT/test', 'SUCCESS')
    }
}
mavenJob('DEVOPS_PROJECT/test') {
    logRotator(-1, 10)
     scm {
        github('snagarajudmm/myweb', 'master')
    }
    goals('clean test')
   
   publishers {
        downstream('DEVOPS_PROJECT/sonar', 'SUCCESS')
    }
}
mavenJob('DEVOPS_PROJECT/sonar') {
    logRotator(-1, 10)
     scm {
        github('snagarajudmm/myweb', 'master')
    }
    goals('clean sonar:sonar')
  publishers {
        downstream('DEVOPS_PROJECT/nexus', 'SUCCESS')
    }
}
mavenJob('DEVOPS_PROJECT/nexus') {
    logRotator(-1, 10)
     scm {
        github('snagarajudmm/myweb', 'master')
    }
    goals('clean deploy')
}
buildPipelineView('DEVOPS_PROJECT/build-pipeline') {
    filterBuildQueue()
    filterExecutors()
    
    displayedBuilds(5)
    selectedJob('DEVOPS_PROJECT/compile')
    alwaysAllowManualTrigger()
    showPipelineParameters()
    refreshFrequency(60)
}
