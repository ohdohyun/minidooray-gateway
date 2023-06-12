package com.nhn.sadari.minidooray.gateway.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.nhn.sadari.minidooray.gateway.domain.*;
import com.nhn.sadari.minidooray.gateway.domain.common.CommonResponse;
import com.nhn.sadari.minidooray.gateway.domain.project.*;
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

    public ProjectGet findByProjectId(Long id) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> requestEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<ProjectGet> exchange = restTemplate.exchange("http://" + "localhost" + ":" + "9090" + "/api/projects/" + id,
                HttpMethod.GET,
                requestEntity,
                ProjectGet.class,
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

    public List<ProjectListGet> getProjectsByMemberId(Long memberId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> requestEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<CommonResponse> exchange = restTemplate.exchange("http://" + "localhost" + ":" + "9090" + "/api/projects/members/" + memberId,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });


//        Object o = exchange.getBody();
        CommonResponse response = exchange.getBody();
//
//        return (List<ProjectListGet>) response.getResult().get(0);
        return (List<ProjectListGet>) response.getResult();
//
//        return exchange.getBody();
    }

    public IdDto createProjectMember(ProjectMemberRegister projectMemberRegister, Long projectId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<ProjectMemberRegister> requestEntity = new HttpEntity<>(projectMemberRegister, httpHeaders);

        ResponseEntity<IdDto> exchange = restTemplate.exchange("http://" + "localhost" + ":" + "9090" + "/api/projects/" + projectId + "/members",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });
        return exchange.getBody();
    }

    public IdDto deleteProjectMemberByMemberId(Long projectId, Long memberId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> requestEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<IdDto> exchange = restTemplate.exchange("http://" + "localhost" + ":" + "9090" + "/api/projects/" + projectId + "/members/" + memberId,
                HttpMethod.DELETE,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });
        return exchange.getBody();
    }


}
