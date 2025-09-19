package com.java.dto;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KakaoPayResponseDto {

    @JsonProperty("tid")
    private String tid; // 결제 고유번호
    
    @JsonProperty("next_redirect_app_url")
    private String nextRedirectAppUrl; // 카카오톡으로 결제 요청 메시지를 보내기 위한 사용자 정보 입력화면 Redirect URL
    
    @JsonProperty("next_redirect_mobile_url")
    private String nextRedirectMobileUrl; // 모바일 웹일 경우 받는 결제페이지 URL
    
    @JsonProperty("next_redirect_pc_url")
    private String nextRedirectPcUrl; // PC 웹일 경우 받는 결제페이지 URL
    
    @JsonProperty("android_app_scheme")
    private String androidAppScheme; // 카카오페이 결제화면으로 이동하는 Android 앱 스킴
    
    @JsonProperty("ios_app_scheme")
    private String iosAppScheme; // 카카오페이 결제화면으로 이동하는 iOS 앱 스킴
    
    @JsonProperty("created_at")
    private String createdAt; // 결제 준비 요청 시각
    
    
}