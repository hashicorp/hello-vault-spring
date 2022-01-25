package com.hashicorp.hellovaultspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@SpringBootApplication
public class HelloVaultSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloVaultSpringApplication.class, args);
    }

    @PostConstruct
    private void setSecretID() {
        try {
            String secretID = Files.readString(Path.of(System.getenv("VAULT_APPROLE_SECRET_ID_FILE"))).trim();
            System.setProperty("spring.cloud.vault.app-role.secret-id", secretID);
        } catch (IOException e) {
            throw new RuntimeException("could not fetch secret id from file", e);
        }
    }
}
