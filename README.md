# Custom Handler PoC

[![Build Status](https://travis-ci.org/serrodcal/CustomHandlerPoC.svg?branch=master)](https://travis-ci.org/serrodcal/CustomHandlerPoC)

This project consists of a Proof of Concept about extending ESB by a custom synapse handler. 
In this case, the handler only log info about the request/response.

## Build

Run `mvn clean install` to build the OSGi Bundler.

## Install

Put JAR file (in target directory after building) into `<ESB_HOM>/repository/components/dropins`.

## Docker

Create new folder named `esb` and put into that folder `wso2esb-5.0.0.zip` file downloaded from WSO2.

After that, use scripts from `scripts` folder to build and run. Check logs using `scripts/terminal.sh`
and run `tail -f repository/logs/wso2carbon.log`, sending a request as given below:

```
curl -i -X POST \
   -H "Content-Type:application/json" \
   -H "X-ING-TRC-REQUEST:klñjasdf98ynu23978ynxd" \
   -d \
'{
  "field":"value"
}' \
 'http://localhost:8280/echo/echo'
```

## Custom logs

This artifacts has it own logger. Log in wso2carbon.log (root logger) and log in customer_logger.log files.

In `conf/log4j.properties`, is the configuration:

```
log4j.rootLogger=ERROR, CARBON_CONSOLE, CARBON_LOGFILE, CARBON_MEMORY, CARBON_SYS_LOG, ERROR_LOGFILE, CUSTOM_LOGGER

...

log4j.logger.CUSTOM_LOGGER=INFO

...

# Appender config to send LoggerCustomHandler logs to new file.
log4j.appender.CUSTOM_LOGGER = org.apache.log4j.RollingFileAppender
log4j.appender.CUSTOM_LOGGER.layout = org.apache.log4j.PatternLayout
log4j.appender.CUSTOM_LOGGER.layout.ConversionPattern = %d{ISO8601} %-5p %c ~ %m%n
log4j.appender.CUSTOM_LOGGER.File = ${carbon.home}/repository/logs/custom_logger.log
log4j.appender.CUSTOM_LOGGER.Threshold = INFO
log4j.appender.CUSTOM_LOGGER.Append=true
```

## Documentation

* [Apache Felix Bundle](http://felix.apache.org/documentation/subprojects/apache-felix-maven-bundle-plugin-bnd.html).
* [WSO2 Extending Synapse](https://docs.wso2.com/display/ESB490/Writing+a+Synapse+Handler).