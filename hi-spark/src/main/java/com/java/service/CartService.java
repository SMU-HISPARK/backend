package com.java.service;

import java.util.Optional;

import com.java.entity.Cart;
import com.java.entity.Member;

public interface CartService {

	Cart getCartByMember(Member member);

	Cart getCartByMember_MemberId(int memberId);

}
