package com.awstraining.backend.smoketest;

import com.awstraining.backend.smoketest.api.MeasurementsApi;
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
        propertyHandler = new PropertyHandler();
    }

    @BeforeEach
    void init() {
        measurementsApi = new MeasurementsApi();
        apiClient = measurementsApi.getApiClient();


        final String url = propertyHandler.getUrl();
        final String user = propertyHandler.getUsername();
        final String pass = propertyHandler.getPassword();

        apiClient.setBasePath(url + "/backend/v1");
        apiClient.setUsername(user);
        apiClient.setPassword(pass);
        apiClient.setVerifyingSsl(false);
    }


  @Test
    void smokeTest_allCurlRequests() throws Exception {

        // ================
        // 1. curl http://LB
        // ================
        String rootResponse = apiClient.invokeAPI(
                "/",                     // path
                "GET",                   // method
                new HashMap<>(),         // query params
                null,                    // body
                new HashMap<>(),         // headers
                new HashMap<>(),         // cookies
                new HashMap<>(),         // form params
                "application/json",      // accept
                null,                    // contentType
                new String[]{}           // authNames
        ).toString();

        assertNotNull(rootResponse);
        System.out.println("Root response: " + rootResponse);



        // ======================================
        // 2. curl GET /device/v1/test z basicAuth
        // ======================================

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
                new String[]{}       // Basic Auth podany globalnie (user/pass)
        ).toString();

        assertNotNull(getResponse);
        System.out.println("GET /device/v1/test: " + getResponse);



        // ======================================
        // 3. curl POST /device/v1/test z JSON body
        // ======================================

        Map<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("type", "test");
        jsonBody.put("value", -510.190);

        String postResponse = apiClient.invokeAPI(
                "/device/v1/test",
                "POST",
                new HashMap<>(),
                jsonBody,                     // BODY
                Map.of("Content-Type", "application/json"),
                new HashMap<>(),
                new HashMap<>(),
                "application/json",
                "application/json",
                new String[]{}               // basicAuth przez user/pass
        ).toString();

        assertNotNull(postResponse);
        System.out.println("POST /device/v1/test: " + postResponse);
    }

}
