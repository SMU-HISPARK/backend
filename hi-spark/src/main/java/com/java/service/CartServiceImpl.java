package com.java.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.entity.Cart;
import com.java.entity.Member;
import com.java.repository.MemberRepository;
import com.java.repository.CartRepository;

@Service
public class CartServiceImpl implements CartService {

	@Autowired CartRepository cartRepository;
	@Autowired MemberRepository memberRepository;
	
	@Override
	public Cart getCartByMember(Member member) {
	    Cart cart = cartRepository.findByMember(member).orElseGet(
	    		() -> {return (new Cart());}
	    		);
		return cart;
	}

	@Override
	public Cart getCartByMember_MemberId(int memberId) {
		Member member = memberRepository.findById(memberId)
	            .orElseThrow(() -> new RuntimeException("회원이 존재하지 않습니다."));
	    return cartRepository.findByMember(member)
	            .orElseThrow(() -> new RuntimeException("장바구니가 존재하지 않습니다."));
	}


}
