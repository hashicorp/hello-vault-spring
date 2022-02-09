package com.hashicorp.hellovaultspring.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.vault.annotation.VaultPropertySource;

@Primary
@Configuration
// @VaultPropertySource provides a mechanism for adding data retrieved from Vault to Spring's Environment
// https://docs.spring.io/spring-vault/docs/current/reference/html/#vault.core.propertysupport
// https://docs.spring.io/spring-vault/docs/current/api/org/springframework/vault/annotation/VaultPropertySource.html
@VaultPropertySource(value = "database/creds/dev-readonly", renewal = VaultPropertySource.Renewal.RENEW)
@ConfigurationProperties(prefix = "spring.datasource")
public class DatabaseConfiguration extends DataSourceProperties {

    @Autowired
    Environment env;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.setUsername(env.getProperty("username"));
        this.setPassword(env.getProperty("password"));

        super.afterPropertiesSet();
    }
}
