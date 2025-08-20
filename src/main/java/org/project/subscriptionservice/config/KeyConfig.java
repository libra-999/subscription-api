package org.project.subscriptionservice.config;

import org.project.subscriptionservice.share.utility.SignatureUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.security.*;

@Configuration
public class KeyConfig {

    @Bean
    public KeyPair keyPair() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        return SignatureUtil.generateKeyPair();
    }
    @Bean
    public PrivateKey privateKey(KeyPair keyPair) {
        return keyPair.getPrivate();
    }

    @Bean
    public PublicKey publicKey(KeyPair keyPair) {
        return keyPair.getPublic();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
