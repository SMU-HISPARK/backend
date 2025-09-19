package com.java.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.java.entity.Orders;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {
    private String orderCode;
    private int orderState;
    private int totalAmount;
    private int deliverCost;
    private String receiver;
    private String phone;
    private String addressMain;
    private String addressDetail;
    private String deliveryMessage;
    private Timestamp createdAt;
    private List<OrderItemDto> orderItems;

    public static OrderDto from(Orders order) {
        OrderDto dto = new OrderDto();
        dto.setOrderCode(order.getOrderCode());
        dto.setOrderState(order.getOrderState());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setDeliverCost(order.getDeliverCost());
        dto.setReceiver(order.getReceiver());
        dto.setPhone(order.getPhone());
        dto.setAddressMain(order.getAddressMain());
        dto.setAddressDetail(order.getAddressDetail());
        dto.setDeliveryMessage(order.getDeliveryMessage());
        dto.setCreatedAt(order.getCreatedAt());

        List<OrderItemDto> items = order.getOrderitems().stream()
                                       .map(OrderItemDto::from)
                                       .collect(Collectors.toList());
        dto.setOrderItems(items);

        return dto;
    }
}