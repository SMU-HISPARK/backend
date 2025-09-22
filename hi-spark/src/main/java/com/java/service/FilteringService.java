package com.java.service;

import com.java.dto.FilteringResponseDto;

public interface FilteringService {
    FilteringResponseDto filterText(String originalTitle, String originalContent);
}