package com.example.ThingsboardSpringWebApp.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ThingsBoardService {

    @Value("${thingsboard.url}")
    private String thingsBoardUrl;

    @Value("${thingsboard.username}")
    private String username;

    @Value("${thingsboard.password}")
    private String password;

    private String jwtToken;

    private RestTemplate restTemplate = new RestTemplate();

    public void authenticate() {

        String url = thingsBoardUrl + "/api/auth/login";

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("username", username);
        requestBody.put("password", password);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Map.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            jwtToken = (String) response.getBody().get("token");
        } else {
            throw new RuntimeException("Failed to authenticate with ThingsBoard");
        }
    }

    public String getTelemetryData(String entityType, String entityId, String keys, long startTs, long endTs, int limit, String orderBy) {
        if (jwtToken == null) {
            authenticate();
        }

        String url = String.format("%s/api/plugins/telemetry/%s/%s/values/timeseries?keys=%s&startTs=%d&endTs=%d&limit=%d&orderBy=%s",
                thingsBoardUrl, entityType, entityId, keys, startTs, endTs, limit, orderBy);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + jwtToken);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to retrieve telemetry data from ThingsBoard");
        }
    }

    public void sendTelemetryData(String entityId, String telemetryData) {
        if (jwtToken == null) {
            authenticate();
        }

        String url = String.format("%s/api/v1/%s/telemetry", thingsBoardUrl, entityId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + jwtToken);

        HttpEntity<String> requestEntity = new HttpEntity<>(telemetryData, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Failed to send telemetry data to ThingsBoard");
        }
    }

}
