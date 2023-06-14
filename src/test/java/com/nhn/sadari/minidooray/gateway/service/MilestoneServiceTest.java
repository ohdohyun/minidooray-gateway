package com.nhn.sadari.minidooray.gateway.service;

import com.nhn.sadari.minidooray.gateway.domain.common.CommonResponse;
import com.nhn.sadari.minidooray.gateway.domain.common.IdDto;
import com.nhn.sadari.minidooray.gateway.domain.milestone.MilestoneDto;
import com.nhn.sadari.minidooray.gateway.domain.milestone.MilestoneRegister;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MilestoneServiceTest {

    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private MilestoneService milestoneService;


    @Test
    void registerMilestoneTest() {
        // given
        MilestoneRegister milestoneRegister = new MilestoneRegister();
        Long testProjectId = 1L;

        IdDto responseDto = new IdDto(1L);
        CommonResponse<IdDto> response = new CommonResponse<>(
                new CommonResponse.Header(true, 201, "success"),
                List.of(responseDto),
                1
        );

        given(restTemplate.exchange(
                eq("http://localhost:9090/api/projects/" + testProjectId + "/milestones"),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(new ParameterizedTypeReference<CommonResponse<IdDto>>() {})
        )).willReturn(ResponseEntity.ok(response));

        // when
        IdDto result = milestoneService.registerMilestone(milestoneRegister, testProjectId);

        // then
        assertThat(result.getId()).isEqualTo(responseDto.getId());
    }


    @Test
    void deleteMilestoneTest() {
        // given
        Long testProjectId = 1L;
        Long testMilestoneId = 1L;

        IdDto responseDto = new IdDto(testMilestoneId);
        CommonResponse<IdDto> response = new CommonResponse<>(
                new CommonResponse.Header(true, 200, "success"),
                List.of(responseDto),
                1
        );

        given(restTemplate.exchange(
                eq("http://localhost:9090/api/projects/" + testProjectId + "/milestones/" + testMilestoneId),
                eq(HttpMethod.DELETE),
                any(HttpEntity.class),
                eq(new ParameterizedTypeReference<CommonResponse<IdDto>>() {})
        )).willReturn(ResponseEntity.ok(response));

        // when
        IdDto result = milestoneService.deleteMilestone(testProjectId, testMilestoneId);

        // then
        assertThat(result.getId()).isEqualTo(responseDto.getId());
    }


    @Test
    void getMilestoneList() {
        // given
        Long projectId = 1L;
        List<MilestoneDto> milestones = List.of(
                new MilestoneDto(1L, "Milestone 1", LocalDate.of(2023, 6, 20), LocalDate.of(2023, 6, 15)),
                new MilestoneDto(2L, "Milestone 2", LocalDate.of(2023, 6, 30), LocalDate.of(2023, 6, 25))
        );

        CommonResponse<MilestoneDto> response = new CommonResponse<>(
                new CommonResponse.Header(true, 200, "OK"), milestones, 2);

        ResponseEntity<CommonResponse<MilestoneDto>> entity = new ResponseEntity<>(response, HttpStatus.OK);

        given(restTemplate.exchange(
                eq("http://localhost:9090/api/projects/" + projectId + "/milestones"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(new ParameterizedTypeReference<CommonResponse<MilestoneDto>>() {})
        )).willReturn(entity);

        // when
        List<MilestoneDto> result = milestoneService.getMilestoneList(projectId);

        // then
        assertEquals(milestones.size(), result.size());
        for (int i = 0; i < result.size(); i++) {
            assertEquals(milestones.get(i).getId(), result.get(i).getId());
            assertEquals(milestones.get(i).getName(), result.get(i).getName());
            assertEquals(milestones.get(i).getStartDate(), result.get(i).getStartDate());
            assertEquals(milestones.get(i).getEndDate(), result.get(i).getEndDate());
        }
    }

    @Test
    void doMilestoneModify() {
        // given
        Long projectId = 1L;
        Long milestoneId = 1L;
        MilestoneDto milestoneDto = new MilestoneDto(1L, "Modified Milestone", LocalDate.of(2023, 7, 1), LocalDate.of(2023, 6, 20));

        IdDto idDto = new IdDto(milestoneId);
        CommonResponse<IdDto> response = new CommonResponse<>(new CommonResponse.Header(true, 200, "OK"), Collections.singletonList(idDto), 1);

        ResponseEntity<CommonResponse<IdDto>> entity = new ResponseEntity<>(response, HttpStatus.OK);

        given(restTemplate.exchange(
                eq("http://localhost:9090/api/projects/" + projectId + "/milestones/" + milestoneId),
                eq(HttpMethod.PUT),
                any(HttpEntity.class),
                eq(new ParameterizedTypeReference<CommonResponse<IdDto>>() {})
        )).willReturn(entity);

        // when
        IdDto result = milestoneService.doMilestoneModify(projectId, milestoneId, milestoneDto);

        // then
        assertEquals(milestoneId, result.getId());
    }


    @Test
    void getMilestone() {
        // given
        Long projectId = 1L;
        Long milestoneId = 1L;
        MilestoneDto milestoneDto = new MilestoneDto(milestoneId, "Test Milestone", LocalDate.of(2023, 7, 1), LocalDate.of(2023, 6, 1));

        CommonResponse<MilestoneDto> response = new CommonResponse<>(new CommonResponse.Header(true, 200, "OK"), Collections.singletonList(milestoneDto), 1);

        ResponseEntity<CommonResponse<MilestoneDto>> entity = new ResponseEntity<>(response, HttpStatus.OK);

        given(restTemplate.exchange(
                eq("http://localhost:9090/api/projects/" + projectId + "/milestones/" + milestoneId),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(new ParameterizedTypeReference<CommonResponse<MilestoneDto>>() {})
        )).willReturn(entity);

        // when
        MilestoneDto result = milestoneService.getMilestone(projectId, milestoneId);

        // then
        assertEquals(milestoneDto, result);
    }

}