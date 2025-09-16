package com.java.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
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
import com.java.service.CartItemService;
import com.java.service.CartService;
import com.java.service.MemberService;
import com.java.service.OrderService;

import jakarta.servlet.http.HttpSession;

@Controller
public class OrderController {

	@Autowired OrderService orderService;
	@Autowired CartService cartService;
	@Autowired MemberService memberService;
	@Autowired CartItemService cartItemService;
	
	@PostMapping("/order/order_form")
	public String orderform(@RequestParam("cartItemIds") List<Integer> cartItemIds, 
			@RequestParam("quantities") List<Integer> quantities,
		HttpSession session, Model model) {
		
		
		int memberId = (int) session.getAttribute("memberId"); // 로그인 세션
		System.out.println(memberId);
		
		Member member = memberService.findById(memberId);

	    // 선택한 카트아이템만 가져오기
	    List<CartItem> cartItems = cartItemService.findAllById(cartItemIds);
	    Map<Integer, Integer> qtyMap = new HashMap<>();
	    for (int i = 0; i < cartItemIds.size(); i++) {
	        qtyMap.put(cartItemIds.get(i), quantities.get(i));
	    }

	    for (CartItem ci : cartItems) {
	        if (qtyMap.containsKey(ci.getCartitemId())) {
	            ci.setQuantity(qtyMap.get(ci.getCartitemId()));
	        }
	    }
	    cartItemService.saveAll(cartItems);

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
	public String orderfinish(@RequestParam("selectedItems") List<Integer> selectedItemIds,
	                          @RequestParam Map<String,String> params,
	                          HttpSession session) {

	    // 1. 회원 조회
	    int memberId = (int) session.getAttribute("memberId");
	    Member member = memberService.findById(memberId);
	    if(member == null) return "redirect:/login";
	    

	    String deliveryMessage = "selfText".equals(params.get("deliveryMessage")) 
                ? params.get("deliveryText") 
                : params.get("deliveryMessage");
	    
	    String phone = params.get("phone1")+"-"+params.get("phone2")+"-"+params.get("phone3");
	    
	    String orderCode = orderService.generateOrderCode();
	    
	    // 2. 주문 객체 생성
	    Orders order = new Orders();
	    
	    order.setOrderCode(orderCode);
	    order.setMember(member);
	    order.setReceiver(params.get("acceptant"));
	    order.setPhone(phone);
	    order.setZipcode(params.get("zipcode"));
	    order.setAddressMain(params.get("address1"));
	    order.setAddressDetail(params.get("address2"));
	    order.setOrderState(0); // 상품 준비중
	    order.setPaymentMethod(params.get("paymethod"));
	    order.setDeliveryMessage(deliveryMessage);
	    
	    int totalAmount = 0;
	    int deliverCost = Integer.parseInt(params.get("shipping")); // JSP에서 shipping 가져오던 변수
	    order.setDeliverCost(deliverCost);

	    List<OrderItem> orderItems = new ArrayList<>();

	    // 3. 선택된 cartItem 처리
	    for(int cartItemId : selectedItemIds) {
	        CartItem cartItem = cartItemService.findById(cartItemId); // DB에서 가져오기
	        if(cartItem == null) continue;

	        OrderItem orderItem = new OrderItem();
	        orderItem.setOrders(order);
	        orderItem.setProduct(cartItem.getProduct());
	        orderItem.setQuantity(cartItem.getQuantity());
	        orderItem.setPrice(cartItem.getProduct().getProductPrice() * cartItem.getQuantity());

	        totalAmount += orderItem.getPrice();
	        orderItems.add(orderItem);

	        // 4. cartItem 삭제
	        cartItemService.deleteById(cartItemId);
	    } 

	    order.setTotalAmount(totalAmount);
	    order.setOrderitems(orderItems);

	    // 5. 주문 저장
	    orderService.save(order);
	    
	    System.out.println(order);

	    return "shop/shop_order_finish";
	}
	
	
}
