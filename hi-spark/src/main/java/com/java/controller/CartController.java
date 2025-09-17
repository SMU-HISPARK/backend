package com.java.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.java.entity.Cart;
import com.java.entity.CartItem;
import com.java.entity.Member;
import com.java.entity.Product;
import com.java.repository.CartItemRepository;
import com.java.service.MemberService;
import com.java.service.CartItemService;
import com.java.service.CartService;
import com.java.service.MainService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class CartController {
	
	@Autowired CartService cartService;
	@Autowired CartItemService cartItemService;
	@Autowired MemberService memberService;
	@Autowired MainService mainService;
	
	//카트 페이지 열기
	@GetMapping("/shop/cart")
	public String cart(
			HttpSession session, 
			Model model) {
		
		Object oMemberId = session.getAttribute("member_id");
		if (oMemberId == null) {
	        model.addAttribute("msg", "로그인이 필요합니다.");
	        model.addAttribute("url", "/member/login");
	        return "alert"; // alert.jsp
	    }

	    Integer memberId = (Integer) oMemberId;
	    Member member = memberService.findById(memberId);

	    if (member == null) {
	        model.addAttribute("msg", "회원 정보가 없습니다.");
	        model.addAttribute("url", "/shop");
	        return "alert"; // alert.jsp
	    }

	    Cart cart = cartService.getCartByMember(member);
	    model.addAttribute("cart", cart);
	    
	    int cartCount = (cart != null && cart.getItems() != null) ? cart.getItems().size() : 0;
	    session.setAttribute("cart_count", cartCount);
	    return "shop/shop_cart";
	}
	
	
	//카트에 아이템 추가
	@PostMapping("/shop/cart/add")
	public String addToCart(@RequestParam("productId") int productId,
            @RequestParam("quantity") int quantity,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes,
            Model model) {

		HttpSession session = request.getSession();
		int memberId = (int) session.getAttribute("member_id");
		Member member = memberService.findById(memberId);
		Cart cart = cartService.getOrCreateCart(member);
		
		// 장바구니에 이미 있는지 먼저 확인
		Optional<CartItem> existingItemOpt = cartItemService.findByCartAndProduct(cart,mainService.findById(productId));
		
		// 실제 저장 (수량 증가 or 신규 추가)
		cartItemService.addCartItem(cart, productId, quantity);
		
		// JSP에서 forEach 돌릴 수 있도록 담기
		model.addAttribute("cart", cartService.getCartByMember(member));
		
		// 메시지 분기
		if (existingItemOpt.isPresent()) {
			redirectAttributes.addFlashAttribute("msg", "이미 장바구니에 있는 상품입니다. 수량이 증가되었습니다.");
		} else {
			redirectAttributes.addFlashAttribute("msg", "장바구니에 상품을 추가했습니다.");
		}
		
		
			return "redirect:/shop/cart";
	}

	//카트아이템 삭제
	@DeleteMapping("/cart/delete")
	@ResponseBody
	public ResponseEntity<Void> deleteCartItem(@RequestParam("cartItemId") int CartItemId, HttpSession session) {
		cartItemService.deleteById(CartItemId);
		Integer memberId = (Integer) session.getAttribute("member_id");
	    Cart cart = cartService.getCartByMember_MemberId(memberId);
	    int cartCount = (cart != null && cart.getItems() != null) ? cart.getItems().size() : 0;
	    session.setAttribute("cart_count", cartCount); //카트저장

	    return ResponseEntity.ok().build();
	}
	
	
	@GetMapping({"/cart/delete","/shop/cart/add"})
	public String alert(Model model) {
		model.addAttribute("msg", "권한이 없습니다.");
        model.addAttribute("url", "/shop");
        return "alert"; // alert.jsp
	}
	
	
	
	
	
}
