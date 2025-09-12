package com.java.service;

import com.java.entity.Cart;

public interface OrderService {

	Cart getCartByMember_Id(String session_id);

}
