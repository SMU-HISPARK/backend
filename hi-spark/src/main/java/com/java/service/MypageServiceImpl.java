package com.java.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.java.dto.Member;
import com.java.dto.OrderItemDto;
import com.java.dto.Orders;
import com.java.dto.Tracking;
import com.java.entity.OrderItem;
import com.java.repository.MemberRepository;
import com.java.repository.MypageRepository;

import jakarta.transaction.Transactional;

@Service
public class MypageServiceImpl implements MypageService{

	@Autowired MypageRepository mypageRepository;
	@Autowired MemberRepository memberRepository;
	@Autowired OrderItemRepository orderItemRepository;
	
	@Autowired private RestTemplate restTemplate;
	
	
	@org.springframework.beans.factory.annotation.Value("${api1.service_key}")
	private String dservice_key;
	


	@Override
	public String getTrackingStatus(String orderCode) {
	    
	    List<Tracking> trackingList = mypageRepository.findTrackingByOrderCode(orderCode);
	    
	    if(trackingList.isEmpty()) {
	        return "{\"error\":\"배송정보를 찾을 수 없습니다\"}";
	    }
	    
	    Tracking tracking = trackingList.get(0);
	    Long invoiceNo = tracking.getInvoiceNo();
	    String courier = tracking.getCourier(); // 택배사 코드 
	    
	    try {
	        String apiUrl = "https://info.sweettracker.co.kr/api/v1/trackingInfo"
	                +"?t_key=" + dservice_key + "&t_code=" + courier + "&t_invoice=" + invoiceNo;
	        
	        System.out.println("API URL: " + apiUrl);
	        
	        String result = restTemplate.getForObject(apiUrl, String.class);
	        System.out.println("API 응답: " + result);
	        
	        // API 응답에 orderCode 추가
	        if (result != null && !result.contains("\"error\"")) {
	            // JSON 파싱해서 orderCode 추가
	            result = result.substring(0, result.length()-1) + ",\"orderCode\":\"" + orderCode + "\"}";
	        }
	        
	        return result;
	    } catch(Exception e){
	        System.out.println("API 호출 에러: " + e.getMessage());
	        e.printStackTrace();
	        return "{\"error\":\"API 호출 중 오류 발생: " + e.getMessage() + "\"}";
	    }
	}
	
	@Override
	public List<Orders> getOrdersByMemberId(String loginId) {
	    List<Orders> orders = mypageRepository.findByMemberLoginId(loginId);
	    return orders;
	}
	
	@Override
	public List<Orders> getOrdersList() {
		
		List<Orders> order = mypageRepository.findAll();
		return order;
	}

	@Override
	public Orders getOrderByCode(String orderCode) {
		 return mypageRepository.findByOrderCode(orderCode);
	}
	
	@Transactional		// db 반영
	@Override
	public void updateMember(String loginId, String nickname, String phone1, String phone2, String phone3) {
		Member member = memberRepository.findByLoginId(loginId);
		
		String phone = phone1 + "-" + phone2 + "-" + phone3;
		
		member.setNickname(nickname);
		member.setPhone(phone);
		
		memberRepository.save(member);
		
	}

	@Transactional
	@Override
	public void updateMemberPoint(String loginId, int point) {
	    Member m = memberRepository.findByLoginId(loginId);
	    
	    m.setPoint(point);
	    memberRepository.save(m); 
	}

	@Transactional
	@Override
	public void updateMemberPassword(String loginId, String password) {
	    Member m = memberRepository.findByLoginId(loginId);
	    
	    m.setPassword(password);
	    memberRepository.save(m);
	}

	@Transactional
	@Override
	public void updateOrderAddress(String orderCode, String receiver, String phone, 
	                              String zipcode, String addressMain, String addressDetail, 
	                              String deliveryMessage) {
	    Orders order = mypageRepository.findByOrderCode(orderCode);
	    if (order != null) {
	    	
	        order.setReceiver(receiver);
	        order.setPhone(phone);
	        order.setZipcode(zipcode);
	        order.setAddressMain(addressMain);
	        order.setAddressDetail(addressDetail);
	        order.setDeliveryMessage(deliveryMessage);
	        
	        Orders savedOrder = mypageRepository.save(order);
	    }
	}

	@Override
    @Transactional
    public boolean cancelOrder(String orderCode, String loginId) {
        try {
            // 1. 주문이 존재하는지 확인
            Orders order = mypageRepository.getOrderByCode(orderCode, loginId);
            
            
            int updateResult = mypageRepository.updateOrderState(orderCode, loginId, -1);
            System.out.println("주문 상태 업데이트 결과: " + updateResult);
            return updateResult > 0;
            

        } catch (Exception e) {
            System.err.println("주문 취소 중 오류 발생:");
            e.printStackTrace();
            return false;
        }
    }

	@Override
	public List<OrderItemDto> getOrderItemsByOrderCode(String orderCode) {
		
		List<OrderItem> orderItems = orderItemRepository.findByOrdersOrderCode(orderCode);
		
		// 엔티티를 DTO로 변환
	    List<OrderItemDto> orderItemDtos = new ArrayList<>();
	    for (OrderItem orderItem : orderItems) {
	        orderItemDtos.add(OrderItemDto.from(orderItem));
	    }
		
	    return orderItemDtos;
	}

}
