package com.esempla.proxy.config;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MinioConfiguration {
   private final MinioProperties minioProperties;

    public MinioConfiguration(MinioProperties minioProperties) {
        this.minioProperties = minioProperties;
    }

    @Bean
    public MinioClient minioClient() {
        return new MinioClient.Builder()
                .credentials(minioProperties.accessKey(), minioProperties.secretKey())
                .endpoint("http://127.0.0.1:9000")
                .build();
    }

    @Bean
    public RestTemplate restTemplate()
    {
        return new RestTemplate();
    }
}