package com.java.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int orderitem_id;
	
	@ManyToOne
	@JoinColumn(name="order_id")
	@JsonIgnore
	@ToString.Exclude
	private Orders orders; 
	
	@ManyToOne
	@JoinColumn(name="product_id")
	private Product product;
	
	@Column(nullable = false)
	private int quantity;
	
	@Column(nullable = false)
	private int price;
	
}
