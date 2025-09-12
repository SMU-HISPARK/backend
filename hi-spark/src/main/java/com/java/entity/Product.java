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
@Builder         //부분생성자
@Entity
public class Product {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name="product_id")
   private int productId;
   
   @Column(name="product_name",nullable=false,length=100)
   private String productName;
   
   @Column(name="product_img",nullable=false,length=500)
   private String productImg;
   
   @Column(name="product_price",nullable=false)
   private int productprice;
   
   @Column(name="delivery_fee",nullable=false)
   private int delfee;
   
   @Column(name="product_quantity", nullable=false)
   private int productQuantity;  //수량
   
   @Column(name="product_content",length=12000)
   private String productContent;  //상품설명
}
