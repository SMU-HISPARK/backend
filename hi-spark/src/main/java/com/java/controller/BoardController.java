package com.java.controller;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.java.dto.Board;
import com.java.dto.Poll;
import com.java.dto.Poll_Item;
import com.java.dto.sComment;
import com.java.entity.Member;
import com.java.repository.BoardRepository;
import com.java.repository.MemberRepository;
import com.java.repository.PollRepository;
import com.java.repository.Poll_ItemRepository;
import com.java.repository.Vote_LogRepository;
import com.java.repository.sCommentRepository;
import com.java.service.BoardService;
import com.java.service.PollService;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Controller
public class BoardController {
	
	@Autowired
	 private PollRepository pollRepository;
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private sCommentRepository scommentRepository;
	
	@Autowired
	private PollService pollService;
	
	@Autowired
    private Poll_ItemRepository pollItemRepository;
    
    @Autowired
    private Vote_LogRepository voteLogRepository;
	
	@Autowired
	private BoardRepository boardRepository;

	@Autowired
    private BoardService boardService;
	
	// 권한 체크 메서드 추가
	private boolean checkAdminAuth(HttpSession session, RedirectAttributes redirect) {
		Member loggedInMember = (Member) session.getAttribute("loggedInMember");
		if (loggedInMember == null || !"관리자".equals(loggedInMember.getName())) {
			redirect.addFlashAttribute("authError", "권한이 없습니다.");
			return false;
		}
		return true;
	}
	
	
	@GetMapping("/board/main")
	public String board_main() {
		return "board/board_main";
	}
	
	
	
	
	
	@GetMapping("/board/forum_list")
	public String forum_list(
	    @RequestParam(name = "page", defaultValue = "1") int page,
	    @RequestParam(name = "searchType", required = false) String searchType,
	    @RequestParam(name = "keyword", required = false) String keyword,
	    Model model) {
	    
	    try {
	        Pageable pageable = PageRequest.of(page - 1, 10);
	        Page<Board> forumPage;

	        if (keyword != null && !keyword.trim().isEmpty()) {
	            if ("title".equals(searchType)) {
	                forumPage = boardRepository.findByBTypeAndBtitleContaining(1, keyword, pageable);
	            } else if ("content".equals(searchType)) {
	                forumPage = boardRepository.findByBTypeAndBcontentContaining(1, keyword, pageable);
	            } else if ("writer".equals(searchType)) {
	                forumPage = boardRepository.findByBTypeAndMemberNameContaining(1, keyword, pageable);
	            } else {
	                forumPage = boardRepository.findByBTypeWithPaging(1, pageable);
	            }
	        } else {
	            forumPage = boardRepository.findByBTypeWithPaging(1, pageable);
	        }
	        
	        List<Map<String, Object>> boardListWithCount = new ArrayList<>();
	        
	        for (Board board : forumPage.getContent()) {
	            Map<String, Object> item = new HashMap<>();
	            item.put("board", board);
	            
	            long commentCount = 0;
	            try {
	                commentCount = boardService.getCommentCount(board.getBno());
	            } catch (Exception e) {
	                System.out.println("댓글 수 조회 오류: " + e.getMessage());
	            }
	            item.put("commentCount", commentCount);
	            
	            long likeCount = 0;
	            try {
	                likeCount = boardService.getLikeCount(board.getBno());
	            } catch (Exception e) {
	                System.out.println("좋아요 수 조회 오류: " + e.getMessage());
	            }
	            item.put("likeCount", likeCount);
	            
	            boardListWithCount.add(item);
	        }

	        int totalPages = forumPage.getTotalPages();
	        int currentPage = page;
	        
	        if (totalPages == 0) {
	            model.addAttribute("boardListWithCount", new ArrayList<>());
	            model.addAttribute("currentPage", 1);
	            model.addAttribute("totalPages", 0);
	            model.addAttribute("startPage", 1);
	            model.addAttribute("endPage", 1);
	            model.addAttribute("hasPrevious", false);
	            model.addAttribute("hasNext", false);
	            model.addAttribute("totalElements", 0L);
	            model.addAttribute("bType", 1);
	        } else {
	            int startPage = Math.max(1, currentPage - 2);
	            int endPage = Math.min(totalPages, currentPage + 2);
	            
	            if (endPage - startPage < 4) {
	                if (startPage == 1) {
	                    endPage = Math.min(totalPages, startPage + 4);
	                } else if (endPage == totalPages) {
	                    startPage = Math.max(1, endPage - 4);
	                }
	            }
	            
	            model.addAttribute("boardListWithCount", boardListWithCount);
	            model.addAttribute("currentPage", currentPage);
	            model.addAttribute("totalPages", totalPages);
	            model.addAttribute("startPage", startPage);
	            model.addAttribute("endPage", endPage);
	            model.addAttribute("hasPrevious", forumPage.hasPrevious());
	            model.addAttribute("hasNext", forumPage.hasNext());
	            model.addAttribute("totalElements", forumPage.getTotalElements());
	            model.addAttribute("bType", 1);
	        }
	        
	        System.out.println("총 게시글 수: " + forumPage.getTotalElements());
	        System.out.println("boardListWithCount 크기: " + boardListWithCount.size());
	        
	    } catch (Exception e) {
	        System.out.println("자유게시판 페이지네이션 오류: " + e.getMessage());
	        e.printStackTrace();
	        
	        List<Board> forumList = boardRepository.findByBType(1);
	        List<Map<String, Object>> boardListWithCount = new ArrayList<>();
	        
	        for (Board board : forumList) {
	            Map<String, Object> item = new HashMap<>();
	            item.put("board", board);
	            item.put("commentCount", 0L);
	            item.put("likeCount", 0L);
	            boardListWithCount.add(item);
	        }
	        
	        model.addAttribute("boardListWithCount", boardListWithCount);
	        model.addAttribute("currentPage", 1);
	        model.addAttribute("totalPages", 1);
	        model.addAttribute("startPage", 1);
	        model.addAttribute("endPage", 1);
	        model.addAttribute("hasPrevious", false);
	        model.addAttribute("hasNext", false);
	        model.addAttribute("totalElements", (long) forumList.size());
	        model.addAttribute("bType", 1);
	    }
	    
	    return "board/forum_list";
	}
	
