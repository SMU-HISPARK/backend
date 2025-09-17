package com.java.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClubDto {
    private Integer clubId;
    private String name;        // 동아리 이름
    private String imageUrl;    // 절대경로로 JSP에서 사용
    private Timestamp finishedAt;
}