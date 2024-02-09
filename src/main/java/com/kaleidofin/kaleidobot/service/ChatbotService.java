package com.kaleidofin.kaleidobot.service;

import com.kaleidofin.kaleidobot.dto.HuggingFaceRequest;
import com.kaleidofin.kaleidobot.dto.HuggingFaceResponse;
import com.kaleidofin.kaleidobot.dto.Input;
import com.kaleidofin.kaleidobot.dto.Page;
import com.kaleidofin.kaleidobot.dto.Pages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class ChatbotService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${application.notion.client-secret}")
    private String token;

    @Value("${application.notion.version}")
    private String version;

    @Value("${application.notion.base-url}")
    private String baseUrl;

    @Value("${application.notion.database}")
    private String database;

    @Value("${application.hugging-face.base-url}")
    private String huggingFaceBaseUrl;

    @Value("${application.hugging-face.client-secret}")
    private String huggingFaceClientSecret;


    public ResponseEntity<Pages> getPrompts() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "Bearer " + token);
        httpHeaders.set("Notion-Version", version);
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        String url = baseUrl + "/v1/databases/" + database + "/query";
        return restTemplate.exchange(url, HttpMethod.POST, entity, Pages.class);
    }

    public ResponseEntity<HuggingFaceResponse> reply(String block, String question) {
        Pages pages = getNotion(block);
        StringBuilder sb = new StringBuilder();
        pages.getResults().forEach(page -> {
            page.getParagraph().getRich_text().forEach(richText -> {
                sb.append(richText.getText().getContent());
            });
        });
        return getHuggingFace(sb.toString(), question);

    }

    private Pages getNotion(String block) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "Bearer " + token);
        httpHeaders.set("Notion-Version", version);
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        String url = baseUrl + "/v1/blocks/" + block + "/children";
        ResponseEntity<Pages> pages = restTemplate.exchange(url, HttpMethod.GET, entity, Pages.class);
        return pages.getBody();
    }

    private ResponseEntity<HuggingFaceResponse> getHuggingFace(String content, String question) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "Bearer " + huggingFaceClientSecret);
        httpHeaders.set("Content-Type", "application/json");
        HuggingFaceRequest huggingFaceRequest = new HuggingFaceRequest();
        Input input = new Input();
        input.setQuestion(question);
        input.setContext(content);
        huggingFaceRequest.setInputs(input);
        HttpEntity<HuggingFaceRequest> entity = new HttpEntity<>(huggingFaceRequest, httpHeaders);
        log.info("Calling hugging face");
        String url = huggingFaceBaseUrl + "/models/deepset/roberta-base-squad2";
        return restTemplate.exchange(url, HttpMethod.POST, entity, HuggingFaceResponse.class);
    }

}
