package com.java.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.java.dto.Member;
import com.java.dto.OrderItemDto;
import com.java.dto.Orders;
import com.java.entity.OrderItem;
import com.java.service.MemberService;
import com.java.service.MypageService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/mypage")
public class MypageController {

	@Autowired MemberService memberService;
	@Autowired MypageService mypageService;
	
	@Autowired HttpSession session;
	
    @Value("${api1.service_key}")
    private String dservice_key;


    @GetMapping("/member")
    public String mypage(Model model) {
        String loginId = (String) session.getAttribute("session_id");
        
        
        // 로그인한 유저 정보만 DB에서 조회
        Member m = memberService.findByLoginId(loginId);
        
        model.addAttribute("m", m);
        
        
        System.out.println(m);
        return "mypage/member";
    }
    
    @ResponseBody
    @PostMapping("/member/update")
    public String updateMember(@RequestParam(name = "nickname") String nickname,
    		@RequestParam(name = "phone1") String phone1,
    		@RequestParam(name = "phone2") String phone2,
    		@RequestParam(name = "phone3") String phone3) {
    	
    	String loginId = (String) session.getAttribute("session_id");
    	
    	
    	mypageService.updateMember(loginId,nickname,phone1,phone2,phone3);
    	
    	return "OK";
    	
    }

    @GetMapping("/chgpw")
    public String chgpw() {
        return "mypage/chgpw";
    }
    

	@PostMapping("/chgpw")
	public String chgpw(@RequestParam(name = "currPassword") String currPassword,
	                    @RequestParam(name = "newPassword") String newPassword, 
	                    Model model) {
	    
	    String loginId = (String) session.getAttribute("session_id");
	    Member member = memberService.findByLoginId(loginId); 
	    
	    // 현재 비밀번호 확인
	    if (!member.getPassword().equals(currPassword)) {
	        model.addAttribute("error", "현재 비밀번호가 일치하지 않습니다.");
	        return "mypage/chgpw";
	    }
	    
	    // 새 비밀번호로 변경
	    mypageService.updateMemberPassword(loginId, newPassword);
	    
	    model.addAttribute("success", "비밀번호가 성공적으로 변경되었습니다.");
	    return "mypage/chgpw";
	}

    @GetMapping("/club")
    public String club() {
    	
    	
        return "mypage/club";
    }

    @GetMapping("/community")
    public String community() {
        return "mypage/community";
    }

    @GetMapping("/shop")
    public String shop(Model model, HttpSession session) {
        String loginId = (String) session.getAttribute("session_id");
        System.out.println(loginId);
        if (loginId == null) {
            return "redirect:/"; // 로그인하지 않은 경우 로그인 페이지로 리다이렉트
        }
        
        List<Orders> ordersList = mypageService.getOrdersByMemberId(loginId);
        model.addAttribute("ordersList", ordersList);
        return "mypage/shop";
    }

    @ResponseBody
    @GetMapping("/shop/detail")
    public Map<String, Object> getOrderDetail(@RequestParam(name = "orderCode") String orderCode) {
    	String loginId = (String) session.getAttribute("session_id");

        // 해당 회원의 주문인지 확인 후 반환
        Orders order = mypageService.getOrderByCode(orderCode);
        if (order.getMember().getLoginId().equals(loginId)) {
        	
        	// DTO
        	List<OrderItemDto> orderItems = mypageService.getOrderItemsByOrderCode(orderCode);
        	
        	Map<String, Object> result = new HashMap<>();
            result.put("order", order);
            result.put("orderItems", orderItems);
        	
        	return result;
        }
        return null; // 권한이 없거나 주문이 없는 경우
    }
    
    @ResponseBody
    @GetMapping("/shop/tracking")
    public String tracking(@RequestParam(name = "orderCode") String orderCode){
        
    	return mypageService.getTrackingStatus(orderCode);

    }

    @GetMapping("/shopupdate")
    public String shopupdate(@RequestParam(name = "orderCode") String orderCode, Model model) {
        String loginId = (String) session.getAttribute("session_id");
        if (loginId == null) {
            return "redirect:/";
        }
        
        Orders order = mypageService.getOrderByCode(orderCode);
        model.addAttribute("order", order);
        
        System.out.println(order);
        return "mypage/shopupdate";
    }

    @PostMapping("/shopupdate")
    @ResponseBody
    public String updateShopInfo(@RequestParam(name = "orderCode") String orderCode, 
                               @RequestParam(name = "receiver") String receiver,
                               @RequestParam(name = "phone") String phone,
                               @RequestParam(name = "zipcode") String zipcode,
                               @RequestParam(name = "addressMain") String addressMain,
                               @RequestParam(name = "addressDetail") String addressDetail,
                               @RequestParam(name = "deliveryMessage") String deliveryMessage) {
        String loginId = (String) session.getAttribute("session_id");
        if (loginId == null) {
            return "FAIL";
        }
        
        try {
            mypageService.updateOrderAddress(orderCode, receiver, phone, zipcode, 
                                           addressMain, addressDetail, deliveryMessage);
            return "SUCCESS";
        } catch (Exception e) {
            return "FAIL";
        }
    }
    
    @PostMapping("/shop/cancel")
    @ResponseBody
    public Map<String, Object> cancelOrder(@RequestParam(name = "orderCode") String orderCode, HttpSession session) {
        Map<String, Object> response = new HashMap<>();

        try {
            String loginId = (String) session.getAttribute("session_id");

            if (loginId == null) {
                response.put("success", false);
                response.put("message", "로그인이 필요합니다.");
                return response;
            }

            // 주문 취소 서비스 호출
            boolean cancelResult = mypageService.cancelOrder(orderCode, loginId);

            if (cancelResult) {
                response.put("success", true);
                response.put("message", "주문이 성공적으로 취소되었습니다.");
            } else {
                response.put("success", false);
                response.put("message", "주문 취소에 실패했습니다.");
            }

        } catch (Exception e) {
            e.printStackTrace(); // 디버깅을 위해 추가
            response.put("success", false);
            response.put("message", "주문취소 처리 중 오류가 발생했습니다.");
        }

        return response;
    }

    
	@GetMapping("/point")
	public String point(Model model) {
	    String loginId = (String) session.getAttribute("session_id");
	    Member member = memberService.findByLoginId(loginId);
	    
	    model.addAttribute("currentPoint", member.getPoint());
	    
	    return "mypage/point";
	}
    
    @PostMapping("/point")
    public String point(@RequestParam("point") int point, Model model) {
        
        String loginId = (String) session.getAttribute("session_id");
        
        // 현재 포인트를 가져와서 누적
        Member currentMember = memberService.findByLoginId(loginId);
        int currentPoint = currentMember.getPoint();
        int newPoint = currentPoint + point;
        
        mypageService.updateMemberPoint(loginId, newPoint);
        
        return "redirect:/mypage/point";
    }
}

