package com.java.service;

import com.java.entity.Cart;

public interface ShoppingService {

	Cart getCartBySessionId(String sessionId);

}
