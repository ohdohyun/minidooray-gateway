package com.nhn.sadari.minidooray.gateway.service;

import com.nhn.sadari.minidooray.gateway.domain.common.IdDto;
import com.nhn.sadari.minidooray.gateway.domain.common.CommonResponse;
import com.nhn.sadari.minidooray.gateway.domain.milestone.MilestoneDto;
import com.nhn.sadari.minidooray.gateway.domain.milestone.MilestoneRegister;
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
public class MilestoneService {

    private final RestTemplate restTemplate;

    public IdDto registerMilestone(MilestoneRegister milestoneRegister, Long projectId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<MilestoneRegister> requestEntity = new HttpEntity<>(milestoneRegister, httpHeaders);
        ResponseEntity<CommonResponse<IdDto>> exchange = restTemplate.exchange("http://" + "localhost" + ":" + "9090" + "/api/projects/" + projectId + "/milestones",
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

    public IdDto deleteMilestone(Long projectId, Long milestoneId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<Void> requestEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<CommonResponse<IdDto>> exchange = restTemplate.exchange("http://" + "localhost" + ":" + "9090" + "/api/projects/" + projectId + "/milestones/" + milestoneId,
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

    public List<MilestoneDto> getMilestoneList(Long projectId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<Void> requestEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<CommonResponse<MilestoneDto>> exchange = restTemplate.exchange("http://" + "localhost" + ":" + "9090" + "/api/projects/" + projectId + "/milestones",
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });
        CommonResponse<MilestoneDto> response = exchange.getBody();

        if (200 != response.getHeader().getResultCode()) {
            throw new NotFoundException(response.getHeader().getResultMessage());
        }
        return (List<MilestoneDto>) response.getResult();
    }

    public IdDto doMilestoneModify(Long projectId, Long milestoneId, MilestoneDto milestoneDto) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<MilestoneDto> requestEntity = new HttpEntity<>(milestoneDto, httpHeaders);
        ResponseEntity<CommonResponse<IdDto>> exchange = restTemplate.exchange("http://" + "localhost" + ":" + "9090" + "/api/projects/" + projectId + "/milestones/" + milestoneId,
                HttpMethod.PUT,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });
        CommonResponse<?> response = exchange.getBody();

        if (200 != response.getHeader().getResultCode()) {
            throw new NotFoundException(response.getHeader().getResultMessage());
        }
        return (IdDto) (response.getResult().get(0));
    }


    public MilestoneDto getMilestone(Long projectId, Long milestoneId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<Void> requestEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<CommonResponse<MilestoneDto>> exchange = restTemplate.exchange("http://" + "localhost" + ":" + "9090" + "/api/projects/" + projectId + "/milestones/" + milestoneId,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });
        CommonResponse<?> response = exchange.getBody();

        if (200 != response.getHeader().getResultCode()) {
            throw new NotFoundException(response.getHeader().getResultMessage());
        }
        return (MilestoneDto) response.getResult().get(0);
    }
}
