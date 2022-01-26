package com.hashicorp.hellovaultspring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.vault.core.VaultKeyValueOperationsSupport;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PaymentsController {
    @Autowired
    private VaultTemplate vaultTemplate;

    @PostMapping("/payments")
    @ResponseBody
    public ResponseEntity postPayment() {
        VaultResponse response = vaultTemplate
                .opsForKeyValue("kv-v2", VaultKeyValueOperationsSupport.KeyValueBackend.KV_2)
                .get("api-key");

        if ( response != null ) {
            return new ResponseEntity<>(response.getData(), HttpStatus.OK);
        }

        else {
            return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
        }

    }
}
