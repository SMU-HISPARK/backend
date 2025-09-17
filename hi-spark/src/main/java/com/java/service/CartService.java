package com.java.service;

import java.util.Optional;

import com.java.entity.Cart;
import com.java.entity.Member;

public interface CartService {
	
	
	
	// 상품디테일에 전송받은 후 장바구니 화면
	Cart getCartByMember(Member member);

	Cart getCartByMember_MemberId(int memberId);
	
	// 장바구니 가져오기

	Cart getOrCreateCart(Member member);



}