package com.nhn.sadari.minidooray.gateway.service;

import com.nhn.sadari.minidooray.gateway.domain.IdDto;
import com.nhn.sadari.minidooray.gateway.domain.common.CommonResponse;
import com.nhn.sadari.minidooray.gateway.domain.project.*;
import com.nhn.sadari.minidooray.gateway.exception.AlreadyExistsException;
import com.nhn.sadari.minidooray.gateway.exception.NotFoundException;
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
        ResponseEntity<CommonResponse<IdDto>> exchange = restTemplate.exchange("http://" + "localhost" + ":" + "9090" + "/api/projects",
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

    public ProjectGet findByProjectId(Long id) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<Void> requestEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<CommonResponse<ProjectGet>> exchange = restTemplate.exchange("http://" + "localhost" + ":" + "9090" + "/api/projects/" + id,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });

        CommonResponse<?> response = exchange.getBody();

        if (200 != response.getHeader().getResultCode()) {
            throw new NotFoundException(response.getHeader().getResultMessage());
        }
        return (ProjectGet) response.getResult().get(0);
    }

    public IdDto modify(ProjectModifyDto projectModifyDto, Long projectId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<ProjectModifyDto> requestEntity = new HttpEntity<>(projectModifyDto, httpHeaders);
        ResponseEntity<CommonResponse<IdDto>> exchange = restTemplate.exchange("http://" + "localhost" + ":" + "9090" + "/api/projects/" + projectId,
                HttpMethod.PUT,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });

        CommonResponse<?> response = exchange.getBody();

        if (200 != response.getHeader().getResultCode()) {
            throw new NotFoundException(response.getHeader().getResultMessage());
        }
        return (IdDto) response.getResult().get(0);
    }

    public IdDto delete(Long projectId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> requestEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<CommonResponse<IdDto>> exchange = restTemplate.exchange("http://" + "localhost" + ":" + "9090" + "/api/projects/" + projectId,
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

    public List<ProjectListGet> getProjectsByMemberId(Long memberId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> requestEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<CommonResponse<?>> exchange = restTemplate.exchange("http://" + "localhost" + ":" + "9090" + "/api/projects/members/" + memberId,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });


        CommonResponse<?> response = exchange.getBody();
        return (List<ProjectListGet>) response.getResult();
    }

    public IdDto createProjectMember(ProjectMemberRegister projectMemberRegister, Long projectId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<ProjectMemberRegister> requestEntity = new HttpEntity<>(projectMemberRegister, httpHeaders);

        ResponseEntity<CommonResponse<IdDto>> exchange = restTemplate.exchange("http://" + "localhost" + ":" + "9090" + "/api/projects/" + projectId + "/members",
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

    public IdDto deleteProjectMemberByMemberId(Long projectId, Long memberId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> requestEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<CommonResponse<IdDto>> exchange = restTemplate.exchange("http://" + "localhost" + ":" + "9090" + "/api/projects/" + projectId + "/members/" + memberId,
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
