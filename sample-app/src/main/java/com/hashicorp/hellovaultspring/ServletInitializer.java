/*
 * Copyright IBM Corp. 2022, 2024
 * SPDX-License-Identifier: MPL-2.0
 */

package com.hashicorp.hellovaultspring;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(HelloVaultSpringApplication.class);
    }

}
