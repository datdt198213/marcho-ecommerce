package com.ncc.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "file") // file.upload-dir
public class FileStorageConfig {
    private String uploadDir; // java can bind upload-dir to uploadDir
}
