package com.nhn.sadari.minidooray.gateway.service;

import com.nhn.sadari.minidooray.gateway.domain.IdDto;
import com.nhn.sadari.minidooray.gateway.domain.common.CommonResponse;
import com.nhn.sadari.minidooray.gateway.domain.tag.TagRegister;
import com.nhn.sadari.minidooray.gateway.exception.AlreadyExistsException;
import com.nhn.sadari.minidooray.gateway.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {

    private final RestTemplate restTemplate;

    public IdDto registerTag(TagRegister tagRegister, Long projectId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<TagRegister> requestEntity = new HttpEntity<>(tagRegister, httpHeaders);
        ResponseEntity<CommonResponse<IdDto>> exchange = restTemplate.exchange("http://" + "localhost" + ":" + "9090" + "/api/projects/" + projectId + "/tags",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });
        CommonResponse<?> response = exchange.getBody();

        if (201 != response.getHeader().getResultCode()) {
            throw new AlreadyExistsException(response.getHeader().getResultMessage());
        }
        return (IdDto) response.getResult().get(0);
    }

    public IdDto deleteTag(Long projectId, Long tagId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> requestEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<CommonResponse<IdDto>> exchange = restTemplate.exchange("http://" + "localhost" + ":" + "9090" + "/api/projects/" + projectId + "/tags/" + tagId,
                HttpMethod.DELETE,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });
        CommonResponse<?> response = exchange.getBody();

        if (200 != response.getHeader().getResultCode()) {
            throw new NotFoundException(response.getHeader().getResultMessage());
        }
        return (IdDto) response.getResult().get(0);
    }

}
