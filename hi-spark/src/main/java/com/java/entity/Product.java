package com.java.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder  	    //부분생성자
@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int product_id;
	
	@Column(nullable=false,length=100)
	private String product_name;
	
	@Column(nullable=false,length=500)
	private String product_img;
	
	@Column(nullable=false)
	private int product_price;
	
//	@Column(nullable=false)
//	private boolean has_option;
	
	@Column(nullable=false)
	private int product_quantity;  //수량
	
	@Column(length=1000)
	private String product_content;  //상품설명
}
