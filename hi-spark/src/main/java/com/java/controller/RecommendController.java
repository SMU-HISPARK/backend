package com.java.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.entity.Member;
import com.java.entity.OrderItem;
import com.java.entity.Orders;
import com.java.entity.Product;
import com.java.repository.MemberRepository;
import com.java.repository.OrderItemRepository;
import com.java.repository.OrderRepository;
import com.java.repository.ProductRepository;

@RestController
@RequestMapping("/api")
public class RecommendController {

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/orders")
    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }

    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/members")
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Autowired
    private OrderItemRepository orderItemRepository;

    @GetMapping("/orderitems")
    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }
}
