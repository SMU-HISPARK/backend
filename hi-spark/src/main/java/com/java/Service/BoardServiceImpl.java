package com.java.Service;

import com.java.dto.Board;
import com.java.dto.Board_Like;
import com.java.dto.Comment;
import com.java.dto.Comment_Like;
import com.java.dto.Member;
import com.java.repository.BoardRepository;
import com.java.repository.Board_LikeRepository;
import com.java.repository.CommentRepository;
import com.java.repository.Comment_LikeRepository; // 추가
import com.java.repository.MemberRepository; // 추가

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private Board_LikeRepository boardLikeRepository;
    
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private Comment_LikeRepository commentLikeRepository; // 추가

    @Autowired
    private MemberRepository memberRepository; // Member 엔티티를 찾기 위해 추가

    @Override
    public List<Board> getBoardList() {
        return boardRepository.findAll();
    }

    @Override
    public Board getBoard(int bno) {
        return boardRepository.findById(bno).orElse(null);
    }

    @Override
    @Transactional
    public Board saveBoard(Board board) {
        return boardRepository.save(board);
    }

    @Override
    @Transactional
    public Optional<Board> updateBoard(Board board) {
        if (!boardRepository.existsById(board.getBno())) {
            return Optional.empty();
        }
        return Optional.of(boardRepository.save(board));
    }

    @Override
    public void deleteBoard(int bno) {
        boardRepository.deleteById(bno);
    }

    @Override
    @Transactional
    public boolean toggleLike(int bno, String memberId) {
        Optional<Board> optionalBoard = boardRepository.findById(bno);
        Optional<Member> optionalMember = memberRepository.findById(Integer.parseInt(memberId));
        
        if (optionalBoard.isPresent() && optionalMember.isPresent()) {
            Board board = optionalBoard.get();
            Member member = optionalMember.get();
            
            Optional<Board_Like> existingLike = boardLikeRepository.findByBoardAndMember(board, member);
            
            if (existingLike.isPresent()) {
                boardLikeRepository.delete(existingLike.get());
                return false;
            } else {
                Board_Like newLike = Board_Like.builder()
                                             .board(board)
                                             .member(member)
                                             .build();
                boardLikeRepository.save(newLike);
                return true;
            }
        }
        return false;
    }

    @Override
    @Transactional
    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public Optional<Comment> updateComment(Comment comment) {
        if (!commentRepository.existsById(comment.getCno())) {
            return Optional.empty();
        }
        return Optional.of(commentRepository.save(comment));
    }

    @Override
    public void deleteComment(int cno) {
        commentRepository.deleteById(cno);
    }
    
    @Override
    @Transactional
    public boolean toggleCommentLike(int cno, int memberId) {
        Optional<Comment> optionalComment = commentRepository.findById(cno);
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        
        if (optionalComment.isPresent() && optionalMember.isPresent()) {
            Comment comment = optionalComment.get();
            Member member = optionalMember.get();
            
            // 이미 좋아요 기록이 있는지 확인
            Optional<Comment_Like> existingLike = commentLikeRepository.findByCommentAndMember(comment, member);
            
            if (existingLike.isPresent()) {
                // 좋아요 기록이 있으면 삭제
                commentLikeRepository.delete(existingLike.get());
                return false; // 좋아요 취소
            } else {
                // 없으면 새로 추가
                Comment_Like newLike = Comment_Like.builder()
                                                 .comment(comment)
                                                 .member(member)
                                                 .build();
                commentLikeRepository.save(newLike);
                return true; // 좋아요 추가
            }
        }
        return false;
    }

    @Override
    @Transactional
    public void incrementBhit(int bno) {
        boardRepository.findById(bno).ifPresent(board -> {
            board.setBhit(board.getBhit() + 1);
            boardRepository.save(board);
        });
    }
}