package com.java.dto;

import java.util.List;

import com.java.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KakaoPayRequestDto {

    // 결제 준비 단계에서 필요한 필드
    private String partnerOrderId;    // 가맹점 주문번호
    private String partnerUserId;     // 가맹점 회원 id
    private String name;              // 상품명
    private String quantity;          // 상품 수량
    private Integer price;            // 상품 총액
    private String receiver;
    private String phone;
    private String zipcode;
    private String addressMain;
    private String addressDetail;
    private List<Integer> cartItemIds;
    private String deliveryMessage;
    private int shipping;
    

    // 결제 승인 단계에서 필요한 필드
    private String tid;               // 결제 고유번호 (ready API 응답에서 받아옴)
    private String pgToken;           // redirect 시 카카오가 보내주는 토큰

    // URL들은 하드코딩으로 처리
    public String vatAmount() {
        return "0";  // 시험용 기본값
    }

    public String taxFreeAmount() {
        return "0";  // 시험용 기본값
    }

    public String approvalUrl() {
        return "http://localhost:8080/kakao-pay/approve";  // 시험용 기본값
    }

    public String cancelUrl() {
        return "http://localhost:8080/kakao-pay/cancel";   // 시험용 기본값
    }

    public String failUrl() {
        return "http://localhost:8080/kakao-pay/fail";     // 시험용 기본값
    }
}

