package com.serrodcal.esb.custom.handler;

import org.apache.axis2.addressing.EndpointReference;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.AbstractSynapseHandler;
import org.apache.synapse.MessageContext;
import org.apache.synapse.core.axis2.Axis2MessageContext;

import java.util.TreeMap;

public class LoggerCustomHandler extends AbstractSynapseHandler {

    private static final Log log = LogFactory.getLog("CUSTOM_LOGGER");//  getLog(LoggerCustomHandler.class);

    public static final String API = "API";
    public static final String IP = "IP";
    public static final String X_ING_TRC_REQUEST = "Request";

    @Override
    public boolean handleRequestInFlow(MessageContext messageContext) {

        TreeMap headers_map = this.getHeaders(messageContext);

        if(headers_map != null) {
            StringBuilder log_statement = new StringBuilder();

            log_statement.append("*** INSIDE = {")
                    .append("[IP:").append(headers_map.get("X-Forwarded-For")).append("],[")
                    .append(X_ING_TRC_REQUEST).append(":").append(headers_map.get(X_ING_TRC_REQUEST)).append("],")
                    .append("[API:").append(this.getTo(messageContext)).append("],")
                    .append("[UUID:").append(messageContext.getMessageID()).append("]").append("}");

            this.log.info(log_statement.toString());
        } else {
            this.log.error("Something was wrong, not be able to access to headers :(.");
        }

        return true;
    }

    @Override
    public boolean handleRequestOutFlow(MessageContext messageContext) {

        TreeMap headers_map = this.getHeaders(messageContext);

        if(headers_map != null) {
            StringBuilder log_statement = new StringBuilder();

            log_statement.append("*** SEND TO ENDPOINT = {")
                    .append("[IP:").append(headers_map.get("X-Forwarded-For")).append("],")
                    .append("[" + X_ING_TRC_REQUEST + ":").append(headers_map.get(X_ING_TRC_REQUEST)).append("],")
                    .append("[API:").append(this.getTo(messageContext)).append("],")
                    .append("[UUID:").append(messageContext.getMessageID()).append("],")
                    .append("[To:").append(messageContext.getTo().getAddress()).append("]").append("}");

            this.log.info(log_statement.toString());
        } else {
            this.log.error("Something was wrong, not be able to access to headers :(.");
        }

        return true;
    }

    @Override
    public boolean handleResponseInFlow(MessageContext messageContext) {

        TreeMap headers_map = this.getHeaders(messageContext);

        if(headers_map != null) {
            StringBuilder log_statement = new StringBuilder();

            log_statement.append("*** RECEIVED FROM ENDPOINT = {")
                    .append("[IP:").append(headers_map.get("X-Forwarded-For")).append("],")
                    .append("[" + X_ING_TRC_REQUEST + ":").append(headers_map.get(X_ING_TRC_REQUEST)).append("],")
                    .append("[API:").append(this.getFrom(messageContext)).append("],")
                    .append("[UUID:").append(messageContext.getMessageID()).append("],")
                    .append("[From:").append(messageContext.getFrom().getAddress()).append("]").append("}");

            this.log.info(log_statement.toString());
        } else {
            this.log.error("Something was wrong, not be able to access to headers :(.");
        }

        return true;
    }

    @Override
    public boolean handleResponseOutFlow(MessageContext messageContext) {

        TreeMap headers_map = this.getHeaders(messageContext);

        if(headers_map != null) {
            StringBuilder log_statement = new StringBuilder();

            log_statement.append("*** OUTSIDE = {")
                    .append("[IP:").append(headers_map.get("X-Forwarded-For")).append("],")
                    .append("[" + X_ING_TRC_REQUEST + ":").append(headers_map.get(X_ING_TRC_REQUEST)).append("],")
                    .append("[API:").append(this.getFrom(messageContext)).append("],")
                    .append("[UUID:").append(messageContext.getMessageID()).append("]").append("}");

            this.log.info(log_statement.toString());
        } else {
            this.log.error("Something was wrong, not be able to access to headers :(.");
        }


        return true;
    }

    private TreeMap getHeaders(MessageContext messageContext) {
        org.apache.axis2.context.MessageContext axis2MessageContext = ((Axis2MessageContext) messageContext)
                .getAxis2MessageContext();
        Object headers = axis2MessageContext.getProperty("TRANSPORT_HEADERS");
        if( headers instanceof TreeMap ) {
            return ((TreeMap) headers);
        }else{
            return null;
        }
    }

    private String getTo(MessageContext messageContext) {
        EndpointReference er = messageContext.getTo();
        if (er != null) {
            return er.getAddress();
        }
        return "null";
    }

    private String getFrom(MessageContext messageContext) {
        EndpointReference er = messageContext.getFrom();
        if (er != null) {
            return er.getAddress();
        }
        return "null";
    }

}