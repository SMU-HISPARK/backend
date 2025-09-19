package com.java.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.java.config.KakaoPayProperties;
import com.java.dto.KakaoPayApproveResponseDto;
import com.java.dto.KakaoPayRequestDto;
import com.java.dto.KakaoPayResponseDto;

@Service
public class KakaoPayService {

    // 결제 준비 API 호출
    public KakaoPayResponseDto getKakaoPayReady(KakaoPayRequestDto request) {
        HttpEntity<MultiValueMap<String, String>> requestEntity =
                new HttpEntity<>(this.getReadyParameters(request), this.getHeaders());

        try {
            RestTemplate restTemplate = new RestTemplate();
            KakaoPayResponseDto response = restTemplate.postForObject(
                    KakaoPayProperties.readyUrl,
                    requestEntity,
                    KakaoPayResponseDto.class
            );
            return response;
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getMessage());
        }
    }

    // 결제 승인 API 호출
    public KakaoPayApproveResponseDto getKakaoPayApprove(KakaoPayRequestDto request) {
        HttpEntity<MultiValueMap<String, String>> requestEntity =
                new HttpEntity<>(this.getApproveParameters(request), this.getHeaders());

        try {
            RestTemplate restTemplate = new RestTemplate();
            KakaoPayApproveResponseDto response = restTemplate.postForObject(
                    KakaoPayProperties.approveUrl,
                    requestEntity,
                    KakaoPayApproveResponseDto.class
            );
            return response;
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getMessage());
        }
    }

    // 공통 헤더
    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + KakaoPayProperties.adminKey);
        headers.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        return headers;
    }

    // ready API 요청 파라미터
    private MultiValueMap<String, String> getReadyParameters(KakaoPayRequestDto request) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("cid", KakaoPayProperties.cid);
        params.add("partner_order_id", request.getPartnerOrderId());  // 여기 꼭 추가
        params.add("partner_user_id", request.getPartnerUserId());   // 여기 꼭 추가
        params.add("item_name", request.getName());
        params.add("quantity", request.getQuantity());
        params.add("total_amount", String.valueOf(request.getPrice()));
        params.add("vat_amount", request.vatAmount());
        params.add("tax_free_amount", request.taxFreeAmount());
        params.add("approval_url", request.approvalUrl());
        params.add("cancel_url", request.cancelUrl());
        params.add("fail_url", request.failUrl());
        return params;
    }

    // approve API 요청 파라미터
    private MultiValueMap<String, String> getApproveParameters(KakaoPayRequestDto request) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("cid", KakaoPayProperties.cid);
        params.add("tid", request.getTid());
        params.add("partner_order_id", request.getPartnerOrderId());
        params.add("partner_user_id", request.getPartnerUserId());
        params.add("pg_token", request.getPgToken());
        return params;
    }
}
