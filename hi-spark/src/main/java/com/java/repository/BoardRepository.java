package com.java.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.java.dto.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {
    
    @Query("SELECT b FROM Board b WHERE b.b_type = :bType ORDER BY b.bdate DESC")
    List<Board> findByBType(@Param("bType") int bType);
    
    // 페이지네이션을 위한 메서드 추가 (Member 조인 포함)
    @Query("SELECT b FROM Board b WHERE b.b_type = :bType ORDER BY b.bdate DESC")
    Page<Board> findByBTypeWithPaging(@Param("bType") int bType, Pageable pageable);
    
    // 제목 검색
    @Query("SELECT b FROM Board b LEFT JOIN FETCH b.member WHERE b.b_type = :bType AND b.btitle LIKE %:keyword% ORDER BY b.bdate DESC")
    Page<Board> findByBTypeAndBtitleContaining(@Param("bType") int bType, @Param("keyword") String keyword, Pageable pageable);

    // 내용 검색
    @Query("SELECT b FROM Board b LEFT JOIN FETCH b.member WHERE b.b_type = :bType AND b.bcontent LIKE %:keyword% ORDER BY b.bdate DESC")
    Page<Board> findByBTypeAndBcontentContaining(@Param("bType") int bType, @Param("keyword") String keyword, Pageable pageable);

    // 작성자 검색
    @Query("SELECT b FROM Board b LEFT JOIN FETCH b.member WHERE b.b_type = :bType AND b.member.name LIKE %:keyword% ORDER BY b.bdate DESC")
    Page<Board> findByBTypeAndMemberNameContaining(@Param("bType") int bType, @Param("keyword") String keyword, Pageable pageable);
    
 // 이전글 조회 (현재 글보다 작은 bno 중 가장 큰 것)
    @Query("SELECT b FROM Board b WHERE b.bno < :bno AND b.b_type = :bType ORDER BY b.bno DESC LIMIT 1")
    Board findTopByBnoLessThanAndBTypeOrderByBnoDesc(@Param("bno") int bno, @Param("bType") int bType);

    // 다음글 조회 (현재 글보다 큰 bno 중 가장 작은 것)
    @Query("SELECT b FROM Board b WHERE b.bno > :bno AND b.b_type = :bType ORDER BY b.bno ASC LIMIT 1")
    Board findTopByBnoGreaterThanAndBTypeOrderByBnoAsc(@Param("bno") int bno, @Param("bType") int bType);

    
}