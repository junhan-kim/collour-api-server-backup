package com.collour.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EntityScan(
        basePackageClasses = {Jsr310JpaConverters.class},
        basePackages = {"com.collour.api.domain"})
@EnableJpaAuditing
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }
}
