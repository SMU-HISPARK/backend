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
import org.springframework.web.bind.annotation.ResponseBody;

import com.java.entity.Cart;
import com.java.entity.CartItem;
import com.java.entity.Member;
import com.java.entity.OrderItem;
import com.java.entity.Orders;
import com.java.entity.Product;
import com.java.repository.CartItemRepository;
import com.java.repository.OrderRepository;
import com.java.service.CartItemService;
import com.java.service.CartService;
import com.java.service.MemberService;
import com.java.service.OrderService;
import com.java.service.ProductService;

import jakarta.servlet.http.HttpSession;

@Controller
public class OrderController {

	@Autowired OrderService orderService;
	@Autowired ProductService productService;
	@Autowired CartService cartService;
	@Autowired MemberService memberService;
	@Autowired CartItemService cartItemService;
	
	
	@PostMapping("/order/order_form")
	public String orderform(@RequestParam("cartItemIds") List<Integer> cartItemIds, 
			@RequestParam("quantities") List<Integer> quantities,
		HttpSession session, Model model) {
		
		
		int memberId = (int) session.getAttribute("member_id"); // 로그인 세션
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
	    
	    int selectedCount = cartItemIds.size();

	    // DB에 Orders 생성하지 않고, 주문서에 필요한 데이터만 모델에 전달
	    model.addAttribute("member", member);
	    model.addAttribute("cartItems", cartItems);
	    model.addAttribute("selectedCount", selectedCount);
	    

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
	                          HttpSession session, Model model) {

	    int memberId = (int) session.getAttribute("member_id");

	    Orders order = orderService.placeOrder(memberId, selectedItemIds, params);
	    
	    Integer cartCount = (Integer) session.getAttribute("cart_count");
	    int orderItemCount = order.getOrderitems().size();
	    int newCartCount = (cartCount != null ? cartCount : 0) - orderItemCount;
	    session.setAttribute("cart_count", newCartCount);

	    return "redirect:/order/finish-view?orderCode=" + order.getOrderCode();
	}
	
	@GetMapping("/order/finish-view")
	public String orderFinishView(@RequestParam("orderCode") String orderCode, HttpSession session, Model model) {
		int memberId = (int) session.getAttribute("member_id");
		Member member = memberService.findById(memberId);

		Orders order = orderService.findByOrderCode(orderCode); // DB 조회
		if (!order.getMember().getMemberId().equals(member.getMemberId())) {
			model.addAttribute("msg", "권한이 없습니다.");
	        model.addAttribute("url", "/shop");
			return "alert";
		}

		
	    model.addAttribute("order", order);
	    model.addAttribute("selectedCount", order.getOrderitems().size());
	    
	    
	    
	    return "shop/shop_order_finish";
	}
	
	
	
}