package com.java.Service;

import com.java.dto.Board;
import com.java.dto.Board_Like;
import com.java.dto.Comment;
import com.java.dto.Comment_Like; // 추가

import java.util.List;
import java.util.Optional;

public interface BoardService {
    // 게시글 관련 기능
    List<Board> getBoardList();
    Board getBoard(int bno);
    Board saveBoard(Board board);
    Optional<Board> updateBoard(Board board);
    void deleteBoard(int bno);

    // 게시글 좋아요
    boolean toggleLike(int bno, String memberId);

    // 댓글 관련 기능
    Comment saveComment(Comment comment);
    Optional<Comment> updateComment(Comment comment);
    void deleteComment(int cno);

    // 댓글 좋아요/취소 (추가)
    
	boolean toggleCommentLike(int cno, int memberId);
    
    // 조회수 증가 기능 추가
    void incrementBhit(int bno);
}