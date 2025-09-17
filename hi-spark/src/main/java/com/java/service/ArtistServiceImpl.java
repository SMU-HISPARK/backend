package com.java.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.dto.Artist;
import com.java.repository.ArtistRepository;
@Service
public class ArtistServiceImpl implements ArtistService {

	@Autowired ArtistRepository artistrepository;
	//아티스트 상세페이지
	@Override
	public Artist findById(int ano) {
		Artist artist = artistrepository.findById(ano).orElseThrow(() -> new RuntimeException("Artist not found with id: " + ano));;
		return artist;
	}
	// 아티스트 상세페이지내 멤버리스트
	@Override
	public List<Artist> findAll() {
		List<Artist> list = artistrepository.findAll();
		return list;
	}


}
