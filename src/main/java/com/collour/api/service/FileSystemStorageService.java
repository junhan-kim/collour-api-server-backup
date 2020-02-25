package com.collour.api.service;

import com.collour.api.dto.ExceptionDTO;
import com.collour.api.dto.FileDownloadDTO;
import com.collour.api.dto.FileUploadDTO;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public interface FileSystemStorageService {

    String store(MultipartFile file);

    String store(MultipartFile file, String name);

    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename) throws Exception;

    boolean delete(String filePath) throws IOException;

    void deleteAll();


    FileDownloadDTO downloadFile(String filename) throws Exception;


    String getUri(String name);

    FileUploadDTO uploadFile(MultipartFile file);

    FileUploadDTO uploadFile(MultipartFile file, String name);

    FileUploadDTO uploadUserPhotoByUsername(MultipartFile file, String username);


    List<FileUploadDTO> uploadMultipleFiles(MultipartFile[] files);
}
