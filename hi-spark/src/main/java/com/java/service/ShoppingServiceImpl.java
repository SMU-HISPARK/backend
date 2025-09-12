package com.java.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.entity.Cart;
import com.java.entity.Member;
import com.java.repository.MemberRepository;
import com.java.repository.ShoppingRepository;

@Service
public class ShoppingServiceImpl implements ShoppingService {

	@Autowired ShoppingRepository shoppingRepository;
	@Autowired MemberService memberService;
	
	@Override
	public Cart getCartBySessionId(String sessionId) {
	    return shoppingRepository.findByMember_LoginId(sessionId)  //findById()
	            .orElseGet(() -> {
	            	Member member = memberService.findByLoginId(sessionId);
	            	
	            	// 새 카트 생성
	            	Cart newCart = new Cart();
	            	newCart.setMember(member);
	            	shoppingRepository.save(newCart);
	            	return newCart;

	            });
	}

}
