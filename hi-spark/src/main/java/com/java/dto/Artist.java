package com.java.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "artist")
public class Artist {  // 클래스명 대문자로 변경
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "artist_seq")
    @jakarta.persistence.SequenceGenerator(
        name = "artist_seq", 
        sequenceName = "artist_SEQ", 
        allocationSize = 1
    )
    private int ano;

    @Column(nullable = false, length = 50)
    private String name;
    @Column(nullable = false, length = 50)
    private String eng_name;

    @Column(nullable = false, length = 50)
    private String birth;

    @Column(nullable = false, length = 10)
    private String mbti;

    @Column(nullable = false)
    private Double height;

    @Column(nullable = false)
    private Double weight;

    @Column(nullable = false, length = 100)
    private String club;

    @Column(name = "club_reason", nullable = false, length = 200)  // 컬럼명 명시
    private String clubReason;  // 카멜케이스로 변경

    @Column(nullable = false, length = 200)
    private String favorite;

    @Column(nullable = false, length = 50)
    private String phone;

    @Column(length = 255)
    private String clubimage_01;
    @Column(length = 255)
    private String clubimage_02;
    @Column(length = 255)
    private String clubimage_03;

    
    @Column(length = 255)
    private String profitimage;

    @Column(length = 255)
    private String comment_text;

    @Column(nullable = false, length = 50)
    private String friend_name;
}