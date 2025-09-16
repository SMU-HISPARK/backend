package com.java.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.java.dto.Artist;

@Service
public interface ArtistService {
	// 아티스트 상세페이지
	Artist findById(int ano);

	List<Artist> findAll();

}
