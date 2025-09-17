package com.java.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.dto.Board;
import com.java.dto.Board_Like;
import com.java.dto.Member;
import com.java.dto.sComment;
import com.java.dto.sComment_Like;
import com.java.repository.BoardRepository;
import com.java.repository.Board_LikeRepository;
import com.java.repository.MemberRepository;
import com.java.repository.sCommentRepository;
import com.java.repository.sComment_LikeRepository;

import jakarta.transaction.Transactional;

@Service
public class BoardServiceImpl implements BoardService {
    
    @Autowired
    private BoardRepository boardRepository;
    
    @Autowired
    private Board_LikeRepository boardLikeRepository;
    
    @Autowired
    private MemberRepository memberRepository;
    
    @Autowired
    private sCommentRepository scommentRepository;
    
    @Autowired
    private sComment_LikeRepository scommentLikeRepository;

    // 게시글 관련 기능
    
    @Override
    public List<Board> getBoardList() {
        return boardRepository.findAll();
    }
    
    @Override
    public Board getBoard(int bno) {
        return boardRepository.findById(bno).orElse(null);
    }
    
    @Override
    public Board saveBoard(Board board) {
        return boardRepository.save(board);
    }
    
    @Override
    public Optional<Board> updateBoard(Board board) {
        return Optional.of(boardRepository.save(board));
    }
    
    @Override
    public void deleteBoard(int bno) {
        boardRepository.deleteById(bno);
    }
    
    // 게시글 좋아요 관련 기능
    
    @Override
    @Transactional
    public boolean toggleLike(int bno, String loginId) {
        Board board = boardRepository.findById(bno).orElse(null);
        Member member = memberRepository.findByLoginId(loginId);
        
        if (board == null || member == null) {
            return false;
        }
        
        Optional<Board_Like> existingLike = boardLikeRepository.findByBoardAndMember(board, member);
        
        if (existingLike.isPresent()) {
            boardLikeRepository.delete(existingLike.get());
            return false; // 좋아요 취소
        } else {
            Board_Like newLike = Board_Like.builder()
                .board(board)
                .member(member)
                .build();
            boardLikeRepository.save(newLike);
            return true; // 좋아요 추가
        }
    }
    
    @Override
    public boolean isLikedByUser(int bno, String loginId) {
        Board board = boardRepository.findById(bno).orElse(null);
        Member member = memberRepository.findByLoginId(loginId);
        
        if (board == null || member == null) {
            return false;
        }
        
        return boardLikeRepository.findByBoardAndMember(board, member).isPresent();
    }
    
    @Override
    public long getLikeCount(int bno) {
        Board board = boardRepository.findById(bno).orElse(null);
        if (board == null) {
            return 0;
        }
        return boardLikeRepository.countByBoard(board);
    }
    
    // 댓글 관련 기능
    
    @Override
    public sComment saveComment(sComment scomment) {
        return scommentRepository.save(scomment);
    }
    
    @Override
    public Optional<sComment> updateComment(sComment scomment) {
        return Optional.of(scommentRepository.save(scomment));
    }
    
    @Override
    public void deleteComment(int scno) {
        scommentRepository.deleteById(scno);
    }
    
    @Override
    public List<sComment> getCommentsByBno(int bno) {
        return scommentRepository.findByBoard_BnoOrderByScno(bno);
    }
    
    @Override
    public long getCommentCount(int bno) {
        return scommentRepository.countByBoard_Bno(bno);
    }
    
    // 댓글 좋아요/취소
    @Override
    @Transactional
    public boolean toggleCommentLike(int scno, int memberId) {
        Optional<sComment> optionalScomment = scommentRepository.findById(scno);
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        
        if (optionalScomment.isPresent() && optionalMember.isPresent()) {
            sComment scomment = optionalScomment.get();
            Member member = optionalMember.get();
            
            Optional<sComment_Like> existingLike = scommentLikeRepository.findByScommentAndMember(scomment, member);
            
            if (existingLike.isPresent()) {
                scommentLikeRepository.delete(existingLike.get());
                return false;
            } else {
                sComment_Like newLike = sComment_Like.builder()
                    .scomment(scomment)
                    .member(member)
                    .build();
                scommentLikeRepository.save(newLike);
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean isCommentLikedByUser(int scno, int memberId) {
        Optional<sComment> optionalScomment = scommentRepository.findById(scno);
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        
        if (optionalScomment.isPresent() && optionalMember.isPresent()) {
            sComment scomment = optionalScomment.get();
            Member member = optionalMember.get();
            return scommentLikeRepository.findByScommentAndMember(scomment, member).isPresent();
        }
        return false;
    }
    
    @Override
    public long getCommentLikeCount(int scno) {
        Optional<sComment> optionalScomment = scommentRepository.findById(scno);
        if (optionalScomment.isPresent()) {
            return scommentLikeRepository.countByScomment(optionalScomment.get());
        }
        return 0;
    }
    
    // 조회수 증가 기능
    @Override
    @Transactional
    public void incrementBhit(int bno) {
        Optional<Board> optionalBoard = boardRepository.findById(bno);
        optionalBoard.ifPresent(board -> {
            board.setBhit(board.getBhit() + 1);
            boardRepository.save(board);
        });
    }
}