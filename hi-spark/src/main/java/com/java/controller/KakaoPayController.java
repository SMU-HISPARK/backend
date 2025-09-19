package com.java.controller;


import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.java.dto.KakaoPayApproveResponseDto;
import com.java.dto.KakaoPayRequestDto;
import com.java.dto.KakaoPayResponseDto;
import com.java.entity.Orders;
import com.java.service.KakaoPayService;
import com.java.service.OrderService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/kakao-pay")
public class KakaoPayController {

    private final KakaoPayService kakaoPayService;
    private final OrderService orderService;

    @Autowired
    public KakaoPayController(KakaoPayService kakaoPayService, OrderService orderService) {
        this.kakaoPayService = kakaoPayService;
        this.orderService = orderService;
    }

    // 1 결제 준비
    @ResponseBody
    @PostMapping("/ready")
    public KakaoPayResponseDto kakaoPayReady(@RequestBody KakaoPayRequestDto request, HttpSession session) {
        Integer memberId = (Integer) session.getAttribute("memberId");
        String orderCode = orderService.generateOrderCode();
        
        System.out.println("cartItemIds in Controller = " + request.getCartItemIds());
        
        //dto 세팅
        request.setPartnerUserId(String.valueOf(memberId));
        request.setPartnerOrderId(orderCode);
        
        KakaoPayResponseDto response = kakaoPayService.getKakaoPayReady(request);

        // ready API 응답 tid, 주문번호 등을 세션에 저장
        session.setAttribute("tid", response.getTid());
        session.setAttribute("partnerOrderId", request.getPartnerOrderId());
        session.setAttribute("request", request);
        
        return response;
    }

    // 2 결제 승인 및 주문 저장 → finish-view로 redirect
    @GetMapping("/approve")
    public String kakaoPayApprove(@RequestParam("pg_token") String pgToken, HttpSession session) {

        Integer memberId = (Integer) session.getAttribute("memberId");

        // 2-1. approve 요청 DTO 생성
        Object oRequest = session.getAttribute("request");
        KakaoPayRequestDto request = (KakaoPayRequestDto) oRequest;
        String tid = (String) session.getAttribute("tid");
        String partnerOrderId = (String) session.getAttribute("partnerOrderId");
        
        request.setPgToken(pgToken);
        request.setTid(tid);
        request.setPartnerUserId(String.valueOf(memberId));
        request.setPartnerOrderId(partnerOrderId);


        // 2-2. 카카오페이 approve API 호출
        KakaoPayApproveResponseDto approveResponse = kakaoPayService.getKakaoPayApprove(request);

        // 2-3. Orders 저장
        Orders order = orderService.placeOrderFromKakao(request, memberId, approveResponse);
        
        orderService.save(order);
        
        session.removeAttribute("tid");
        session.removeAttribute("partnerOrderId");
        session.removeAttribute("request");

        // 2-4. finish-view 페이지로 redirect
        return "redirect:/order/finish-view?orderCode=" + order.getOrderCode();
    }
}
