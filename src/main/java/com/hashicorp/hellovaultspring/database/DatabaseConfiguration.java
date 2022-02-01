package com.hashicorp.hellovaultspring.database;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.vault.core.VaultKeyValueOperations;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.core.VaultKeyValueOperationsSupport.KeyValueBackend;

@Primary
@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
public class DatabaseConfiguration extends DataSourceProperties {

    private final String databaseCredentialsPath = System.getenv("VAULT_DATABASE_CREDENTIALS_PATH");

    @Autowired
    private VaultTemplate vaultTemplate;

    @Override
    public void afterPropertiesSet() throws Exception {

        final VaultKeyValueOperations operations = vaultTemplate.opsForKeyValue(
            "database",
            KeyValueBackend.KV_1
        );

        final Map<String, Object> data = operations.get(databaseCredentialsPath).getData();

        this.setUsername((String) data.get("username"));
        this.setPassword((String) data.get("password"));

        super.afterPropertiesSet();
    }
}
