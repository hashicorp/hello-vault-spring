package com.hashicorp.hellovaultspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@SpringBootApplication
public class HelloVaultSpringApplication {

    public static void main(String[] args) {
        setSecretID();
        SpringApplication.run(HelloVaultSpringApplication.class, args);
    }

    private static void setSecretID() {
        try {
            String secretID = Files.readString(Path.of(System.getenv("VAULT_APPROLE_SECRET_ID_FILE"))).trim();
            System.setProperty("spring.cloud.vault.token", secretID);
        } catch (IOException e) {
            throw new RuntimeException("could not fetch secret id from file", e);
        }
    }

}
