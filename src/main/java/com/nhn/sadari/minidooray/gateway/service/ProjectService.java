package com.nhn.sadari.minidooray.gateway.service;

import com.nhn.sadari.minidooray.gateway.domain.IdDto;
import com.nhn.sadari.minidooray.gateway.domain.ProjectModifyDto;
import com.nhn.sadari.minidooray.gateway.domain.ProjectModifyGet;
import com.nhn.sadari.minidooray.gateway.domain.ProjectRegisterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private RestTemplate restTemplate;

    public IdDto create(ProjectRegisterDto projectRegisterDto) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<ProjectRegisterDto> requestEntity = new HttpEntity<>(projectRegisterDto, httpHeaders);
        ResponseEntity<IdDto> exchange = restTemplate.exchange("http://" + "localhost" + ":" + "9090" + "/api/projects",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });
        return exchange.getBody();
    }

    public ProjectModifyGet findByProjectId(Long id) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> requestEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<ProjectModifyGet> exchange = restTemplate.exchange("http://" + "localhost" + ":" + "9090" + "/api/projects/" + id,
                HttpMethod.GET,
                requestEntity,
                ProjectModifyGet.class,
                new ParameterizedTypeReference<>() {
                });

        return exchange.getBody();
    }

    public IdDto modify(ProjectModifyDto projectModifyDto, Long projectId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<ProjectModifyDto> requestEntity = new HttpEntity<>(projectModifyDto, httpHeaders);
        ResponseEntity<IdDto> exchange = restTemplate.exchange("http://" + "localhost" + ":" + "9090" + "/api/projects/" + projectId,
                HttpMethod.PUT,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });
        return exchange.getBody();
    }

    public IdDto delete(Long projectId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> requestEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<IdDto> exchange = restTemplate.exchange("http://" + "localhost" + ":" + "9090" + "/api/projects/" + projectId,
                HttpMethod.DELETE,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });
        return exchange.getBody();
    }


}
