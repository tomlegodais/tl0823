package com.tomlegodais.api.service;

import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

public class SecretManagerServiceTest {

    private AutoCloseable closeable;
    private SecretManagerService secretManagerService;

    @Mock
    private AWSSecretsManager secretsManager;

    @BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        secretManagerService = new SecretManagerService(secretsManager);
    }

    @Test
    public void testGetSecretValues() {
        var sampleSecretString = "{\"key1\": \"value1\", \"key2\": \"value2\"}";
        var mockResult = new GetSecretValueResult().withSecretString(sampleSecretString);
        when(secretsManager.getSecretValue(any(GetSecretValueRequest.class))).thenReturn(mockResult);

        var secretArn = "arn:aws:secretsmanager:us-east-1:1234567890:secret:my-secret";
        var secretKeys = new String[]{"key1", "key2"};
        var secretValues = secretManagerService.getSecretValues(secretArn, secretKeys);

        assertThat(secretValues).hasSize(2);
        assertThat(secretValues).containsEntry("key1", "value1");
        assertThat(secretValues).containsEntry("key2", "value2");

        verify(secretsManager, times(1)).getSecretValue(
                argThat(request -> request.getSecretId().equals(secretArn))
        );
    }

    @Test
    public void testGetSecretValuesWithEmptySecretKeys() {
        var secretArn = "arn:aws:secretsmanager:us-east-1:1234567890:secret:my-secret";
        assertThrows(IllegalArgumentException.class, () -> secretManagerService.getSecretValues(secretArn));
    }

    @Test
    public void testGetSecretValuesWithInvalidSecretArn() {
        String secretArn = "invalid-secret-arn";
        String[] secretKeys = {"key1", "key2"};

        assertThrows(IllegalArgumentException.class, () -> secretManagerService.getSecretValues(secretArn, secretKeys));
    }

    @AfterEach
    public void tearDown() throws Exception {
        closeable.close();
    }
}
