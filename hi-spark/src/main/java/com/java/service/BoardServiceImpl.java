package com.java.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.dto.Board;
import com.java.dto.Board_Like;
import com.java.dto.sComment;
import com.java.dto.sComment_Like;
import com.java.entity.Member;
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
    @Transactional // 트랜잭션 추가
    public void deleteBoard(int bno) {
        // 1. 게시글과 관련된 모든 댓글의 좋아요를 먼저 삭제
        scommentLikeRepository.deleteByBoardBno(bno);

        // 2. 게시글과 관련된 모든 댓글을 삭제
        scommentRepository.deleteByBoardBno(bno);

        // 3. 게시글과 관련된 모든 좋아요를 삭제
        boardLikeRepository.deleteByBoardBno(bno);

        // 4. 자식 레코드를 모두 삭제한 후, 게시글을 삭제
        boardRepository.deleteById(bno);
    }

    // ... (나머지 기존 메소드들)
    
    @Override
    public boolean toggleLike(int bno, String loginId) {
        Optional<Board> optionalBoard = boardRepository.findById(bno);
        Optional<Member> optionalMember = memberRepository.findByLoginId(loginId);
        
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
    public boolean isLikedByUser(int bno, String loginId) {
        Optional<Board> optionalBoard = boardRepository.findById(bno);
        Optional<Member> optionalMember = memberRepository.findByLoginId(loginId);
        
        if (optionalBoard.isPresent() && optionalMember.isPresent()) {
            return boardLikeRepository.findByBoardAndMember(optionalBoard.get(), optionalMember.get()).isPresent();
        }
        return false;
    }
    
    @Override
    public long getLikeCount(int bno) {
        Optional<Board> optionalBoard = boardRepository.findById(bno);
        if (optionalBoard.isPresent()) {
            return boardLikeRepository.countByBoard(optionalBoard.get());
        }
        return 0;
    }

    @Override
    public sComment saveComment(sComment scomment) {
        return scommentRepository.save(scomment);
    }
    
    @Override
    @Transactional
    public void updateComment(int scno, String sccontent) {
        sComment comment = scommentRepository.findById(scno).orElseThrow(() -> new RuntimeException("Comment not found"));
        comment.setSccontent(sccontent);
        scommentRepository.save(comment);
    }
    
    @Override
    @Transactional
    public void deleteComment(int scno) {
        // 1. 해당 댓글에 연결된 모든 좋아요를 먼저 삭제
        scommentLikeRepository.deleteByScommentScno(scno);
        // 2. 댓글 삭제
        scommentRepository.deleteById(scno);
    }
    
    @Override
    public List<sComment> getCommentsByBno(int bno) {
        return scommentRepository.findByBoardBno(bno);
    }

    @Override
    public long getCommentCount(int bno) {
        return scommentRepository.countByBoardBno(bno);
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