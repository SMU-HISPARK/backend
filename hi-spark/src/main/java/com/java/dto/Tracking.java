package com.java.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Tracking {

    @Id
    @Column(name = "invoice_no", nullable = false)
    private Long invoiceNo;  // 운송장 번호 (PK)

    @Column(nullable = false, length = 20)
    private String courier;  // 택배사 이름/코드
    
    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
    
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = true) // Orders와 연결
    private Orders orders;
    
    
}