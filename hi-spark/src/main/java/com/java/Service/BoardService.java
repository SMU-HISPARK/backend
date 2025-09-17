package com.java.Service;

import com.java.dto.Board;
import com.java.dto.Board_Like;
import com.java.dto.sComment;
import com.java.dto.sComment_Like;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

public interface BoardService {
	
	
	
    // 게시글 관련 기능
    List<Board> getBoardList();
    Board getBoard(int bno);
    Board saveBoard(Board board);
    Optional<Board> updateBoard(Board board);
    void deleteBoard(int bno);

    // 게시글 좋아요
    boolean toggleLike(int bno, String loginId);
    boolean isLikedByUser(int bno, String loginId);
    long getLikeCount(int bno);

    // 댓글 관련 기능
    sComment saveComment(sComment scomment);
    Optional<sComment> updateComment(sComment scomment);
    void deleteComment(int scno);
    List<sComment> getCommentsByBno(int bno);
    long getCommentCount(int bno);

    // 댓글 좋아요/취소
    boolean toggleCommentLike(int scno, int memberId);
    boolean isCommentLikedByUser(int scno, int memberId);
    long getCommentLikeCount(int scno);
    
    // 조회수 증가 기능
    void incrementBhit(int bno);
}