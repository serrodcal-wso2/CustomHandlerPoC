package com.serrodcal.esb.custom.handler;

import org.apache.axis2.addressing.EndpointReference;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.AbstractSynapseHandler;
import org.apache.synapse.MessageContext;

import org.apache.synapse.core.axis2.Axis2MessageContext;

import java.util.TreeMap;
/*import brave.Tracing;
import brave.opentracing.BraveTracer;
import com.google.common.collect.ImmutableSet;
import io.opentracing.Tracer;
import zipkin2.reporter.AsyncReporter;
import zipkin2.reporter.Reporter;
import zipkin2.reporter.Sender;
import zipkin2.reporter.okhttp3.OkHttpSender;*/

//import java.awt.*;

public class LoggerCustomHandler extends AbstractSynapseHandler {

    private static final Log log = LogFactory.getLog(LoggerCustomHandler.class);

    @Override
    public boolean handleRequestInFlow(MessageContext messageContext) {

        TreeMap headers_map = this.getHeaders(messageContext);

        if(headers_map != null) {
            StringBuilder log_statement = new StringBuilder();

            log_statement.append("*** INSIDE = {")
                    .append("[IP:").append(headers_map.get("X-Forwarded-For")).append("],")
                    .append("[X-ING-TRC-REQUEST:").append(headers_map.get("X-ING-TRC-REQUEST")).append("],")
                    .append("[API:").append(this.getTo(messageContext)).append("],")
                    .append("[UUID:").append(messageContext.getMessageID()).append("]").append("}");

            this.log.error(log_statement.toString());
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
                    .append("[X-ING-TRC-REQUEST:").append(headers_map.get("X-ING-TRC-REQUEST")).append("],")
                    .append("[API:").append(this.getTo(messageContext)).append("],")
                    .append("[UUID:").append(messageContext.getMessageID()).append("],")
                    .append("[To:").append(messageContext.getTo().getAddress()).append("]").append("}");

            this.log.error(log_statement.toString());
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
                    .append("[X-ING-TRC-REQUEST:").append(headers_map.get("X-ING-TRC-REQUEST")).append("],")
                    .append("[API:").append(this.getFrom(messageContext)).append("],")
                    .append("[UUID:").append(messageContext.getMessageID()).append("],")
                    .append("[From:").append(messageContext.getFrom().getAddress()).append("]").append("}");

            this.log.error(log_statement.toString());
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
                    .append("[X-ING-TRC-REQUEST:").append(headers_map.get("X-ING-TRC-REQUEST")).append("],")
                    .append("[API:").append(this.getFrom(messageContext)).append("],")
                    .append("[UUID:").append(messageContext.getMessageID()).append("]").append("}");

            this.log.error(log_statement.toString());
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


/*this.log.error("zipkin");
        Sender sender = OkHttpSender.create("http://zipkin:9411/api/v2/spans");
        Reporter spanReporter = AsyncReporter.create(sender);
        Tracing braveTracing = Tracing.newBuilder().localServiceName("formatter").spanReporter(spanReporter).build();
        Tracer tracer = BraveTracer.create(braveTracing);
        ImmutableSet<Color> google_color = ImmutableSet.<Color>builder()
                        .add(new Color(0, 191, 255))
                        .build();
        this.log.error(google_color.toString());
        this.log.error(messageContext.getContextEntries().toString());
        this.log.error("handleRequestInFlow");*/