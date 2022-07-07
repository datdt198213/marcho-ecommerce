package com.ncc.service.file;

import com.ncc.config.FileStorageConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {
    private FileStorageConfig fileStorageConfig;
    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageConfig fileStorageConfig) {
        this.fileStorageLocation = Paths
                .get(fileStorageConfig.getUploadDir())
                .toAbsolutePath()
                .normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String storeFile(String folderName, MultipartFile multipartFile) {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        try {
            if (fileName.contains(".."))
                throw new RuntimeException("File name contains invalid path sequence" + fileName);
            Path targetLocation = this.fileStorageLocation.resolve(folderName + "\\" + fileName);
            Files.copy(multipartFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            throw new RuntimeException("Could not store file " + fileName, e);
        }

        return fileName;
    }

    public Resource loadFileAsResource(String folderName, String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(folderName + "\\" + fileName);
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists())
                return resource;
            else
                throw new RuntimeException("File not found " + fileName);
        } catch (MalformedURLException e) {
            throw new RuntimeException("File not found " + fileName, e);
        }
    }
}
