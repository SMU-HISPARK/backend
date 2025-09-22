package com.java.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.client.RestTemplate;

import com.java.dto.ClubDto;
import com.java.dto.MemberDto;
import com.java.dto.OrderDto;
import com.java.dto.OrderItemDto;
import com.java.dto.sComment;
import com.java.dto.Board;
import com.java.entity.userData.GameRun;
import com.java.entity.Member;
import com.java.entity.OrderItem;
import com.java.entity.Orders;
import com.java.repository.MyBoardRepository;
import com.java.repository.sCommentRepository;
import com.java.service.MemberService;
import com.java.service.MypageService;
//import com.java.service.ResultUnlockedService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/mypage")
public class MypageController {

   @Autowired MemberService memberService;
   @Autowired MypageService mypageService;
//   @Autowired ResultUnlockedService resultUnlockedService;
   
    
   @Autowired HttpSession session;
   
    @Value("${api1.service_key}")
    private String dservice_key;


    @GetMapping("/member")
    public String mypage(Model model) {
        String loginId = (String) session.getAttribute("session_id");

        Optional<Member> memberOpt = memberService.findByLoginId(loginId);
        MemberDto mDto = memberOpt.map(MemberDto::fromEntity).orElse(null);

        model.addAttribute("m", mDto);

        System.out.println(mDto);
        return "mypage/member";
    }

    
    @ResponseBody
    @PostMapping("/member/update")
    public String updateMember(@RequestParam(name = "nickname") String nickname,
          @RequestParam(name = "phone1") String phone1,
          @RequestParam(name = "phone2") String phone2,
          @RequestParam(name = "phone3") String phone3) {
       
       String loginId = (String) session.getAttribute("session_id");
       
       
       mypageService.updateMember(loginId,nickname,phone1,phone2,phone3);
       
       return "OK";
       
    }


   @GetMapping("/chgpw")
    public String chgpw() {
        return "mypage/chgpw";
    }

    @PostMapping("/chgpw")
    public String chgpw(@RequestParam(name = "currPassword") String currPassword,
                        @RequestParam(name = "newPassword") String newPassword,
                        Model model) {

        String loginId = (String) session.getAttribute("session_id");
        Optional<Member> memberOpt = memberService.findByLoginId(loginId);

        if (memberOpt.isEmpty()) {
            model.addAttribute("error", "회원 정보를 찾을 수 없습니다.");
            return "mypage/chgpw";
        }

        Member member = memberOpt.get();

        // 현재 비밀번호 확인
        if (!member.getPassword().equals(currPassword)) {
            model.addAttribute("error", "현재 비밀번호가 일치하지 않습니다.");
            return "mypage/chgpw";
        }

        // 새 비밀번호로 변경
        mypageService.updateMemberPassword(loginId, newPassword);
        model.addAttribute("success", "비밀번호가 성공적으로 변경되었습니다.");
        return "mypage/chgpw";
    }
   

//    @GetMapping("/club")
//    public String getClubs(Model model, @SessionAttribute("session_id") String loginId) {
//        
//       List<com.java.entity.sourceData.GameResultClub> allClubs = resultUnlockedService.getAllClubs(); // 모든 동아리
//        List<GameRun> myClubs = resultUnlockedService.getMyClubs(loginId);   // 내가 가입한 동아리
//
//        Map<Integer, String> clubImages = Map.of(
//               1, "/images/mypage/book.png",
//               2, "/images/mypage/band.png",
//               3, "/images/mypage/school.png",
//               4, "/images/mypage/basket.png",
//               5, "/images/mypage/cake.png"
//           );
//
//           List<ClubDto> clubDtos = new ArrayList<>();
//           for (com.java.entity.sourceData.GameResultClub club : allClubs) {
//               boolean joined = myClubs.stream()
//                       .anyMatch(gr -> gr.getClub() != null && gr.getClub().getClubId() == club.getClubId());
//
//               clubDtos.add(ClubDto.builder()
//                       .clubId(club.getClubId())
//                       .name(club.getName())
//                       .imageUrl(clubImages.get(club.getClubId())) // 여기서 이미지 매핑
//                       .finishedAt(joined ? Timestamp.valueOf(LocalDateTime.now()) : null)
//                       .build());
//           }
//
//        model.addAttribute("clubs", clubDtos);
//        return "mypage/club";
//        
//    }

