package com.java.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class CartItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cartitem_id;
	
    @ManyToOne
    @JoinColumn(name = "cart_id")
	private Cart cart;
	
    @ManyToOne
    @JoinColumn(name = "product_id")
	private Product product;
	
	private int quantity;
	
	private String option_name;
	
	
}
