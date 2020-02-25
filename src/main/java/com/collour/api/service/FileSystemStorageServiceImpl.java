package com.collour.api.service;

import com.collour.api.dto.ExceptionDTO;
import com.collour.api.dto.FileDownloadDTO;
import com.collour.api.dto.FileUploadDTO;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FileSystemStorageServiceImpl implements FileSystemStorageService {

    @Value("${storage.location}")
    private Path rootLocation;

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(Paths.get("./" + rootLocation));
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage location", e);
        }
    }


    //TODO:20200111,junhan,Reduce these duplicated codes.
    @Override
    public String store(MultipartFile file) {
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                throw new RuntimeException(
                        "Cannot store file with relative path outside current directory " + filename);
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, this.rootLocation.resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file " + filename, e);
        }
        return filename;
    }

    @Override
    public String store(MultipartFile file, String name) {
        String filename = StringUtils.cleanPath(name + "."
                + Objects.requireNonNull(FilenameUtils.getExtension(file.getOriginalFilename())).toLowerCase());
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                throw new RuntimeException(
                        "Cannot store file with relative path outside current directory " + filename);
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, this.rootLocation.resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file " + filename, e);
        }
        return filename;
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read stored files", e);
        }
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) throws Exception {
        Path file = load(filename);
        Resource resource = new UrlResource(file.toUri());
        if (resource.exists() || resource.isReadable()) {
            return resource;
        } else {
            throw new ExceptionDTO(ExceptionDTO.ErrorCode.FILE_NOT_EXISTS);
        }
    }

    public boolean delete(String filePath) throws IOException {
        return Files.deleteIfExists(Paths.get(rootLocation + "/" + filePath));
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }


    @Override
    public FileDownloadDTO downloadFile(String filename) throws Exception {
        Resource resource = loadAsResource(filename);
        String contentType = Files.probeContentType(Paths.get(resource.getFile().getAbsolutePath()));
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return new FileDownloadDTO(contentType, resource);
    }

    @Override
    public String getUri(String name) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/" + rootLocation)
                .path("/" + name)
                .toUriString();
    }

    @Override
    public FileUploadDTO uploadFile(MultipartFile file) {
        String filename = store(file);
        return new FileUploadDTO(filename, getUri(filename), file.getContentType(), file.getSize());
    }

    @Override
    public FileUploadDTO uploadFile(MultipartFile file, String name) {
        String filename = store(file, name);
        return new FileUploadDTO(filename, getUri(filename), file.getContentType(), file.getSize());
    }

    @Override
    public FileUploadDTO uploadUserPhotoByUsername(MultipartFile file, String username) {
        String filename = store(file, "user_photo_" + username);
        return new FileUploadDTO(filename, getUri(filename), file.getContentType(), file.getSize());
    }


    @Override
    public List<FileUploadDTO> uploadMultipleFiles(MultipartFile[] files) {
        return Arrays.stream(files)
                .map(this::uploadFile)
                .collect(Collectors.toList());
    }
}