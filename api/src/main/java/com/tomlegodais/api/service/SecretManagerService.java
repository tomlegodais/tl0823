package com.tomlegodais.api.service;

import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SecretManagerService {

    private final AWSSecretsManager secretsManager;

    public SecretManagerService(AWSSecretsManager secretsManager) {
        this.secretsManager = secretsManager;
    }

    public Map<String, Object> getSecretValues(String secretArn, String... secretKeys) {
        if (secretKeys.length == 0) {
            throw new IllegalArgumentException("secretKeys must not be empty");
        }

        if (secretArn.isEmpty() || !secretArn.matches("arn:aws:secretsmanager:.*")) {
            throw new IllegalArgumentException("secretArn must be a valid arn");
        }

        var request = new GetSecretValueRequest().withSecretId(secretArn);
        var response = secretsManager.getSecretValue(request);
        var secretString = response.getSecretString();
        if (secretString == null) {
            throw new IllegalArgumentException("secretString must not be null");
        }

        var jsonObject = new JSONObject(secretString);
        return Stream.of(secretKeys).collect(Collectors.toMap(key -> key, jsonObject::opt));
    }
}