    @GetMapping("/community")
    public String community(@RequestParam(name="page", defaultValue = "1") int page,Model model) {
       
       String loginId = (String) session.getAttribute("session_id");
        Optional<Member> memberOpt = memberService.findByLoginId(loginId);

        Member member = memberService.findByLoginId(loginId)
                .orElseThrow(() -> new RuntimeException("로그인 정보가 없습니다."));

        List<Board> postsList = mypageService.getAllBoards(member); // 전체 게시글
        List<sComment> commentsList = mypageService.getCommentsByMember(member); // 내 댓글

        model.addAttribute("postsList", postsList);
        model.addAttribute("commentsList", commentsList);

        return "mypage/community";
       
    }

    @GetMapping("/shop")
    public String shop(@RequestParam(name="page", defaultValue = "1") int page, Model model) {
        String loginId = (String) session.getAttribute("session_id");
        if (loginId == null) {
            return "redirect:/"; // 로그인하지 않은 경우 홈으로 리다이렉트
        }

        try {
            System.out.println("마이페이지 주문내역 페이지 접근: " + loginId);
            System.out.println("받은 page 파라미터: " + page);
            
            // 페이지네이션 설정
            int currentPage = page - 1; // pageable은 0부터 시작
            int size = 4; // 1페이지당
            int rowperpage = 5; // 하단넘버링 개수 5개
            
            // 정렬 - 주문일 기준 내림차순 (최신순)
            Sort sort = Sort.by(Sort.Order.desc("createdAt"));
            
            // 주문 가져오기
            Pageable pageable = PageRequest.of(currentPage, size, sort);
            Page<Orders> pageList = mypageService.getOrdersByMemberIdWithPaging(loginId, pageable);
            
            // DTO로 변환
            List<OrderDto> orderDtoList = pageList.getContent().stream()
                                                .map(OrderDto::from)
                                                .collect(Collectors.toList());
            
            // 페이지 정보 계산
            int totalElements = (int) pageList.getTotalElements();
            int totalPages = pageList.getTotalPages();
            
            int startpage = ((page - 1) / rowperpage) * rowperpage + 1;
            int endpage = Math.min(startpage + rowperpage - 1, totalPages);
            
            // 디버깅 로그
            System.out.println("현재 페이지: " + page);
            System.out.println("총 주문 수: " + totalElements);
            System.out.println("총 페이지 수: " + totalPages);
            System.out.println("시작 페이지: " + startpage);
            System.out.println("끝 페이지: " + endpage);
            
            // 모델에 데이터 추가
            model.addAttribute("ordersList", orderDtoList);
            model.addAttribute("page", page);
            model.addAttribute("maxpage", totalPages);
            model.addAttribute("startpage", startpage);
            model.addAttribute("endpage", endpage);
            model.addAttribute("totalOrders", totalElements);
            
            return "mypage/shop";
            
        } catch (Exception e) {
            System.err.println("주문내역 페이지 로드 중 오류: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("ordersList", List.of());
            model.addAttribute("error", "주문내역을 불러오는데 실패했습니다.");
            return "mypage/shop";
        }
    }

    @ResponseBody
    @GetMapping("/shop/detail")
    public Map<String, Object> getOrderDetail(@RequestParam(name = "orderCode") String orderCode) {
       String loginId = (String) session.getAttribute("session_id");

        // 해당 회원의 주문인지 확인 후 반환
        Orders order = mypageService.getOrderByCode(orderCode);
        if (order.getMember().getLoginId().equals(loginId)) {
           
           // DTO
           List<OrderItemDto> orderItems = mypageService.getOrderItemsByOrderCode(orderCode);
           
           Map<String, Object> result = new HashMap<>();
            result.put("order", order);
            result.put("orderItems", orderItems);
           
           return result;
        }
        return null; // 권한이 없거나 주문이 없는 경우
    }
    
    @ResponseBody
    @GetMapping("/shop/tracking")
    public String tracking(@RequestParam(name = "orderCode") String orderCode){
        
       return mypageService.getTrackingStatus(orderCode);

    }

    @GetMapping("/shopupdate")
    public String shopupdate(@RequestParam(name = "orderCode") String orderCode, Model model) {
        String loginId = (String) session.getAttribute("session_id");
        if (loginId == null) {
            return "redirect:/";
        }
        
        Orders order = mypageService.getOrderByCode(orderCode);
        model.addAttribute("order", order);
        
        System.out.println(order);
        return "mypage/shopupdate";
    }

    @PostMapping("/shopupdate")
    @ResponseBody
    public String updateShopInfo(@RequestParam(name = "orderCode") String orderCode, 
                               @RequestParam(name = "receiver") String receiver,
                               @RequestParam(name = "phone") String phone,
                               @RequestParam(name = "zipcode") String zipcode,
                               @RequestParam(name = "addressMain") String addressMain,
                               @RequestParam(name = "addressDetail") String addressDetail,
                               @RequestParam(name = "deliveryMessage") String deliveryMessage) {
        String loginId = (String) session.getAttribute("session_id");
        if (loginId == null) {
            return "FAIL";
        }
        
        try {
            mypageService.updateOrderAddress(orderCode, receiver, phone, zipcode, 
                                           addressMain, addressDetail, deliveryMessage);
            return "SUCCESS";
        } catch (Exception e) {
            return "FAIL";
        }
    }
    
    @PostMapping("/shop/cancel")
    @ResponseBody
    public Map<String, Object> cancelOrder(@RequestParam(name = "orderCode") String orderCode, HttpSession session) {
        Map<String, Object> response = new HashMap<>();

        try {
            String loginId = (String) session.getAttribute("session_id");

            if (loginId == null) {
                response.put("success", false);
                response.put("message", "로그인이 필요합니다.");
                return response;
            }

            // 주문 취소 서비스 호출
            boolean cancelResult = mypageService.cancelOrder(orderCode, loginId);

            if (cancelResult) {
                response.put("success", true);
                response.put("message", "주문이 성공적으로 취소되었습니다.");
            } else {
                response.put("success", false);
                response.put("message", "주문 취소에 실패했습니다.");
            }

        } catch (Exception e) {
            e.printStackTrace(); // 디버깅을 위해 추가
            response.put("success", false);
            response.put("message", "주문취소 처리 중 오류가 발생했습니다.");
        }

        return response;
    }

    
    @GetMapping("/point")
    public String point(Model model) {
        String loginId = (String) session.getAttribute("session_id");
        Optional<Member> memberOpt = memberService.findByLoginId(loginId);
        MemberDto mDto = memberOpt.map(MemberDto::fromEntity).orElse(null);

        model.addAttribute("currentPoint", mDto != null ? mDto.getPoint() : 0);
        return "mypage/point";
    }

    @PostMapping("/point")
    public String point(@RequestParam(name = "point") int point, Model model) {
        String loginId = (String) session.getAttribute("session_id");
        Optional<Member> memberOpt = memberService.findByLoginId(loginId);

        if (memberOpt.isPresent()) {
            MemberDto mDto = MemberDto.fromEntity(memberOpt.get());
            int newPoint = mDto.getPoint() + point;
            mypageService.updateMemberPoint(loginId, newPoint);
        }

        return "redirect:/mypage/point";
    }
}

