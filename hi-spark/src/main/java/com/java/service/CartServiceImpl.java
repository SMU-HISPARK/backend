package com.java.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.entity.Cart;
import com.java.entity.CartItem;
import com.java.entity.Member;
import com.java.repository.MemberRepository;
import com.java.repository.CartItemRepository;
import com.java.repository.CartRepository;

@Service
public class CartServiceImpl implements CartService {

    @Autowired private CartRepository cartRepository;
    @Autowired private CartItemRepository cartItemRepository;  // ✅ CartItemRepository 추가
    @Autowired private MemberRepository memberRepository;

    @Override
    public Cart getCartByMember(Member member) {
        return cartRepository.findByMember(member)
                .orElseGet(Cart::new);
    }

    @Override
    public Cart getCartByMember_MemberId(int memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("회원이 존재하지 않습니다."));
        return cartRepository.findByMember(member)
                .orElseThrow(() -> new RuntimeException("장바구니가 존재하지 않습니다."));
    }

    @Override
    public CartItem save(CartItem cartItem) {
        return cartItemRepository.save(cartItem);  // ✅ CartRepository 대신 CartItemRepository 사용
    }

    @Override
    public Cart getOrCreateCart(Member member) {
        return cartRepository.findByMember(member)
                .orElseGet(() -> {
                    Cart newCart = Cart.builder()
                            .member(member)
                            .build();
                    return cartRepository.save(newCart);
                });
    }
}
