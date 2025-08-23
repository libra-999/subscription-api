package org.project.subscriptionservice.config.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.cert")
public class RSAConfig {

    private RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;
}
