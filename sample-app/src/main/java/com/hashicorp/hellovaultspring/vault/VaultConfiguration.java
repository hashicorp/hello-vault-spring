package com.hashicorp.hellovaultspring.vault;

import org.springframework.context.annotation.Configuration;
import org.springframework.vault.authentication.AppRoleAuthentication;
import org.springframework.vault.authentication.AppRoleAuthenticationOptions;
import org.springframework.vault.authentication.AppRoleAuthenticationOptions.SecretId;
import org.springframework.vault.authentication.AppRoleAuthenticationOptions.RoleId;
import org.springframework.vault.authentication.ClientAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.config.AbstractVaultConfiguration;
import org.springframework.vault.support.VaultToken;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;

@Configuration
public class VaultConfiguration extends AbstractVaultConfiguration {

    private final String vaultAddress = System.getenv("VAULT_ADDRESS");
    private final String appRoleId    = System.getenv("VAULT_APPROLE_ROLE_ID");
    private final String secretIdFile = System.getenv("VAULT_APPROLE_SECRET_ID_FILE");


    @Override
    public VaultEndpoint vaultEndpoint() {
        return VaultEndpoint.from(URI.create(vaultAddress));
    }

    @Override
    public ClientAuthentication clientAuthentication() {
        // Fetch our wrapped Secret ID from a file our trusted orchestrator prepared for us
        // and use it to populate our App Role credentials
        // https://docs.spring.io/spring-vault/docs/current/reference/html/#vault.core.authentication
        // https://docs.spring.io/spring-vault/docs/current/reference/html/#vault.authentication.approle
        VaultToken wrappedToken = VaultToken.of(getSecretIDFromFilePath(secretIdFile));

        AppRoleAuthenticationOptions options = AppRoleAuthenticationOptions
                .builder()
                .secretId(SecretId.wrapped(wrappedToken))
                .roleId(RoleId.provided(appRoleId))
                .build();

        return new AppRoleAuthentication(options, restOperations());
    }

    private static String getSecretIDFromFilePath(String p) {
        try {
            return Files.readString(Path.of(p)).trim();
        } catch (IOException e) {
            throw new RuntimeException("could not fetch secret id from file", e);
        }
    }
}
