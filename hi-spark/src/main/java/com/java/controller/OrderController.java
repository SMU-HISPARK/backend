package com.java.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.java.entity.Cart;
import com.java.entity.CartItem;
import com.java.entity.Member;
import com.java.entity.OrderItem;
import com.java.entity.Orders;
import com.java.repository.CartItemRepository;
import com.java.repository.OrderRepository;
import com.java.service.CartService;
import com.java.service.MemberService;
import com.java.service.OrderService;

import jakarta.servlet.http.HttpSession;

@Controller
public class OrderController {

	@Autowired OrderService orderService;
	@Autowired CartService cartService;
	@Autowired MemberService memberService;
	@Autowired CartItemRepository cartItemRepository;
	@Autowired OrderRepository orderRepository;
	
	@PostMapping("/order/order_form")
	public String orderform(@RequestParam("cartItemIds") List<Integer> cartItemIds, 
			@RequestParam("quantities") List<Integer> quantities,
		HttpSession session, Model model) {
		
		
		int memberId = (int) session.getAttribute("memberId"); // 로그인 세션
		System.out.println(memberId);
		
		Member member = memberService.findById(memberId);

	    // 선택한 카트아이템만 가져오기
	    List<CartItem> cartItems = cartItemRepository.findAllById(cartItemIds);
	    Map<Integer, Integer> qtyMap = new HashMap<>();
	    for (int i = 0; i < cartItemIds.size(); i++) {
	        qtyMap.put(cartItemIds.get(i), quantities.get(i));
	    }

	    for (CartItem ci : cartItems) {
	        if (qtyMap.containsKey(ci.getCartitemId())) {
	            ci.setQuantity(qtyMap.get(ci.getCartitemId()));
	        }
	    }
	    cartItemRepository.saveAll(cartItems);

	    // DB에 Orders 생성하지 않고, 주문서에 필요한 데이터만 모델에 전달
	    model.addAttribute("member", member);
	    model.addAttribute("cartItems", cartItems);
	    

	    // 계산용
	    int total = cartItems.stream().mapToInt(i -> i.getProduct().getProductPrice() * i.getQuantity()).sum();
	    int shipping = total >= 50000 ? 0 : 3000;
	    model.addAttribute("total", total);
	    model.addAttribute("shipping", shipping);
	    model.addAttribute("grandTotal", total + shipping);

	    return "shop/shop_order";
	}
	
	@PostMapping("/order/order_finish")
	public String orderfinish(@RequestParam Map<String, String> params, HttpSession session) {
		    
	    //휴대전화
	    String phone = params.get("phone1") + "-" + params.get("phone2") + "-" + params.get("phone3");
	    //이메일
	    String email = params.get("email") + "@" + params.get("domain");
	    //주소
	    String addressMain = params.get("address1");
	    String addressDetail = params.get("address2");
	    //배송메시지
	    String deliveryMessage = "selfText".equals(params.get("deliveryMessage")) 
	        ? params.get("deliveryText") 
	        : params.get("deliveryMessage");
	    // 로그인된 회원 정보
	    Member member = (Member) session.getAttribute("member");
	    if (member == null) {
	        return "redirect:/login";
	    }
	    // 장바구니 정보 (주문할 상품들)
	    List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cartItems");
	    
	    
	    
	    
	    
		
	    
	    return "shop/shop_order_finish";
	}
	
	
}
