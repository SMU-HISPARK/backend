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
	@Autowired MemberService memberService;
	
	@Override
	public Cart findByMember(Member member) {
	    Cart cart = cartRepository.findByMember(member).orElseGet(
	    		() -> {return (new Cart());}
	    		);
		return cart;
	}

}
