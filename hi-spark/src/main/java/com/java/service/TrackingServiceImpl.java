package com.java.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

@Slf4j
@Service
public class TrackingServiceImpl implements TrackingService{
//
//	@Value("${smartparcel.api.key}")
//    private String apiKey;
//
//    @Value("${smartparcel.api.url:https://info.sweettracker.co.kr/api/v1/trackingInfo}")
//    private String apiUrl;
//
//    private final RestTemplate restTemplate;
//    private final ObjectMapper objectMapper;
//
//    public TrackingServiceImpl() {
//        this.restTemplate = new RestTemplate();
//        this.objectMapper = new ObjectMapper();
//    }
//
//    /**
//     * 택배 배송 조회
//     * @param t_key API 키
//     * @param t_code 택배회사 코드 (예: "04" CJ대한통운)
//     * @param t_invoice 송장번호
//     * @return TrackingInfo
//     */
//    public TrackingInfo getTrackingInfo(String companyCode, String invoiceNumber) {
//        try {
//            // API URL 구성
//            String url = String.format("%s?t_code=%s&t_invoice=%s",
//                    apiUrl, companyCode, invoiceNumber);
//            
//            log.info("스마트택배 API 호출: {}", url);
//            
//            // HTTP 헤더 설정
//            HttpHeaders headers = new HttpHeaders();
//            headers.set("Accept", "application/json");
//            headers.set("User-Agent", "MyApp/1.0");
//
//            HttpEntity<String> entity = new HttpEntity<>(headers);
//
//            ResponseEntity<String> response = restTemplate.exchange(
//                    url, HttpMethod.GET, entity, String.class);
//            
//            // API 호출
//            log.info("API 응답: {}", response.getBody());
////            log.info("API 호출 URL: {}", response.url);
//            
//            // JSON을 TrackingInfo 객체로 변환
//            TrackingInfo trackingInfo = objectMapper.readValue(
//            	    response.getBody(), TrackingInfo.class);
//            
//            return trackingInfo;
//            
//        } catch (Exception e) {
//            log.error("배송 조회 API 호출 실패", e);
//            // 에러 발생 시 기본 객체 반환
//            return createErrorTrackingInfo(invoiceNumber, e.getMessage());
//        }
//    }
//    
//    /**
//     * 주문번호로 배송 조회 (주문번호에서 송장번호를 추출하는 로직 필요)
//     */
//    public TrackingInfo getTrackingInfoByOrderNumber(String orderNumber) {
//        // 실제로는 주문번호로 DB에서 송장번호와 택배사 코드를 조회해야 함
//        // 임시로 샘플 데이터 사용
//        String companyCode = getCompanyCodeByOrderNumber(orderNumber);
//        String invoiceNumber = getInvoiceNumberByOrderNumber(orderNumber);
//        
//        return getTrackingInfo(companyCode, invoiceNumber);
//    }
//    
//    /**
//     * 에러 발생 시 기본 TrackingInfo 객체 생성
//     */
//    private TrackingInfo createErrorTrackingInfo(String invoiceNumber, String errorMessage) {
//        return TrackingInfo.builder()
//                .invoiceNo(invoiceNumber)
//                .result("배송 정보를 조회할 수 없습니다: " + errorMessage)
//                .complete(false)
//                .completeYN("N")
//                .level(0)
//                .build();
//    }
//    
//    /**
//     * 주문번호로 택배사 코드 조회 (실제로는 DB에서 조회)
//     */
//    private String getCompanyCodeByOrderNumber(String orderNumber) {
//        // 실제 구현에서는 주문 테이블에서 택배사 정보를 조회
//        // 임시로 CJ대한통운 코드 반환
//        return "04"; // CJ대한통운
//    }
//    
//    /**
//     * 주문번호로 송장번호 조회 (실제로는 DB에서 조회)
//     */
//    private String getInvoiceNumberByOrderNumber(String orderNumber) {
//        // 실제 구현에서는 주문 테이블에서 송장번호를 조회
//        // 임시 데이터
//        switch(orderNumber) {
//            case "ORD-2025-001":
//                return "599808408551";
//            case "ORD-2025-002":
//                return "599807358691";
//            default:
//                return "599807136021";
//        }
//    }
//    
//    /**
//     * 택배사 코드 매핑 (주요 택배사들)
//     * 참고: https://info.sweettracker.co.kr/apidoc/guide.php
//     */
//    public static class CompanyCode {
//        public static final String CJ_LOGISTICS = "04";      // CJ대한통운
//        public static final String HANJIN = "05";           // 한진택배
//        public static final String LOTTE = "08";            // 롯데택배
//        public static final String POST_OFFICE = "01";      // 우체국택배
//        public static final String LOGEN = "06";            // 로젠택배
//    }

}
