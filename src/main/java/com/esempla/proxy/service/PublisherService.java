package com.esempla.proxy.service;

import com.esempla.proxy.wsdl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;


public class PublisherService extends WebServiceGatewaySupport {

    private static final Logger log = LoggerFactory.getLogger(PublisherService.class);

    public GetPublisherResponse getPublisher(int id) {

        GetPublisherRequest request = new GetPublisherRequest();
        request.setId(id);

        log.info("Idiul a fost setat " + id);

        GetPublisherResponse response = (GetPublisherResponse) getWebServiceTemplate()
                .marshalSendAndReceive("http://localhost:8080/ws/publishers", request,
                        new SoapActionCallback(
                                "http://www.library.com/spring/soap/api/soap/GetPublisherRequest"));

        return response;
    }

}