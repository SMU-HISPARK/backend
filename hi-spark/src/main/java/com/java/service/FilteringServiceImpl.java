package com.java.service;

import com.java.dto.FilteringRequestDto;
import com.java.dto.FilteringResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;

@Service
public class FilteringServiceImpl implements FilteringService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String apiUrl = "https://2c39b19acbca.ngrok-free.app/filter-text";

    @Override
    public FilteringResponseDto filterText(String originalTitle, String originalContent) {
        try {
            // 요청 객체에 제목과 본문 모두 포함
            FilteringRequestDto requestDto = new FilteringRequestDto(originalTitle, originalContent);
            HttpEntity<FilteringRequestDto> request = new HttpEntity<>(requestDto);

            // API 호출하고 응답을 DTO로 바로 받음
            return restTemplate.postForObject(apiUrl, request, FilteringResponseDto.class);
        } catch (Exception e) {
            System.err.println("필터링 API 호출 오류: " + e.getMessage());
            // 오류 발생 시, 원본 텍스트와 제목을 그대로 반환
            FilteringResponseDto errorResponse = new FilteringResponseDto();
            errorResponse.setFilteredTitle(originalTitle);
            errorResponse.setFilteredContent(originalContent);
            return errorResponse;
        }
    }
}