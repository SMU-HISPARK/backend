package com.java.dto;

import com.java.entity.OrderItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {
    private int orderitem_id;
    private int quantity;
    private int price;
    private ProductDto product;
    
    // OrderItem 엔티티에서 DTO로 변환하는 메소드
    public static OrderItemDto from(OrderItem orderItem) {
        OrderItemDto dto = new OrderItemDto();
        dto.setOrderitem_id(orderItem.getOrderitem_id());
        dto.setQuantity(orderItem.getQuantity());
        dto.setPrice(orderItem.getPrice());
        dto.setProduct(ProductDto.from(orderItem.getProduct()));
        return dto;
    }
}