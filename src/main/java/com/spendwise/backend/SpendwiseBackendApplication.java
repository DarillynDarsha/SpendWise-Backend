package com.spendwise.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.spendwise")

@EntityScan(basePackages = "com.spendwise")

@EnableJpaRepositories(basePackages = "com.spendwise")

public class SpendwiseBackendApplication {

    public static void main(String[] args) {

        SpringApplication.run(
                SpendwiseBackendApplication.class,
                args
        );
    }
}