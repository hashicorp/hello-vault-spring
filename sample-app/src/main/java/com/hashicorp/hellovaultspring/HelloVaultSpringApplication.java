/*
 * Copyright IBM Corp. 2022, 2024
 * SPDX-License-Identifier: MPL-2.0
 */

package com.hashicorp.hellovaultspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HelloVaultSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloVaultSpringApplication.class, args);
    }

}
