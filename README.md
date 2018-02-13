# Custom Handler PoC

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


