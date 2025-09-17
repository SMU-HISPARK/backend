package com.java.service;

import com.java.entity.*;
import com.java.repository.*;
import com.java.service.OrderService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional  // 트랜잭션 처리 필수!
public class OrderServiceImpl implements OrderService {


		@Autowired OrderRepository orderRepository;
		@Autowired CartItemService cartItemService;
		@Autowired ProductService productService;
		@Autowired MemberService memberService;

	    @Override //주문 생성·재고 차감·포인트 차감
	    public Orders placeOrder(int memberId, List<Integer> selectedItemIds, Map<String,String> params) {
	        Member member = memberService.findById(memberId);
	        if (member == null) throw new RuntimeException("로그인 필요");

	        String deliveryMessage = "selfText".equals(params.get("deliveryMessage"))
	                ? params.get("deliveryText")
	                : params.get("deliveryMessage");

	        String phone = params.get("phone1") + "-" + params.get("phone2") + "-" + params.get("phone3");

	        String orderCode = generateOrderCode();

	        Orders order = new Orders();
	        order.setOrderCode(orderCode);
	        order.setMember(member);
	        order.setReceiver(params.get("acceptant"));
	        order.setPhone(phone);
	        order.setZipcode(params.get("zipcode"));
	        order.setAddressMain(params.get("address1"));
	        order.setAddressDetail(params.get("address2"));
	        order.setOrderState(0);
	        order.setPaymentMethod(params.get("paymethod"));
	        order.setDeliveryMessage(deliveryMessage);

	        int deliverCost = Integer.parseInt(params.get("shipping"));
	        order.setDeliverCost(deliverCost);

	        List<OrderItem> orderItems = new ArrayList<>();
	        int totalAmount = 0;

	        for (int cartItemId : selectedItemIds) {
	            CartItem cartItem = cartItemService.findById(cartItemId);
	            if (cartItem == null) continue;

	            Product product = cartItem.getProduct();
	            int orderQty = cartItem.getQuantity();

	            if (product.getProductQuantity() < orderQty) {
	                throw new RuntimeException("재고 부족: " + product.getProductName());
	            }

	            product.setProductQuantity(product.getProductQuantity() - orderQty);
	            productService.save(product);

	            OrderItem orderItem = new OrderItem();
	            orderItem.setOrders(order);
	            orderItem.setProduct(product);
	            orderItem.setQuantity(orderQty);
	            orderItem.setPrice(product.getProductPrice() * orderQty);

	            totalAmount += orderItem.getPrice();
	            orderItems.add(orderItem);

	            cartItemService.deleteById(cartItemId);
	        }

	        order.setTotalAmount(totalAmount);
	        order.setOrderitems(orderItems);

	        int totalPoint = orderItems.stream()
	        	    .mapToInt(i -> i.getPrice())
	        	    .sum();

	        	// 적립금 전액 결제만 지원
	        	if ("적립금".equals(params.get("paymethod"))) {
	        	    if (member.getPoint() < totalPoint) {
	        	        throw new RuntimeException("포인트 부족");
	        	    }
	        	    member.setPoint(member.getPoint() - totalPoint);
	        	    memberService.save(member);
	        	}

	        return orderRepository.save(order);
	    }

	    
	    @Override  // 주문번호 생성
	    public String generateOrderCode() {
		    String orderCode;
		    do {
		        String dateStr = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		        String randomStr = String.format("%05d", (int)(Math.random() * 99999) + 1);
		        orderCode = "O" + dateStr + "-" + randomStr;
		    } while (orderRepository.existsByOrderCode(orderCode)); // 중복 체크
		    return orderCode;
		}
	    
		@Override  //주문 저장
		public void save(Orders order) {
			orderRepository.save(order);
		}


		@Override
		public Orders findByOrderCode(String orderCode) {
			Orders order = orderRepository.findByOrderCode(orderCode);
			return order;
		}

}