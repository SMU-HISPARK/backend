package com.java.service;

import java.util.List;

public interface RecommendationService {

    /**
     * 특정 상품을 구매한 다른 사용자가 많이 구매한 상품 ID 목록을 가져옵니다.
     * @param productId 기준 상품 ID
     * @return 추천 상품 ID 리스트
     */
    List<Integer> getRecommendationsByUser(int productId);

    /**
     * 특정 상품과 한 주문에 함께 많이 구매된 상품 ID 목록을 가져옵니다.
     * @param productId 기준 상품 ID
     * @return 추천 상품 ID 리스트
     */
    List<Integer> getRecommendationsTogether(int productId);
}
