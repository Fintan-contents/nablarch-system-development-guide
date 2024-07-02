FROM maven:3.6.1-jdk-8
COPY . /usr/src/app
WORKDIR /usr/src/app
EXPOSE 3333

CMD sleep 10 ; \
cd project; \
mvn -P gsp gsp-dba:generate-ddl gsp-dba:execute-ddl gsp-dba:load-data -pl proman-common -s ci/settings.xml ; \
cd ../; \
java -jar $(find . -name *standalone.jar) -d webapp/ -p 3333