package com.awstraining.backend.smoketest;

import com.awstraining.backend.smoketest.api.MeasurementsApi;
import com.awstraining.backend.smoketest.ApiClient;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;





class SmokeBackendTest {

    private MeasurementsApi measurementsApi;
    private ApiClient apiClient;
    private static HttpClient client;

    private static PropertyHandler propertyHandler;

    @BeforeAll
    static void beforeAll() throws Exception {
        // Wczytuje URL + login + hasło z plików konfiguracyjnych smoke testu
        propertyHandler = new PropertyHandler();
    }

    @BeforeEach
    void init() {
        // Tworzymy klienta API
        measurementsApi = new MeasurementsApi();
        apiClient = measurementsApi.getApiClient();

        // Pobieramy dane konfiguracyjne
        final String url = propertyHandler.getUrl();
        final String user = propertyHandler.getUsername();
        final String pass = propertyHandler.getPassword();

        // Konfigurujemy bazową ścieżkę
        apiClient.setBasePath(url + "/backend/v1");

        // Uwierzytelnianie (Basic Auth)
        apiClient.setUsername(user);
        apiClient.setPassword(pass);

        // Ignorujemy SSL (w testach CI/TEST)
        apiClient.setVerifyingSsl(false);
    }




    @Test
    void smoke_root() throws Exception {
        String url = propertyHandler.getUrl();

        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(req, HttpResponse.BodyHandlers.ofString());

        System.out.println("GET / => " + response.body());
        assertTrue(response.statusCode() < 500, "Root endpoint should respond");
    }

    // ========================================
    // 2. curl -u user:pass http://LB/device/v1/test
    // ========================================
    @Test
    void smoke_deviceTestGet() throws Exception {
        String url = propertyHandler.getUrl() + "/device/v1/test";

        String auth = propertyHandler.getUsername() + ":" + propertyHandler.getPassword();
        String encoded = Base64.getEncoder().encodeToString(auth.getBytes());

        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Basic " + encoded)
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(req, HttpResponse.BodyHandlers.ofString());

        System.out.println("GET /device/v1/test => " + response.body());
        assertTrue(response.statusCode() < 500);
    }

    // =================================================================
    // 3. curl -X POST -u user:pass -H "Content-Type: application/json"
    //        -d '{ "type":"test", "value": -510.190 }'
    // =================================================================
    @Test
    void smoke_deviceTestPost() throws Exception {
        String url = propertyHandler.getUrl() + "/device/v1/test";

        String auth = propertyHandler.getUsername() + ":" + propertyHandler.getPassword();
        String encoded = Base64.getEncoder().encodeToString(auth.getBytes());

        String json = """
                {
                    "type": "test",
                    "value": -510.190
                }
                """;

        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Basic " + encoded)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response =
                client.send(req, HttpResponse.BodyHandlers.ofString());

        System.out.println("POST /device/v1/test => " + response.body());
        assertTrue(response.statusCode() < 500);
    }

    @Test
    void testSomething() {
        // <<TODO: test something>>
    }
}

