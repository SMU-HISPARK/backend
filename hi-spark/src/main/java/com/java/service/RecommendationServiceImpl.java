package com.java.service;

import com.java.dto.Recommendation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException; // [추가]
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecommendationServiceImpl implements RecommendationService {

    private final RestTemplate restTemplate;
    private static final String RECOMMEND_API_BASE_URL = "https://525088021188.ngrok-free.app";

    @Autowired
    public RecommendationServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Integer> getRecommendationsByUser(int productId) {
        String url = RECOMMEND_API_BASE_URL + "/recommend/by-user/" + productId;
        System.out.println("사용자 기반 추천 API 호출: " + url);
        return callApiAndGetProductIds(url);
    }

    @Override
    public List<Integer> getRecommendationsTogether(int productId) {
        String url = RECOMMEND_API_BASE_URL + "/recommend/together/" + productId;
        System.out.println("주문 기반 추천 API 호출: " + url);
        return callApiAndGetProductIds(url);
    }

    private List<Integer> callApiAndGetProductIds(String url) {
        try {
            ResponseEntity<List<Recommendation>> response = restTemplate.exchange(
                url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {}
            );
            
            if (response.getBody() != null) {
                return response.getBody().stream()
                               .map(Recommendation::getProduct_id)
                               .collect(Collectors.toList());
            }
            return Collections.emptyList();
            
        } catch (HttpClientErrorException e) { // [수정] 4xx 에러를 별도로 처리
            // 추천 결과가 없어서 API가 404 Not Found를 반환한 경우, 정상적으로 빈 리스트를 반환
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                System.out.println("추천 결과 없음 (404 Not Found). 빈 리스트를 반환합니다.");
                return Collections.emptyList();
            }
            // 그 외 4xx 에러는 로그를 남김
            System.err.println("추천 API 호출 중 클라이언트 에러(" + e.getStatusCode() + "): " + e.getResponseBodyAsString());
            return Collections.emptyList();
        } catch (RestClientException e) {
            // 네트워크 문제 등 기타 서버 에러
            System.err.println("추천 API(" + url + ") 호출 중 통신 에러: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}

