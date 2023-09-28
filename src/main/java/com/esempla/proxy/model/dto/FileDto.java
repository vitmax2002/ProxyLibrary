package com.esempla.proxy.model.dto;

public record FileDto(byte [] data,
                      String bucketName,
                      String objectName) {
}
