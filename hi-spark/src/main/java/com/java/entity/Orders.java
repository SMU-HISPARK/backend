package com.java.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Orders {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int order_id;
	
	@Column(nullable = false,length=100)
	private String order_code;
	
	@ManyToOne
	@JoinColumn(name="member_id")
	private Member member;
	
	@Column(nullable=false, length=100)
	private String receiver;
	
	@Column(nullable=false, length=100)
	private String phone;
	
	@Column(nullable=false, length=100)
	private String zipcode;
	
	@Column(name="address_main", nullable=false, length=100)
	private String addressMain;
	
	@Column(name="adress_detail", nullable=false, length=100)
	private String addressDetail;
	
	@Column(name="order_state", nullable=false) //0: 상품준비중 1:배송중 2:배송완료 -1: 취소
	private int orderState;
	
	@Column(name="payment_method", nullable=false)
	private String paymentMethod;
	
	@Column(name="total_amount", nullable=false)
	private int totalAmount;
	
	@Column(name="deliver_cost", nullable=false)
	private int deliverCost;
	
	@Column(name="delivery_message", nullable=false, length=100)
	private String deliveryMessage;
	
	@CreationTimestamp
	@Column(name="created_at")
	private Timestamp createdAt;
	
	@UpdateTimestamp
	@Column(name="updated_at")
	private Timestamp updatedAt;

}
