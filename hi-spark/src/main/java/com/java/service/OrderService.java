package com.java.service;

import java.util.List;

import com.java.entity.Cart;
import com.java.entity.CartItem;
import com.java.entity.Orders;

public interface OrderService {

	Orders createOrder(Orders order, List<CartItem> cartItems);


}
