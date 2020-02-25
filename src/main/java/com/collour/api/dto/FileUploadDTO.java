package com.collour.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.net.URI;

@AllArgsConstructor
@Getter
public class FileUploadDTO {

    private String name;
    private String uri;
    private String type;
    private long size;
}