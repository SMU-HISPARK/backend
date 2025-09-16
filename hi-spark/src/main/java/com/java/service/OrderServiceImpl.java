package com.java.service;

import com.java.entity.*;
import com.java.repository.*;
import com.java.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

	@Override
	public Orders createOrder(Orders order, List<CartItem> cartItems) {
		// TODO Auto-generated method stub
		return null;
	}
    

}
