package com.java.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.java.entity.Member;

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
   
   @ManyToOne
   @JoinColumn(name="artist_id")
   private Artist artist;
  
   @Column(nullable = false)
   private int send;
   
   @Column(nullable = false, length = 2000)
   private String message;
   
   @Column(name = "created_at")
   @CreationTimestamp
   private Timestamp createdAt;
   
   
}
