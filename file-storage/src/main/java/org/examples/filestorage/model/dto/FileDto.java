package org.examples.filestorage.model.dto;

public record FileDto(String name,
        String contentType,
        int size,
        byte[] content) {

}
