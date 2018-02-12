FROM airdock/oracle-jdk:jdk-1.8

ADD ./esb/wso2esb-5.0.0.zip /opt/wso2esb-5.0.0.zip

RUN apt-get update && \
    apt-get install -y zip wget vim && \
    apt-get clean && \
    unzip /opt/wso2esb-5.0.0.zip -d /opt && \
    rm /opt/wso2esb-5.0.0.zip

ADD ./api/Echo.xml /opt/wso2esb-5.0.0/repository/deployment/server/synapse-configs/default/api/Echo.xml
ADD ./target/loggerCustomHandler-1.0.0.jar /opt/wso2esb-5.0.0/repository/components/dropins/loggerCustomHandler-1.0.0.jar
ADD ./conf/synapse-handlers.xml /opt/wso2esb-5.0.0/repository/conf/synapse-handlers.xml
ADD ./bin/wso2server.sh /opt/wso2esb-5.0.0/bin/wso2server.sh

EXPOSE 9443 8243 8280 19444
WORKDIR /opt/wso2esb-5.0.0
CMD ["bin/wso2server.sh"]