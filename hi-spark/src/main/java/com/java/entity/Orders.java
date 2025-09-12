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
	
	@Column(nullable=false, length=100)
	private String address_main;
	
	@Column(nullable=false, length=100)
	private String address_detail;
	
	@Column(nullable=false) //0: 상품준비중 1:배송중 2:배송완료 -1: 취소
	private int order_state;
	
	@Column(nullable=false)
	private String payment_method;
	
	@Column(nullable=false)
	private int total_amount;
	
	@Column(nullable=false)
	private int deliver_cost;
	
	@Column(nullable=false, length=100)
	private String delivery_message;
	
	@CreationTimestamp
	private Timestamp created_at;
	
	@UpdateTimestamp
	private Timestamp updated_at;

}
