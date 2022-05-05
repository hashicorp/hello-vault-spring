package com.hashicorp.hellovaultspring.controllers;

import org.springframework.boot.system.SystemProperties;
import org.springframework.core.SpringVersion;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthcheckController {
    String healthMsg =
            "        Status: OK\n" +
            "Spring Version: " + SpringVersion.getVersion() + "\n" +
            "   JDK Version: " + SystemProperties.get("java.version") + "\n";

    @GetMapping("/healthcheck")
    public String healthcheck() {
        return healthMsg;
    }

}
