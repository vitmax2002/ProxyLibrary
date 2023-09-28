package com.esempla.proxy.controller;

import com.esempla.proxy.model.dto.FileDto;
import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class MinioController {

    private final Logger log = LoggerFactory.getLogger(MinioController.class);

    private final MinioClient minioClient;

    public MinioController(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    @PostMapping("/object/upload")
    public void uploadFile(@RequestBody FileDto dto) throws IOException,
            ServerException,
            InsufficientDataException,
            ErrorResponseException,
            NoSuchAlgorithmException,
            InvalidKeyException,
            InvalidResponseException, XmlParserException, InternalException {

        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(dto.bucketName())
                        .object(dto.objectName())
                        .stream(new ByteArrayInputStream(dto.data()), dto.data().length, -1)
                        .build());

        log.info("succesfully uploaded");
    }


    @PutMapping("/object/download/{bucketName}/{objectName}")
    public ResponseEntity<byte[]> downloadObject(@PathVariable String bucketName, @PathVariable String objectName) throws IOException,
            ServerException,
            InsufficientDataException,
            ErrorResponseException,
            NoSuchAlgorithmException,
            InvalidKeyException,
            InvalidResponseException,
            XmlParserException,
            InternalException {

        GetObjectResponse objectStream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .build());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", objectName);
        return ResponseEntity.ok()
                .headers(headers)
                .body(objectStream.object().getBytes());
    }

    @DeleteMapping("/delete/{bucketName}/{objectName}")
    public ResponseEntity<Void> deleteObject(
            @PathVariable String bucketName,
            @PathVariable String objectName) throws ServerException,
            InsufficientDataException,
            ErrorResponseException,
            IOException,
            NoSuchAlgorithmException,
            InvalidKeyException,
            InvalidResponseException,
            XmlParserException,
            InternalException {
        RemoveObjectArgs args= RemoveObjectArgs
                .builder()
                .bucket(bucketName)
                .object(objectName)
                .build();

        minioClient.removeObject(args);
        log.debug("object was removed");
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/objects/show")
    public ResponseEntity<List<String>> getObjects(@RequestParam String bucketName) throws ServerException,
            InsufficientDataException,
            ErrorResponseException,
            IOException,
            NoSuchAlgorithmException,
            InvalidKeyException,
            InvalidResponseException,
            XmlParserException,
            InternalException {
        Iterable<Result<Item>> results = minioClient.listObjects(
                ListObjectsArgs.builder().bucket(bucketName).build());

        List<String> objectNames = new ArrayList<>();
        for (Result<Item> result : results) {
            objectNames.add(result.get().objectName());
        }
        return ResponseEntity.ok().body(objectNames);
    }
}