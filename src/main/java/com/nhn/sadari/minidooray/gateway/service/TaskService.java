package com.nhn.sadari.minidooray.gateway.service;

import com.nhn.sadari.minidooray.gateway.domain.common.IdDto;
import com.nhn.sadari.minidooray.gateway.domain.common.CommonResponse;
import com.nhn.sadari.minidooray.gateway.domain.project.ProjectGet;
import com.nhn.sadari.minidooray.gateway.domain.task.TaskDto;
import com.nhn.sadari.minidooray.gateway.domain.task.TaskRegister;
import com.nhn.sadari.minidooray.gateway.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final RestTemplate restTemplate;

    public IdDto registerTask(TaskRegister taskRegister, Long projectId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<TaskRegister> requestEntity = new HttpEntity<>(taskRegister, httpHeaders);
        ResponseEntity<IdDto> exchange = restTemplate.exchange("http://" + "localhost" + ":" + "9090" + "/api/projects/" + projectId + "/tasks",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });
        return exchange.getBody();
    }

    public IdDto deleteTask(Long projectId, Long taskId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<Void> requestEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<CommonResponse<IdDto>> exchange = restTemplate.exchange("http://" + "localhost" + ":" + "9090" + "/api/projects/" + projectId + "/tasks/" + taskId,
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

    public TaskDto getTask(Long projectId, Long taskId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<Void> requestEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<CommonResponse<TaskDto>> exchange = restTemplate.exchange("http://" + "localhost" + ":" + "9090" + "/api/projects/" + projectId + "/tasks/" + taskId,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });
        CommonResponse<TaskDto> response = exchange.getBody();

        if (response.getHeader().isSuccessful()) {
            throw new NotFoundException(response.getHeader().getResultMessage());
        }
        return (TaskDto) response.getResult().get(0);
    }

    public List<TaskDto> getTaskList(Long projectId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<Void> requestEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<CommonResponse<TaskDto>> exchange = restTemplate.exchange("http://" + "localhost" + ":" + "9090" + "/api/projects/" + projectId + "/tasks",
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });
        CommonResponse<?> response = exchange.getBody();

        if (response.getHeader().isSuccessful()) {
            throw new NotFoundException(response.getHeader().getResultMessage());
        }
        return (List<TaskDto>) response.getResult();
    }
}
