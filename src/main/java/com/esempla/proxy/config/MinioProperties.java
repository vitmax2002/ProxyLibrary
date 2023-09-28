package com.esempla.proxy.config;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "minio", ignoreUnknownFields = false)
public record MinioProperties( @NotEmpty String accessKey,
                               @NotEmpty String secretKey,
                               @NotEmpty String minioUrl,
                               @NotEmpty String bucketName,
                               @NotEmpty String defaultFolder ){

}
