package com.collour.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.core.io.Resource;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FileDownloadDTO {

    private String type;
    private Resource resource;
}