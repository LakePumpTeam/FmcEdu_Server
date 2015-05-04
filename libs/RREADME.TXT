Due to some jar files we can not obtain from the maven center repository, so we have to install these jar files to the local repository.
please run the bellow script in the command line window to install jar files to local repository:
mvn install:install -file -Dfile=${the jar file path} -DgroupId=${group id} -DartifactId=${artifact id} -Dversion=${version} -Dpackaging=jar
e.g:
Install bccs-api-lib.jar to local repository:
mvn install:install-file -Dfile=d:\bccs-api-lib-1.1.2.jar -DgroupId=baidu -DartifactId=bacc-api-lib -Dversion=1.1.2 -Dpackaging=jar