package com.port.petfit.user.member.board;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.json.JSONArray;
import org.json.JSONObject;

@Service
public class ChatGPTService {

    @Value("${openai.api.key}")
    private String apiKey;

    public String getChatGPTResponse(String prompt) {
        String url = "https://api.openai.com/v1/chat/completions";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        JSONObject request = new JSONObject();
        request.put("model", "gpt-3.5-turbo");
        request.put("messages", new JSONArray().put(new JSONObject().put("role", "user").put("content", prompt)));
        request.put("max_tokens", 150);

        HttpEntity<String> entity = new HttpEntity<>(request.toString(), headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        JSONObject jsonResponse = new JSONObject(response.getBody());
        return jsonResponse.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content").trim();
    }
}
