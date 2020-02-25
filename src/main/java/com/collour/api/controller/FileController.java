package com.collour.api.controller;

import com.collour.api.dto.FileUploadDTO;
import com.collour.api.service.FileSystemStorageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

//TODO: 20200113, junhan, Remove this controller.
@RestController
@RequestMapping(value = "/file/")
@AllArgsConstructor
public class FileController {

    private final FileSystemStorageService fileSystemStorageService;


    @PostMapping(value = "/files")
    public ResponseEntity<List<FileUploadDTO>> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return ResponseEntity.ok(fileSystemStorageService.uploadMultipleFiles(files));
    }
}
