package org.mukhtarovich.uz.Auth.Service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Service
@RequiredArgsConstructor
public class TelegramOtpService {
    @Value("${telegram.token}")
    private String token;
    private final RestTemplate restTemplate;
    private final Map<String, String> requestStore = new ConcurrentHashMap<>();

    public String sendOtp(String phoneNumber) {
        String url = "https://gatewayapi.telegram.org/sendVerificationMessage";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = Map.of(
                "phone_number", phoneNumber,
                "code_length", 6,
                "ttl", 300
        );
        HttpEntity<Map<String, Object>> requests = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response =
                restTemplate.postForEntity(url, requests, Map.class);
        Map<String, Object> result = (Map<String, Object>) response.getBody().get("result");

        String requestId = result.get("request_id").toString();

        requestStore.put(phoneNumber, requestId);


        restTemplate.postForEntity(url, requests, Void.class);
        return requestId;
    }
    public boolean verifyOtp(String phoneNumber, String code) {

        String requestId = requestStore.get(phoneNumber);

        if (requestId == null) {
            return false;
        }

        String url = "https://gatewayapi.telegram.org/checkVerificationStatus";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = Map.of(
                "request_id", requestId,
                "code", code
        );

        HttpEntity<Map<String, Object>> request =
                new HttpEntity<>(body, headers);

        ResponseEntity<Map> response =
                restTemplate.postForEntity(url, request, Map.class);

        Map<String, Object> result =
                (Map<String, Object>) response.getBody().get("result");

        Map<String, Object> verification =
                (Map<String, Object>) result.get("verification_status");

        String status = verification.get("status").toString();

        if ("code_valid".equals(status)) {
            requestStore.remove(phoneNumber);

            return true;
        }

        return false;
    }
}
