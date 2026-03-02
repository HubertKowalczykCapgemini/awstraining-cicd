package com.awstraining.backend.smoketest;

import com.awstraining.backend.smoketest.api.MeasurementsApi;
import com.awstraining.backend.smoketest.ApiClient;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class SmokeBackendTest {

    private MeasurementsApi measurementsApi;
    private ApiClient apiClient;

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


//     // ================================================
//     // CURL #1: curl http://LB
//     // ================================================
//     @Test
//     void smoke_rootEndpoint() throws Exception {
//         apiClient.

//         String response = apiClient.invokeAPI(
//                 "/",               // path
//                 "GET",             // method
//                 new HashMap<>(),
//                 null,
//                 new HashMap<>(),
//                 new HashMap<>(),
//                 new HashMap<>(),
//                 "text/plain",
//                 null,
//                 new String[]{}
//         ).toString();

//         System.out.println("Smoke GET / => " + response);
//         assertNotNull(response);
//     }

//     // =====================================================
//     // CURL #2: curl -vk http://LB/device/v1/test -u user:pass
//     // =====================================================
//     @Test
//     void smoke_deviceTestGet() throws Exception {

//         String response = apiClient.invokeAPI(
//                 "/device/v1/test",
//                 "GET",
//                 new HashMap<>(),
//                 null,
//                 new HashMap<>(),
//                 new HashMap<>(),
//                 new HashMap<>(),
//                 "application/json",
//                 null,
//                 new String[]{}
//         ).toString();

//         System.out.println("Smoke GET /device/v1/test => " + response);
//         assertNotNull(response);
//     }

//     // =====================================================================
//     // CURL #3:
//     // curl -X POST http://LB/device/v1/test -H "Content-Type: application/json"
//     //      -u user:pass
//     //      -d '{ "type": "test", "value": -510.190 }'
//     // =====================================================================
//     @Test
//     void smoke_deviceTestPost() throws Exception {

//         Map<String, Object> body = new HashMap<>();
//         body.put("type", "test");
//         body.put("value", -510.190);

//         Map<String, String> headers = new HashMap<>();
//         headers.put("Content-Type", "application/json");

//         String response = apiClient.invokeAPI(
//                 "/device/v1/test",
//                 "POST",
//                 new HashMap<>(),
//                 body,               // JSON BODY
//                 headers,            // headers including Content-Type
//                 new HashMap<>(),
//                 new HashMap<>(),
//                 "application/json",
//                 "application/json",
//                 new String[]{}
//         ).toString();

//         System.out.println("Smoke POST /device/v1/test => " + response);
//         assertNotNull(response);
//     }
// }
    @Test
    void testSomething() {
        // <<TODO: test something>>
    }
}

