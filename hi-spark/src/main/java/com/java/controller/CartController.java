package com.java.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
@RequestMapping("/shop")
public class CartController {

    private final CartItemRepository cartItemRepository;
	
	@Autowired CartService cartService;
	@Autowired MemberService memberService;
	@Autowired CartItemService cartItemService;
	@Autowired MainService mainService;
    CartController(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }
    @PostMapping("/cart/add")
    public String addToCart(@RequestParam("productId") int productId,
                            @RequestParam("quantity") int quantity,
                            HttpServletRequest request,
                            Model model) {

        HttpSession session = request.getSession();
        int memberId = 1; // 로그인 구현 전 테스트용
        session.setAttribute("memberId", memberId);

        Member member = memberService.findById(memberId);
        Product product = mainService.findById(productId);

        Cart cart = cartService.getOrCreateCart(member);

        CartItem cartItem = CartItem.builder()
                .cart(cart)
                .product(product)
                .quantity(quantity)
                .build();

        cartItemService.save(cartItem);

        // 🟢 JSP에서 forEach 돌릴 수 있도록 담기
        model.addAttribute("cart", cartService.getCartByMember(member));

        return "shop/shop_cart"; // forward
    }
	
	
	//상품디테일에 전송받은 후 장바구니 화면 
	@GetMapping("/cart")
	public String cart(HttpServletRequest request, Model model) {
	    HttpSession session = request.getSession();
	    int memberId = 1; // 로그인 붙이기 전이라면 테스트용
	    session.setAttribute("memberId", memberId);

	    Member member = memberService.findById(memberId);
	    if (member == null) {
	        model.addAttribute("error", "member not found");
	        return "shop/shop_cart";
	    }

	    Cart cart = cartService.getCartByMember(member);
	    model.addAttribute("cart", cart);

	    return "shop/shop_cart"; // JSP
	}

	
	public String addToCart(
            @RequestParam("cartId") int cartId,
            @RequestParam("productId") int productId,
            @RequestParam("quantity") int quantity) {

        cartItemService.addCartItem(cartId, productId, quantity);

        return "redirect:/cart"; // 장바구니 화면으로 리다이렉트
    }
	
	
	
	
	
	
		
	
	
	
	
}