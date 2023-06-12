package com.nhn.sadari.minidooray.gateway.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.nhn.sadari.minidooray.gateway.domain.comment.CommentRegister;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GitService {
    private final RestTemplate restTemplate;

    public String getEmail(String accessToken) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
        httpHeaders.set("Authorization", "Bearer " + accessToken);

        HttpEntity<CommentRegister> requestEntity = new HttpEntity<>( httpHeaders);

        ResponseEntity<JsonNode[]> exchange = restTemplate.exchange("https://api.github.com/user/emails",
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });
        // TODO result 넘길 때 이메일 둘 다 넘기기로 수정
        String result = exchange.getBody()[1].get("email").toString();

        return result;
    }

}
