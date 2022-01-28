package com.hashicorp.hellovaultspring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.vault.core.VaultKeyValueOperationsSupport;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponse;
import org.springframework.vault.support.Versioned;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Objects;


@RestController
public class PaymentsController {
    private final String secureServiceAddress = System.getenv("SECURE_SERVICE_ADDRESS");
    private final String kvMountPath          = System.getenv("VAULT_KV2_MOUNT");
    private final String apiKeyPath           = System.getenv("VAULT_API_KEY_PATH");
    private final String apiKeyField          = System.getenv("VAULT_API_KEY_FIELD");

    private WebClient secureServiceClient;

    @Autowired
    private VaultTemplate vaultTemplate;

    @PostConstruct
    private void initClient() {
        secureServiceClient = WebClient.create(secureServiceAddress);
    }

    @PostMapping("/payments")
    @ResponseBody
    public ResponseEntity<?> postPayment() {
        Versioned<Map<String, Object>> response = vaultTemplate
                .opsForVersionedKeyValue(kvMountPath)
                .get(apiKeyPath);

        assert response != null;

        if ( response.hasData() ) {

            String apiKey = (String) Objects.requireNonNull(response.getData()).get(apiKeyField);

            return new ResponseEntity<>(secureServiceClient
                    .get()
                    .header("X-API-KEY", apiKey)
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block()
                    , HttpStatus.OK);
        }

        return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
    }
}
