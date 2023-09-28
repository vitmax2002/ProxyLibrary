package com.esempla.proxy.controller;


import com.esempla.proxy.service.PublisherService;

import com.esempla.proxy.wsdl.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/publishers")
public class PublisherController {

    private  PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<GetPublisherResponse> getPublisher(@PathVariable int id){
       GetPublisherResponse publisher= publisherService.getPublisher(id);
        return ResponseEntity.ok().body(publisher);
    }
}
