package com.java.dto;

import java.sql.Timestamp;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Chat {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name="chat_id")
   private int chatId;
   
   @ManyToOne
   @JoinColumn(name="user_id")
   private Member member;
   
    @Column(nullable = false)
   private int send;
   
   @Column(nullable = false, length = 2000)
   private String message;
   
   @UpdateTimestamp
   private Timestamp created_at;
   
   
}
