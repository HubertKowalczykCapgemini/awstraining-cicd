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

    private static PropertyHandler static void beforeAll() throws Exception {
        propertyHandler = new PropertyHandler();
    }

    @BeforeEach
    void init() {
        measurementsApi = new MeasurementsApi();
        apiClient = measurementsApi.getApiClient();

        final String url = propertyHandler.getUrl();       // np. http://myapp-lb...
        final String user = propertyHandler.getUsername(); // userEMEATest
        final String pass = propertyHandler.getPassword(); // welt

        apiClient.setBasePath(url);
        apiClient.setUsername(user);
        apiClient.setPassword(pass);
        apiClient.setVerifyingSsl(false);
    }

    @Test
    void smokeTest_backendEndpoints() throws Exception {

        // ======================================================
        // 1. curl http://<LB>
        // ======================================================
        String rootResponse = apiClient.invokeAPI(
                "/",               // path
                "GET",             // method
                new HashMap<>(),   // query params
                null,              // body
                new HashMap<>(),   // headers
                new HashMap<>(),   // cookies
                new HashMap<>(),   // form params
                "application/json",
                null,
                new String[]{}     // basic auth już ustawione globalnie
        ).toString();

        System.out.println("GET / => " + rootResponse);
        assertNotNull(rootResponse);


        // ======================================================
        // 2. curl -vk http://<LB>/device/v1/test -u user:pass
        // ======================================================
        String getResponse = apiClient.invokeAPI(
                "/device/v1/test",
                "GET",
                new HashMap<>(),
                null,
                new HashMap<>(),
                new HashMap<>(),
                new HashMap<>(),
                "application/json",
                null,
                new String[]{}        // BasicAuth z ApiClient
        ).toString();

        System.out.println("GET /device/v1/test => " + getResponse);
        assertNotNull(getResponse);


        // ======================================================
        // 3. curl -X POST -H "Content-Type: application/json" \
        //        -d '{ "type": "test", "value": -510.190 }'
        // ======================================================
        Map<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("type", "test");
        jsonBody.put("value", -510.190);

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        String postResponse = apiClient.invokeAPI(
                "/device/v1/test",
                "POST",
                new HashMap<>(),
                jsonBody,
                headers,
                new HashMap<>(),
                new HashMap<>(),
                "application/json",
                "application/json",
                new String[]{}          // BasicAuth globalnie
        ).toString();

        System.out.println("POST /device/v1/test => " + postResponse);
        assertNotNull(postResponse);
    }
}