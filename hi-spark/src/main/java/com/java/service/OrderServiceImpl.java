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

	@Autowired OrderRepository orderRepository;

	@Override
	public void save(Orders order) {
		orderRepository.save(order);
		
	}
	    
    public String generateOrderCode() {
        // O20250915-00001, 날짜 + 순번
        String dateStr = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        // 00001~99999 (앞자리 0 포함 5자리)
        String randomStr = String.format("%05d", (int)(Math.random() * 99999) + 1);
        return "O" + dateStr + "-" + randomStr;
    }
    

}
