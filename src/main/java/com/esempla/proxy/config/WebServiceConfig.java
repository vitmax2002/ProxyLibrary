package com.esempla.proxy.config;


import com.esempla.proxy.service.PublisherService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;




@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {
    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // this package must match the package in the <generatePackage> specified in
        // pom.xml
        marshaller.setContextPath("com.esempla.proxy.wsdl");
        return marshaller;
    }

    @Bean
    public PublisherService countryClient(Jaxb2Marshaller marshaller) {
        PublisherService client = new PublisherService();
        client.setDefaultUri("http://localhost:8080/ws");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

}