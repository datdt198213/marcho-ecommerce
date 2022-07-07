package com.ncc;

import com.ncc.config.FileStorageConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EnableConfigurationProperties({
        FileStorageConfig.class
})
@EnableCaching
public class EcommerceApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(EcommerceApplication.class, args);
    }

}