	@GetMapping("/board/forum_view")
	public String forum_view(@RequestParam("bno") int bno, Model model, HttpSession session) {
	    Board board = boardService.getBoard(bno);

	    if (board == null) {
	        return "redirect:/board/forum_list";
	    }

	    // 조회수 증가
	    boardService.incrementBhit(bno);

	    // 게시글 좋아요 상태 및 개수
	    boolean isLiked = false;
	    long likeCount = boardService.getLikeCount(bno);

	    // 로그인 사용자 ID 가져오기
	    String loginId = null;
	    Member loggedInMember = (Member) session.getAttribute("loggedInMember");
	    
	    if (loggedInMember != null) {
	        loginId = loggedInMember.getLoginId();
	        isLiked = boardService.isLikedByUser(bno, loginId);
	    }
	    
	    // 댓글 목록 가져오기 (댓글 좋아요 관련 로직은 제거)
	    List<sComment> comments = boardService.getCommentsByBno(bno);
	    long commentCount = boardService.getCommentCount(bno);

	    // 이전글/다음글
	    int bType = 1; // Forum 게시판 타입
	    Board prevBoard = boardRepository.findTopByBnoLessThanAndBTypeOrderByBnoDesc(bno, bType);
	    Board nextBoard = boardRepository.findTopByBnoGreaterThanAndBTypeOrderByBnoAsc(bno, bType);

	    // 모델에 값 전달
	    model.addAttribute("board", board);
	    model.addAttribute("isLiked", isLiked);
	    model.addAttribute("likeCount", likeCount);
	    model.addAttribute("comments", comments);
	    model.addAttribute("commentCount", commentCount);
	    model.addAttribute("prevBoard", prevBoard);
	    model.addAttribute("nextBoard", nextBoard);
	    model.addAttribute("loginId", loginId);

	    return "board/forum_view";
	}
	
	
	// 댓글 삭제 (AJAX 요청 처리)
    @PostMapping("/board/deleteComment")
    @ResponseBody
    public Map<String, Object> deleteComment(@RequestParam("scno") int scno, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        Member loggedInMember = (Member) session.getAttribute("loggedInMember");

        if (loggedInMember == null) {
            response.put("success", false);
            response.put("message", "로그인이 필요합니다.");
            return response;
        }

        try {
            sComment comment = scommentRepository.findById(scno).orElse(null);
            if (comment == null) {
                response.put("success", false);
                response.put("message", "해당 댓글을 찾을 수 없습니다.");
                return response;
            }
            
            // 작성자 본인 또는 관리자만 삭제 가능
            if (!comment.getMember().getLoginId().equals(loggedInMember.getLoginId()) && !"관리자".equals(loggedInMember.getName())) {
                response.put("success", false);
                response.put("message", "삭제 권한이 없습니다.");
                return response;
            }

            boardService.deleteComment(scno);
            response.put("success", true);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "댓글 삭제 중 오류가 발생했습니다.");
            return response;
        }
    }
    
    @PostMapping("/board/updateComment")
    @ResponseBody
    public Map<String, Object> updateComment(@RequestParam("scno") int scno, @RequestParam("sccontent") String sccontent, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        Member loggedInMember = (Member) session.getAttribute("loggedInMember");

        if (loggedInMember == null) {
            response.put("success", false);
            response.put("message", "로그인이 필요합니다.");
            return response;
        }

        try {
            // 댓글 존재 여부 및 권한 확인
            sComment comment = scommentRepository.findById(scno).orElse(null);
            if (comment == null) {
                response.put("success", false);
                response.put("message", "해당 댓글을 찾을 수 없습니다.");
                return response;
            }
            
            if (!comment.getMember().getLoginId().equals(loggedInMember.getLoginId()) && !"관리자".equals(loggedInMember.getName())) {
                response.put("success", false);
                response.put("message", "수정 권한이 없습니다.");
                return response;
            }

            // 핵심: BoardService를 호출하여 댓글 업데이트
            boardService.updateComment(scno, sccontent);
            response.put("success", true);
            response.put("message", "댓글이 성공적으로 수정되었습니다.");
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "댓글 수정 중 오류가 발생했습니다.");
            return response;
        }
    }
	
	
	
	
	
	
	
	
	
	@PostMapping("/board/forum_delete")
	public String forum_delete(@RequestParam("bno") int bno, HttpSession session, RedirectAttributes redirect) {
	    // 1. 세션에서 로그인된 사용자 정보 가져오기
	    Member loggedInMember = (Member) session.getAttribute("loggedInMember");
	    if (loggedInMember == null) {
	        redirect.addFlashAttribute("error", "로그인이 필요합니다.");
	        return "redirect:/member/login";
	    }

	    // 2. 삭제할 게시글 정보 가져오기
	    Board board = boardService.getBoard(bno);
	    if (board == null) {
	        redirect.addFlashAttribute("error", "해당 게시글을 찾을 수 없습니다.");
	        return "redirect:/board/forum_list";
	    }

	   
	 // 3. 게시글 작성자 또는 '관리자' 이름의 사용자만 삭제 가능하도록 수정
        if (board.getMember() == null || (!board.getMember().getMemberId().equals(loggedInMember.getMemberId()) && !"관리자".equals(loggedInMember.getName()))) {
            redirect.addFlashAttribute("error", "삭제 권한이 없습니다.");
            return "redirect:/board/forum_view?bno=" + bno;
        }

	    // 4. 권한이 확인되면 게시글 삭제
	    boardService.deleteBoard(bno);
	    redirect.addFlashAttribute("success", "게시글이 삭제되었습니다.");
	    return "redirect:/board/forum_list";
	}
	
	 @PostMapping("/board/addComment")
	    public String addComment(@RequestParam("bno") int bno,
	                             @RequestParam("sccontent") String sccontent,
	                             HttpSession session,
	                             RedirectAttributes redirect) {
	        
	        Member member = (Member) session.getAttribute("loggedInMember");
	        if (member == null) {
	            redirect.addFlashAttribute("error", "로그인이 필요합니다.");
	            return "redirect:/member/login";
	        }

	        try {
	            sComment comment = sComment.builder()
	                                        .sccontent(sccontent)
	                                        .board(boardRepository.findById(bno).orElse(null))
	                                        .member(member)
	                                        .build();
	            boardService.saveComment(comment);
	            redirect.addFlashAttribute("message", "댓글이 성공적으로 등록되었습니다.");
	        } catch (Exception e) {
	            redirect.addFlashAttribute("error", "댓글 등록 중 오류가 발생했습니다.");
	            e.printStackTrace();
	        }

	        return "redirect:/board/forum_view?bno=" + bno;
	    }
	 
	 
	// 게시글 수정 페이지로 이동
	    @GetMapping("/board/forum_edit")
	    public String forum_edit(@RequestParam("bno") int bno, Model model, HttpSession session, RedirectAttributes redirect) {
	        Member loggedInMember = (Member) session.getAttribute("loggedInMember");
	        if (loggedInMember == null) {
	            redirect.addFlashAttribute("error", "로그인이 필요합니다.");
	            return "redirect:/member/login";
	        }
	        Board board = boardService.getBoard(bno);
	        if (board == null) {
	            redirect.addFlashAttribute("error", "해당 게시글을 찾을 수 없습니다.");
	            return "redirect:/board/forum_list";
	        }

	        // 게시글 작성자 또는 관리자만 수정 페이지에 접근 가능
	        if (board.getMember() == null || (!board.getMember().getMemberId().equals(loggedInMember.getMemberId()) && !"관리자".equals(loggedInMember.getName()))) {
	            redirect.addFlashAttribute("error", "수정 권한이 없습니다.");
	            return "redirect:/board/forum_view?bno=" + bno;
	        }
	        
	        model.addAttribute("board", board);
	        return "board/forum_edit";
	    }
	    
	    // 게시글 수정 처리
	    @PostMapping("/board/forum_edit_save")
	    public String forum_edit_save(Board board, HttpSession session, RedirectAttributes redirect) {
	        Member loggedInMember = (Member) session.getAttribute("loggedInMember");
	        if (loggedInMember == null) {
	            redirect.addFlashAttribute("error", "로그인이 필요합니다.");
	            return "redirect:/member/login";
	        }
	        Board existingBoard = boardService.getBoard(board.getBno());
	        if (existingBoard == null) {
	            redirect.addFlashAttribute("error", "해당 게시글을 찾을 수 없습니다.");
	            return "redirect:/board/forum_list";
	        }

	        // 게시글 작성자 또는 관리자만 수정 가능
	        if (existingBoard.getMember() == null || (!existingBoard.getMember().getMemberId().equals(loggedInMember.getMemberId()) && !"관리자".equals(loggedInMember.getName()))) {
	            redirect.addFlashAttribute("error", "수정 권한이 없습니다.");
	            return "redirect:/board/forum_view?bno=" + board.getBno();
	        }
	        
	        existingBoard.setBtitle(board.getBtitle());
	        existingBoard.setBcontent(board.getBcontent());
	        
	        boardService.updateBoard(existingBoard);
	        redirect.addFlashAttribute("success", "게시글이 수정되었습니다.");
	        return "redirect:/board/forum_view?bno=" + board.getBno();
	    }
	
	@PostMapping("/api/board/toggleLike")
    @ResponseBody
    public Map<String, Object> toggleLike(@RequestBody Map<String, Object> payload, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        Member loggedInMember = (Member) session.getAttribute("loggedInMember");

        if (loggedInMember == null) {
            response.put("success", false);
            response.put("message", "로그인이 필요합니다.");
            return response;
        }

        try {
            int bno = Integer.parseInt(payload.get("bno").toString());
            boolean currentLikeStatus = boardService.toggleLike(bno, loggedInMember.getLoginId());
            long newLikeCount = boardService.getLikeCount(bno);

            response.put("success", true);
            response.put("isLiked", currentLikeStatus);
            response.put("likeCount", newLikeCount);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "오류가 발생했습니다.");
        }
        return response;
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@GetMapping("/board/forum_write")
	public String forum_write() {
        return "board/forum_write";
    }

	@PostMapping("/board/forum_write_proc")
    public String forumWriteProc(
                               @RequestParam("btitle") String btitle,
                               @RequestParam("bcontent") String bcontent,
                               @RequestParam(value = "uploadFile", required = false) MultipartFile file, // 이 부분을 수정했습니다.
                               HttpSession session,
                               RedirectAttributes redirect) {
        
        Member loggedInMember = (Member) session.getAttribute("loggedInMember");
        
        if (loggedInMember != null) {
            // Board 객체를 직접 생성
            Board board = new Board();
            board.setBtitle(btitle);
            board.setBcontent(bcontent);
            board.setMember(loggedInMember);
            board.setB_type(1); //자유게시판 타입 1로 설정

            if (file != null && !file.isEmpty()) {
                try {
                    String originFileName = file.getOriginalFilename();
                    long time = System.currentTimeMillis();
                    String uploadFileName = String.format("%d_%s", time, originFileName);
                    
                    String fileUrl = "C:/uploads/";
                    File f = new File(fileUrl + uploadFileName);
                    
                    file.transferTo(f);
                    
                    board.setBfile(uploadFileName);
                } catch (Exception e) {
                    e.printStackTrace();
                    return "error"; 
                }
            } else {
                board.setBfile(null);
            }
            
            boardService.saveBoard(board);
            
            redirect.addFlashAttribute("flag", "1");
            
            return "redirect:/board/forum_list";

        } else {
            return "redirect:/member/login";
        }
    }
	
	
	
	@GetMapping("/board/notice_list")
	public String notice_list(
        @RequestParam(name = "page", defaultValue = "1") int page,
        @RequestParam(name = "searchType", required = false) String searchType,
        @RequestParam(name = "keyword", required = false) String keyword,
        Model model) {
		try {
			// 페이지 번호는 0부터 시작하므로 -1
			Pageable pageable = PageRequest.of(page - 1, 10);
			Page<Board> noticePage;

			if (keyword != null && !keyword.trim().isEmpty()) {
                if ("btitle".equals(searchType)) {
                    noticePage = boardRepository.findByBTypeAndBtitleContaining(0, keyword, pageable);
                } else if ("bcontent".equals(searchType)) {
                    noticePage = boardRepository.findByBTypeAndBcontentContaining(0, keyword, pageable);
                } else if ("member_name".equals(searchType)) {
                    noticePage = boardRepository.findByBTypeAndMemberNameContaining(0, keyword, pageable);
                } else {
                    noticePage = boardRepository.findByBTypeWithPaging(0, pageable);
                }
            } else {
                noticePage = boardRepository.findByBTypeWithPaging(0, pageable);
            }
			
			// 페이지 정보 계산
			int totalPages = noticePage.getTotalPages(); // 이 페이지 수
			int currentPage = page; // 현재 페이지
			
			// totalPages가 0인 경우 처리
			if (totalPages == 0) {
				model.addAttribute("noticeList", noticePage.getContent()); // 게시글 리스트
				model.addAttribute("currentPage", 1); // 현재 페이지
				model.addAttribute("totalPages", 0); // 전체 페이지
				model.addAttribute("startPage", 1); // 시작페이지
				model.addAttribute("endPage", 1); // 마지막 페이지
				model.addAttribute("hasPrevious", false); // 이전페이지 유무
				model.addAttribute("hasNext", false); //다음 페이지 유무
				model.addAttribute("totalElements", 0L); // 이 
			} else {
				int startPage = Math.max(1, currentPage - 2); // ex 현재 페이지가 4일 경우 1과 2 중에 큰 수를 지정(시작 페이지) 
				int endPage = Math.min(totalPages, currentPage + 2); // ex 
				
				// 페이지 번호가 5개 미만일 때 조정
				if (endPage - startPage < 4) {
					if (startPage == 1) {
						endPage = Math.min(totalPages, startPage + 4);
					} else if (endPage == totalPages) {
						startPage = Math.max(1, endPage - 4);
					}
				}
				
				model.addAttribute("noticeList", noticePage.getContent());
				model.addAttribute("currentPage", currentPage);
				model.addAttribute("totalPages", totalPages);
				model.addAttribute("startPage", startPage);
				model.addAttribute("endPage", endPage);
				model.addAttribute("hasPrevious", noticePage.hasPrevious());
				model.addAttribute("hasNext", noticePage.hasNext());
				model.addAttribute("totalElements", noticePage.getTotalElements());
			}
			
		} catch (Exception e) {
			// 오류 발생 시 기존 방식으로 폴백
			System.out.println("페이지네이션 오류: " + e.getMessage());
			List<Board> noticeList = boardRepository.findByBType(0);
			model.addAttribute("noticeList", noticeList);
			model.addAttribute("currentPage", 1);
			model.addAttribute("totalPages", 1);
			model.addAttribute("startPage", 1);
			model.addAttribute("endPage", 1);
			model.addAttribute("hasPrevious", false);
			model.addAttribute("hasNext", false);
			model.addAttribute("totalElements", (long) noticeList.size());
		}
		
		return "board/notice_list";
	}
	
	@GetMapping("/board/notice_view")
    public String notice_view(@RequestParam("bno") int bno, HttpSession session, Model model, RedirectAttributes redirect) {
        
        // 세션에 조회한 게시글 ID 목록을 저장할 리스트를 가져오거나 생성
        List<Integer> viewedPosts = (List<Integer>) session.getAttribute("viewedPosts");
        if (viewedPosts == null) {
            viewedPosts = new ArrayList<>();
            session.setAttribute("viewedPosts", viewedPosts);
        }

        // 현재 게시글이 조회된 적이 없다면 조회수 증가
        if (!viewedPosts.contains(bno)) {
            boardService.incrementBhit(bno);
            viewedPosts.add(bno);
        }
        
        // 게시글 정보 가져오기
        Board notice = boardService.getBoard(bno);
        model.addAttribute("notice", notice);
        
        // 이전글/다음글 정보 추가
        // 이전글: 현재 글보다 작은 bno 중 가장 큰 것
        Board previousNotice = boardRepository.findTopByBnoLessThanAndBTypeOrderByBnoDesc(bno, 0);
        model.addAttribute("previousNotice", previousNotice);
        
        // 다음글: 현재 글보다 큰 bno 중 가장 작은 것  
        Board nextNotice = boardRepository.findTopByBnoGreaterThanAndBTypeOrderByBnoAsc(bno, 0);
        model.addAttribute("nextNotice", nextNotice);

        return "board/notice_view";
    }
	
	@GetMapping("/board/notice_write")
	public String notice_write(HttpSession session, RedirectAttributes redirect) {
		// 관리자 권한 체크 추가
		if (!checkAdminAuth(session, redirect)) {
			return "redirect:/board/notice_list";
		}
		return "board/notice_write";
	}
	
	@PostMapping("/board/notice_write_proc")
    public String noticeWriteProc(
                               @RequestParam("btitle") String btitle,
                               @RequestParam("bcontent") String bcontent,
                               @RequestParam("uploadFile") MultipartFile file,
                               HttpSession session,
                               RedirectAttributes redirect) {
        
        Member loggedInMember = (Member) session.getAttribute("loggedInMember");
        
        if (loggedInMember != null) {
            // Board 객체를 직접 생성
            Board board = new Board();
            board.setBtitle(btitle);
            board.setBcontent(bcontent);
            board.setMember(loggedInMember);
            board.setB_type(0);

            if (!file.isEmpty()) {
                try {
                    String originFileName = file.getOriginalFilename();
                    long time = System.currentTimeMillis();
                    String uploadFileName = String.format("%d_%s", time, originFileName);
                    
                    String fileUrl = "C:/uploads/";
                    File f = new File(fileUrl + uploadFileName);
                    
                    file.transferTo(f);
                    
                    board.setBfile(uploadFileName);
                } catch (Exception e) {
                    e.printStackTrace();
                    return "error"; 
                }
            } else {
                board.setBfile(null);
            }
            
            boardService.saveBoard(board);
            
            redirect.addFlashAttribute("flag", "1");
            
            return "redirect:/board/notice_list";

        } else {
            return "redirect:/member/login";
        }
    }
	
	@PostMapping("/board/notice_delete")
    public String noticeDelete(@RequestParam("bno") int bno, HttpSession session, RedirectAttributes redirect) {
        // 관리자 권한 체크 추가
        if (!checkAdminAuth(session, redirect)) {
            return "redirect:/board/notice_list";
        }
        
        boardService.deleteBoard(bno);
        redirect.addFlashAttribute("message", "게시글이 성공적으로 삭제되었습니다.");
        return "redirect:/board/notice_list";
    }
	
	@GetMapping("/board/notice_edit")
    public String noticeEdit(@RequestParam("bno") int bno, HttpSession session, Model model, RedirectAttributes redirect) {
        // 관리자 권한 체크 추가
        if (!checkAdminAuth(session, redirect)) {
            return "redirect:/board/notice_list";
        }
        
        Board notice = boardService.getBoard(bno);
        model.addAttribute("notice", notice);
        return "board/notice_edit";
    }

    // 수정 내용 저장
    @PostMapping("/board/notice_edit_proc")
    public String noticeEditProc(
        @RequestParam("bno") int bno, 
        @RequestParam("btitle") String btitle, 
        @RequestParam("bcontent") String bcontent,
        @RequestParam("uploadFile") MultipartFile file,
        @RequestParam("existingBfile") String existingBfile,
        HttpSession session,
        RedirectAttributes redirect) {

        if (!checkAdminAuth(session, redirect)) {
            return "redirect:/board/notice_list";
        }

        Optional<Board> optionalBoard = boardRepository.findById(bno);
        
        if (optionalBoard.isPresent()) {
            Board existingBoard = optionalBoard.get();
            
            existingBoard.setBtitle(btitle);
            existingBoard.setBcontent(bcontent);
            
            // 기존 파일 삭제 요청 처리 (새 파일이 없거나 있어도 동일하게 작동)
            if ("delete".equals(existingBfile) && existingBoard.getBfile() != null) {
                String fileUrl = "C:/uploads/";
                File oldFile = new File(fileUrl + existingBoard.getBfile());
                if (oldFile.exists()) {
                    oldFile.delete(); // 실제 파일 삭제
                }
                existingBoard.setBfile(null); // DB에서 파일 정보 삭제
            }

            // 새 파일이 업로드된 경우 처리
            if (!file.isEmpty()) {
                // 기존 파일이 있을 경우 삭제 (새 파일이 올라오면 기존 파일은 무조건 삭제)
                if (existingBoard.getBfile() != null) {
                    String fileUrl = "C:/uploads/";
                    File oldFile = new File(fileUrl + existingBoard.getBfile());
                    if (oldFile.exists()) {
                        oldFile.delete();
                    }
                }

                try {
                    String originFileName = file.getOriginalFilename();
                    long time = System.currentTimeMillis();
                    String uploadFileName = String.format("%d_%s", time, originFileName);
                    
                    String fileUrl = "C:/uploads/"; 
                    File f = new File(fileUrl + uploadFileName);
                    
                    file.transferTo(f);
                    existingBoard.setBfile(uploadFileName);
                } catch (Exception e) {
                    e.printStackTrace();
                    return "error"; 
                }
            }
            
            boardService.saveBoard(existingBoard);
            
            redirect.addFlashAttribute("message", "게시글이 성공적으로 수정되었습니다.");
            return "redirect:/board/notice_view?bno=" + existingBoard.getBno();
        } else {
            return "redirect:/board/notice_list";
        }
    }
	
    @GetMapping("/board/vote_list")
    public String vote_list(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "searchType", required = false) String searchType,
            @RequestParam(name = "keyword", required = false) String keyword,
            Model model) {
        try {
            Pageable pageable = PageRequest.of(page - 1, 10);
            Page<Poll> pollPage;
            
            if (keyword != null && !keyword.trim().isEmpty()) {
                if ("poll_title".equals(searchType)) {
                    pollPage = pollService.searchByTitle(keyword, pageable);
                } else if ("poll_content".equals(searchType)) {
                    pollPage = pollService.searchByContent(keyword, pageable);
                } else {
                    pollPage = pollService.getPollList(pageable);
                }
            } else {
                pollPage = pollService.getPollList(pageable);
            }
            
            // 페이지네이션 로직
            int totalPages = pollPage.getTotalPages();
            int currentPage = page;
            
            if (totalPages == 0) {
            	model.addAttribute("polls", new ArrayList<>());
            	model.addAttribute("currentPage", 1);
                model.addAttribute("totalPages", 0);
                model.addAttribute("startPage", 1);
                model.addAttribute("endPage", 1);
                model.addAttribute("hasPrevious", false);
                model.addAttribute("hasNext", false);
                model.addAttribute("totalElements", 0L);
            } else {
            	int startPage = Math.max(1, currentPage - 2);
            	int endPage = Math.min(totalPages, currentPage + 2);
            	
            	if (endPage - startPage < 4) {
            		if (startPage == 1) {
            			endPage = Math.min(totalPages, startPage + 4);
            		} else if (endPage == totalPages) {
            			startPage = Math.max(1, endPage - 4);
            		}
            	}
            	
            	List<Map<String, Object>> pollListWithCounts = new ArrayList<>();
            	for (Poll poll : pollPage.getContent()) {
            		long voteCount = pollService.getVoteCountByPollNo(poll.getPoll_no());
            		Map<String, Object> item = new HashMap<>();
            		item.put("poll", poll);
            		item.put("voteCount", voteCount);
            		pollListWithCounts.add(item);
            	}
            	
                model.addAttribute("polls", pollListWithCounts);
                model.addAttribute("pollPage", pollPage);
                model.addAttribute("currentPage", currentPage);
                model.addAttribute("totalPages", totalPages);
                model.addAttribute("startPage", startPage);
                model.addAttribute("endPage", endPage);
                model.addAttribute("hasPrevious", pollPage.hasPrevious());
                model.addAttribute("hasNext", pollPage.hasNext());
                model.addAttribute("totalElements", pollPage.getTotalElements());
            }
        } catch (Exception e) {
            System.out.println("페이지네이션 오류: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("polls", new ArrayList<>());
            model.addAttribute("pollPage", Page.empty());
            model.addAttribute("currentPage", 1);
            model.addAttribute("totalPages", 1);
            model.addAttribute("startPage", 1);
            model.addAttribute("endPage", 1);
            model.addAttribute("hasPrevious", false);
            model.addAttribute("hasNext", false);
            model.addAttribute("totalElements", 0L);
        }
        
        return "board/vote_list";
    }
	
    @GetMapping("/board/vote_view")
    public String vote_view(@RequestParam("pollNo") int pollNo, Model model, HttpSession session) {
        // 로그인 상태 확인 - 두 가지 방법 모두 시도
        String loginId = (String) session.getAttribute("loginId");
        Member loggedInMember = (Member) session.getAttribute("loggedInMember");
        
        // loggedInMember가 있으면 loginId 추출
        if (loginId == null && loggedInMember != null) {
            loginId = loggedInMember.getLoginId();
        }
        
        Optional<Poll> optionalPoll = pollRepository.findById(pollNo);
        
        if (!optionalPoll.isPresent()) {
            model.addAttribute("error", "투표를 찾을 수 없습니다.");
            return "redirect:/board/vote_list";
        }
        
        Poll poll = optionalPoll.get();
        model.addAttribute("poll", poll);
        
        List<Poll_Item> pollItems = pollItemRepository.findByPollNo(pollNo);
        model.addAttribute("pollItems", pollItems);
        
        // 투표 수 계산
        long totalVotes = voteLogRepository.countByPollNo(pollNo);
        Map<Integer, Long> voteCounts = new HashMap<>();
        for (Poll_Item item : pollItems) {
            long count = voteLogRepository.countByPollItemNo(item.getItem_no());
            voteCounts.put(item.getItem_no(), count);
        }
        model.addAttribute("totalVotes", totalVotes);
        model.addAttribute("voteCounts", voteCounts);
        
        // 투표 참여 여부 확인
        boolean hasVoted = false;
        if (loginId != null) {
            hasVoted = voteLogRepository.findByPoll_PollNoAndMember_LoginId(pollNo, loginId).isPresent();
        }
        model.addAttribute("hasVoted", hasVoted);
        model.addAttribute("loginId", loginId);
        
        // 투표 종료 여부
        boolean isPollEnded = poll.getPoll_end_date().getTime() < System.currentTimeMillis();
        model.addAttribute("isPollEnded", isPollEnded);
        
        model.addAttribute("hasVoted", hasVoted);
        // 이전글/다음글 정보 추가
        Optional<Poll> previousPollOptional = pollRepository.findPreviousPoll(pollNo);
        Optional<Poll> nextPollOptional = pollRepository.findNextPoll(pollNo);
        model.addAttribute("previousPoll", previousPollOptional.orElse(null));
        model.addAttribute("nextPoll", nextPollOptional.orElse(null));

        return "board/vote_view";
    }

    // 투표 처리 POST 메서드도 동일하게 수정
    @PostMapping("/board/vote")
    public String processVote(@RequestParam("pollNo") int pollNo, 
                             @RequestParam("itemNo") int itemNo, 
                             HttpSession session, 
                             RedirectAttributes redirect) {
        
        // 로그인 상태 확인 - 두 가지 방법 모두 시도
        String loginId = (String) session.getAttribute("loginId");
        Member loggedInMember = (Member) session.getAttribute("loggedInMember");
        
        // loggedInMember가 있으면 loginId 추출
        if (loginId == null && loggedInMember != null) {
            loginId = loggedInMember.getLoginId();
        }
        
        if (loginId == null) {
            redirect.addFlashAttribute("error", "로그인이 필요합니다.");
            return "redirect:/member/login";
        }
        
        try {
            // 투표가 존재하는지 확인
            Optional<Poll> optionalPoll = pollRepository.findById(pollNo);
            if (!optionalPoll.isPresent()) {
                redirect.addFlashAttribute("error", "존재하지 않는 투표입니다.");
                return "redirect:/board/vote_list";
            }
            
            Poll poll = optionalPoll.get();
            
            // 투표 종료 여부 확인
            if (poll.getPoll_end_date().getTime() < System.currentTimeMillis()) {
                redirect.addFlashAttribute("error", "종료된 투표입니다.");
                return "redirect:/board/vote_view?pollNo=" + pollNo;
            }
            
            // 이미 투표했는지 확인
            if (pollService.hasVoted(pollNo, loginId)) {
                redirect.addFlashAttribute("error", "이미 투표에 참여하셨습니다.");
                return "redirect:/board/vote_view?pollNo=" + pollNo;
            }
            
            // 투표 처리
            pollService.vote(pollNo, itemNo, loginId);
            
            redirect.addFlashAttribute("success");
            
        } catch (Exception e) {
            System.out.println("투표 처리 오류: " + e.getMessage());
            e.printStackTrace();
            redirect.addFlashAttribute("error", "투표 처리 중 오류가 발생했습니다.");
        }
        
        return "redirect:/board/vote_view?pollNo=" + pollNo;
    }
	
	@GetMapping("/board/vote_write")
	public String vote_write(HttpSession session, RedirectAttributes redirect) {
	    // 관리자 권한 체크 추가
	    if (!checkAdminAuth(session, redirect)) {
	        return "redirect:/board/vote_list";
	    }
	    return "board/vote_write";
	}
	
	@PostMapping("/board/vote_write_proc")
	public String voteWriteProc(
	        @RequestParam("poll_title") String poll_title,
	        @RequestParam("poll_content") String poll_content,
	        @RequestParam("poll_end_date") String poll_end_date,
	        @RequestParam("poll_items") List<String> poll_items,
	        @RequestParam(value = "post_file", required = false) MultipartFile file,
	        HttpSession session,
	        RedirectAttributes redirect) throws Exception {
	    
	    // 관리자 권한 체크 추가
	    if (!checkAdminAuth(session, redirect)) {
	        return "redirect:/board/vote_list";
	    }
	    
	    Member loggedInMember = (Member) session.getAttribute("loggedInMember");

	    if (loggedInMember != null) {
	        pollService.savePollAndItems(poll_title, poll_content, poll_end_date, poll_items, file, loggedInMember);
	        redirect.addFlashAttribute("message", "투표가 성공적으로 작성되었습니다.");
	        return "redirect:/board/vote_list";
	    } else {
	        redirect.addFlashAttribute("error", "로그인 후 이용 가능합니다.");
	        return "redirect:/member/login";
	    }
	}
	
	// 투표 삭제 처리
	@PostMapping("/board/vote_delete")
	public String voteDelete(@RequestParam("pollNo") int pollNo, HttpSession session, RedirectAttributes redirect) {
	    // 관리자 권한 체크 추가
	    if (!checkAdminAuth(session, redirect)) {
	        return "redirect:/board/vote_list";
	    }
	    
	    try {
	        pollService.deletePoll(pollNo);
	        redirect.addFlashAttribute("success", "투표가 성공적으로 삭제되었습니다.");
	    } catch (Exception e) {
	        redirect.addFlashAttribute("error", "투표 삭제 중 오류가 발생했습니다.");
	    }
	    return "redirect:/board/vote_list";
	}
	
	@GetMapping("/board/test")
	public String test() {
		return "board/test";
	}
	
	
	
	//투표 수정 ---------------------------------------------------------------------------
	// 투표 수정 페이지를 보여주는 GET 메서드
    @GetMapping("/board/vote_edit")
    public String vote_edit(@RequestParam(value = "pollNo") int pollNo, HttpSession session, Model model, RedirectAttributes redirect) {
        // 관리자 권한 체크 추가
        if (!checkAdminAuth(session, redirect)) {
            return "redirect:/board/vote_list";
        }
        
        Optional<Poll> optionalPoll = pollRepository.findById(pollNo);
        if (optionalPoll.isPresent()) {
            model.addAttribute("poll", optionalPoll.get());
            return "board/vote_edit";
        } else {
            // 투표를 찾을 수 없는 경우
            return "redirect:/board/vote_list";
        }
    }
    
    @PostMapping("/board/vote_edit")
    @Transactional
    public String updateVote(
        @RequestParam("pollNo") int pollNo,
        @RequestParam("poll_end_date") String poll_end_date,
        HttpSession session,
        RedirectAttributes redirect) {
        
        // 관리자 권한 체크 추가
        if (!checkAdminAuth(session, redirect)) {
            return "redirect:/board/vote_list";
        }
        
        // 로그인 확인
        Member loggedInMember = (Member) session.getAttribute("loggedInMember");
        if (loggedInMember == null) {
            redirect.addFlashAttribute("error", "로그인이 필요합니다.");
            return "redirect:/member/login";
        }
        
        try {
            Optional<Poll> optionalPoll = pollRepository.findById(pollNo);
            
            if (optionalPoll.isPresent()) {
                Poll poll = optionalPoll.get();
                
                // 작성자 확인
                if (!poll.getMember().getLoginId().equals(loggedInMember.getLoginId())) {
                    redirect.addFlashAttribute("error", "수정 권한이 없습니다.");
                    return "redirect:/board/vote_view?pollNo=" + pollNo;
                }
                
                if (poll_end_date != null && !poll_end_date.isEmpty()) {
                    Timestamp newEndDate = Timestamp.valueOf(poll_end_date.replace("T", " ") + ":00");
                    
                    if (newEndDate.before(new Timestamp(System.currentTimeMillis()))) {
                        redirect.addFlashAttribute("error", "종료 시간은 현재 시간보다 이후여야 합니다.");
                        return "redirect:/board/vote_edit?pollNo=" + pollNo;
                    }
                    
                    poll.setPoll_end_date(newEndDate);
                    pollRepository.save(poll);
                    
                    // 수정 성공 메시지 제거
                    return "redirect:/board/vote_view?pollNo=" + pollNo;
                } else {
                    redirect.addFlashAttribute("error", "종료 시간을 입력해주세요.");
                    return "redirect:/board/vote_edit?pollNo=" + pollNo;
                }
            } else {
                redirect.addFlashAttribute("error", "해당 투표를 찾을 수 없습니다.");
                return "redirect:/board/vote_list";
            }
        } catch (Exception e) {
            System.err.println("투표 수정 오류: " + e.getMessage());
            redirect.addFlashAttribute("error", "투표 수정 중 오류가 발생했습니다. 다시 시도해 주세요.");
            return "redirect:/board/vote_edit?pollNo=" + pollNo;
        }
    }
	
	
}