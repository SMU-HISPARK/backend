package com.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.dto.Artist;

public interface ArtistRepository extends JpaRepository<Artist, Integer> {

}
