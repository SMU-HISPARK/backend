package com.java.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.Authentication;
import com.java.entity.Product;
import com.java.repository.BoardRepository;
import com.java.repository.sCommentRepository;
import com.java.service.AdpageService;
import com.java.service.AdpageServiceImpl;
import com.java.service.ProductService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/adpage")
public class AdpageController {
	@Autowired AdpageService adpageService;
	@Autowired HttpSession session;
	@Autowired ProductService productService;
	
	private final MemberController memberController;
    private final AdpageServiceImpl adpageServiceImpl;

    AdpageController(MemberController memberController, AdpageServiceImpl adpageServiceImpl) {
        this.memberController = memberController;
        this.adpageServiceImpl = adpageServiceImpl;
    }
   
    @GetMapping("/graphCommu")
    public String getGraphCommuPage(Model model) {
    	
    	String sessionId = (String) session.getAttribute("session_id");
        String sessionName = (String) session.getAttribute("session_name");
        
        model.addAttribute("adminId", sessionId);
        model.addAttribute("adminName", sessionName);
        
        System.out.println("관리자 메인 페이지 접근: " + sessionId);
        
        try {
            // 기본 일주일 데이터를 모델에 추가
            LocalDate today = LocalDate.now();
            LocalDate startDate = today.minusDays(6);
            LocalDate endDate = today;
            
            LocalDateTime startDateTime = startDate.atStartOfDay();
            LocalDateTime endDateTime = endDate.plusDays(1).atStartOfDay().minusNanos(1);
            
            Map<String, Map<String, Integer>> data = adpageService.getStats(startDateTime, endDateTime);
            
            // 총 게시물, 총 댓글 수 계산
            Map<String, Integer> postsData = data.get("posts");
            Map<String, Integer> commentsData = data.get("comments");
            
            int totalPosts = postsData != null ? postsData.values().stream().mapToInt(Integer::intValue).sum() : 0;
            int totalComments = commentsData != null ? commentsData.values().stream().mapToInt(Integer::intValue).sum() : 0;
            
            model.addAttribute("totalPosts", totalPosts);
            model.addAttribute("totalComments", totalComments);
            model.addAttribute("currentPeriod", "week");
            model.addAttribute("startDate", startDate);
            model.addAttribute("endDate", endDate);
            
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("totalPosts", 0);
            model.addAttribute("totalComments", 0);
            model.addAttribute("currentPeriod", "week");
        }
        
        return "adpage/graphCommu"; // JSP 파일 경로
    }

    // AJAX 요청을 위한 JSON 데이터 제공 메소드
    @GetMapping("/graphCommu/data")
    @ResponseBody
    public ResponseEntity<Map<String, Map<String, Integer>>> getCommuChartData(
        @RequestParam(name = "period", required = false, defaultValue = "week") String period,
        @RequestParam(name = "startDate", required = false) String startDate,
        @RequestParam(name = "endDate", required = false) String endDate) {
        
        try {
            LocalDate today = LocalDate.now();
            LocalDate start, end;
            
            switch(period) {
                case "today":
                    start = today;
                    end = today;
                    break;
                    
                case "week":
                    start = today.minusDays(6);
                    end = today;
                    break;
                    
                case "month":
                    start = today.minusDays(29);
                    end = today;
                    break;
                    
                case "custom":
                    if(startDate == null || endDate == null) {
                        throw new IllegalArgumentException("사용자 지정 기간에는 시작일과 종료일이 필요합니다.");
                    }
                    start = LocalDate.parse(startDate);
                    end = LocalDate.parse(endDate);
                    break;
                    
                default:
                    start = today.minusDays(6);
                    end = today;
            }
            
            LocalDateTime startDateTime = start.atStartOfDay();
            LocalDateTime endDateTime = end.plusDays(1).atStartOfDay().minusNanos(1);

            Map<String, Map<String, Integer>> result = adpageService.getStats(startDateTime, endDateTime);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Map<String, Integer>> errorResponse = new HashMap<>();
            errorResponse.put("posts", new HashMap<String, Integer>());
            errorResponse.put("comments", new HashMap<String, Integer>());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
   
    @GetMapping("/excelCommu")
    public void exportExcelCommu(
           @RequestParam(value = "period", defaultValue = "week") String period,
           @RequestParam(value = "startDate", required = false) String startDate,
           @RequestParam(value = "endDate", required = false) String endDate,
           HttpServletResponse response) throws Exception {
        
        // 데이터 조회 - 기존 그래프 데이터와 동일한 로직 사용
        Map<String, Object> data = getActivityData(period, startDate, endDate);
        Map<String, Integer> postsData = (Map<String, Integer>) data.get("posts");
        Map<String, Integer> commentsData = (Map<String, Integer>) data.get("comments");
        
        // 엑셀 파일 이름
        String fileName = "CommunityStats_" + period + ".xlsx";
        
        // 설정
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        
        // 엑셀 생성
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("커뮤니티 활동 분석");
        
        // 헤더 스타일
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        
        // 헤더 생성
        Row header = sheet.createRow(0);
        Cell headerCell1 = header.createCell(0);
        headerCell1.setCellValue("날짜");
        headerCell1.setCellStyle(headerStyle);
        
        Cell headerCell2 = header.createCell(1);
        headerCell2.setCellValue("신규 게시물");
        headerCell2.setCellStyle(headerStyle);
        
        Cell headerCell3 = header.createCell(2);
        headerCell3.setCellValue("신규 댓글");
        headerCell3.setCellStyle(headerStyle);
        
        // 데이터 정렬 (날짜순)
        List<String> sortedDates = new ArrayList<>(postsData.keySet());
        Collections.sort(sortedDates);
        
        // 데이터 입력
        int rowNum = 1;
        for (String date : sortedDates) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(date);
            row.createCell(1).setCellValue(postsData.getOrDefault(date, 0));
            row.createCell(2).setCellValue(commentsData.getOrDefault(date, 0));
        }
        
        // 합계 행 추가
        Row totalRow = sheet.createRow(rowNum);
        Cell totalLabelCell = totalRow.createCell(0);
        totalLabelCell.setCellValue("총 합계");
        totalLabelCell.setCellStyle(headerStyle);
        
        int totalPosts = postsData.values().stream().mapToInt(Integer::intValue).sum();
        int totalComments = commentsData.values().stream().mapToInt(Integer::intValue).sum();
        
        Cell totalPostsCell = totalRow.createCell(1);
        totalPostsCell.setCellValue(totalPosts);
        totalPostsCell.setCellStyle(headerStyle);
        
        Cell totalCommentsCell = totalRow.createCell(2);
        totalCommentsCell.setCellValue(totalComments);
        totalCommentsCell.setCellStyle(headerStyle);
        
        // 열 너비 자동 맞춤
        for (int i = 0; i < 3; i++) {
            sheet.autoSizeColumn(i);
        }
        
        workbook.write(response.getOutputStream());
        workbook.close();
    }


	// 기존 그래프 데이터 조회 메서드를 재사용하거나, 동일한 로직으로 구현
    private Map<String, Object> getActivityData(String period, String startDate, String endDate) {
        // 여기서 실제 데이터 조회 로직 구현
        LocalDate start, end;
        switch (period) {
            case "today":
                start = end = LocalDate.now();
                break;
            case "week":
                start = LocalDate.now().minusDays(6);
                end = LocalDate.now();
                break;
            case "month":
                start = LocalDate.now().minusMonths(1);
                end = LocalDate.now();
                break;
            case "custom":
                start = LocalDate.parse(startDate);
                end = LocalDate.parse(endDate);
                break;
            default:
                start = LocalDate.now().minusDays(6);
                end = LocalDate.now();
        }
        
        // 실제 데이터베이스에서 조회
        Map<String, Integer> postsData = adpageService.getDailyPostCounts(start, end);
        Map<String, Integer> commentsData = adpageService.getDailyCommentCounts(start, end);
        
        Map<String, Object> result = new HashMap<>();
        result.put("posts", postsData);
        result.put("comments", commentsData);
        return result;
    }
    
   // JSP 페이지를 렌더링하는 메소드 (페이지 접근용)
   @GetMapping("/graphShop")
   public String getGraphShopPage(Model model) {
       try {
           // 기본 일주일 데이터를 모델에 추가
           java.util.Date now = new java.util.Date();
           Calendar cal = Calendar.getInstance();
           cal.setTime(now);
           cal.add(Calendar.DAY_OF_MONTH, -6);
           cal.set(Calendar.HOUR_OF_DAY, 0);
           cal.set(Calendar.MINUTE, 0);
           cal.set(Calendar.SECOND, 0);
           cal.set(Calendar.MILLISECOND, 0);
           Timestamp startTs = new Timestamp(cal.getTimeInMillis());
           Timestamp endTs = new Timestamp(now.getTime());
           
           Map<String, Object> data = adpageService.getOrdersData(startTs, endTs);
           
           // 총 매출, 총 주문 건수 계산
           Map<String, Integer> salesData = (Map<String, Integer>) data.get("sales");
           Map<String, Integer> ordersData = (Map<String, Integer>) data.get("orders");
           
           int totalSales = salesData.values().stream().mapToInt(Integer::intValue).sum();
           int totalOrders = ordersData.values().stream().mapToInt(Integer::intValue).sum();
           
           model.addAttribute("totalSales", totalSales);
           model.addAttribute("totalOrders", totalOrders);
           model.addAttribute("currentPeriod", "week");
           
       } catch (Exception e) {
           e.printStackTrace();
           model.addAttribute("totalSales", 0);
           model.addAttribute("totalOrders", 0);
           model.addAttribute("currentPeriod", "week");
       }
       
       return "adpage/graphShop"; // JSP 파일 경로
   }

   // AJAX 요청을 위한 JSON 데이터 제공 메소드
   @GetMapping("/graphShop/data")
   @ResponseBody
   public ResponseEntity<Map<String, Object>> getChartData(
       @RequestParam(name = "period", required = false, defaultValue = "week") String period,
       @RequestParam(name = "startDate", required = false) String startDate,
       @RequestParam(name = "endDate", required = false) String endDate) {
       
       try {
           SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
           Timestamp startTs;
           Timestamp endTs;

           // period 기준 시작, 끝 날짜 계산
           java.util.Date now = new java.util.Date();
           Calendar cal = Calendar.getInstance();
           
           switch(period) {
               case "today":
                   cal.setTime(now);
                   cal.set(Calendar.HOUR_OF_DAY, 0);
                   cal.set(Calendar.MINUTE, 0);
                   cal.set(Calendar.SECOND, 0);
                   cal.set(Calendar.MILLISECOND, 0);
                   startTs = new Timestamp(cal.getTimeInMillis());
                   
                   cal.set(Calendar.HOUR_OF_DAY, 23);
                   cal.set(Calendar.MINUTE, 59);
                   cal.set(Calendar.SECOND, 59);
                   endTs = new Timestamp(cal.getTimeInMillis());
                   break;
                   
               case "week":
                   cal.setTime(now);
                   cal.add(Calendar.DAY_OF_MONTH, -6);
                   cal.set(Calendar.HOUR_OF_DAY, 0);
                   cal.set(Calendar.MINUTE, 0);
                   cal.set(Calendar.SECOND, 0);
                   cal.set(Calendar.MILLISECOND, 0);
                   startTs = new Timestamp(cal.getTimeInMillis());
                   endTs = new Timestamp(now.getTime());
                   break;
                   
               case "month":
                   cal.setTime(now);
                   cal.add(Calendar.DAY_OF_MONTH, -29);
                   cal.set(Calendar.HOUR_OF_DAY, 0);
                   cal.set(Calendar.MINUTE, 0);
                   cal.set(Calendar.SECOND, 0);
                   cal.set(Calendar.MILLISECOND, 0);
                   startTs = new Timestamp(cal.getTimeInMillis());
                   endTs = new Timestamp(now.getTime());
                   break;
                   
               case "custom":
                   if(startDate == null || endDate == null) {
                       throw new IllegalArgumentException("사용자 지정 기간에는 시작일과 종료일이 필요합니다.");
                   }
                   startTs = Timestamp.valueOf(startDate + " 00:00:00");
                   endTs = Timestamp.valueOf(endDate + " 23:59:59");
                   break;
                   
               default:
                   cal.setTime(now);
                   cal.add(Calendar.DAY_OF_MONTH, -6);
                   cal.set(Calendar.HOUR_OF_DAY, 0);
                   cal.set(Calendar.MINUTE, 0);
                   cal.set(Calendar.SECOND, 0);
                   cal.set(Calendar.MILLISECOND, 0);
                   startTs = new Timestamp(cal.getTimeInMillis());
                   endTs = new Timestamp(now.getTime());
           }

           Map<String, Object> result = adpageService.getOrdersData(startTs, endTs);
           return ResponseEntity.ok(result);
           
       } catch (Exception e) {
           e.printStackTrace();
           Map<String, Object> errorResponse = new HashMap<>();
           errorResponse.put("error", "데이터 조회 중 오류가 발생했습니다.");
           errorResponse.put("message", e.getMessage());
           errorResponse.put("sales", new HashMap<String, Integer>());
           errorResponse.put("orders", new HashMap<String, Integer>());
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
       }
   }
   
   @GetMapping("/excelShop")
   public void exportExcelShop(
           @RequestParam(value = "period", defaultValue = "week") String period,
           @RequestParam(value = "startDate", required = false) String startDate,
           @RequestParam(value = "endDate", required = false) String endDate,
           HttpServletResponse response) throws Exception {
       
       // 데이터 조회 - 기존 그래프 데이터와 동일한 로직 사용
       Map<String, Object> data = getShopActivityData(period, startDate, endDate);
       Map<String, Integer> salesData = (Map<String, Integer>) data.get("sales");
       Map<String, Integer> ordersData = (Map<String, Integer>) data.get("orders");
       
       // 엑셀 파일 이름
       String fileName = "ShopStats_" + period + ".xlsx";
       
       // 설정
       response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
       response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
       
       // 엑셀 생성
       Workbook workbook = new XSSFWorkbook();
       Sheet sheet = workbook.createSheet("굿즈 판매량 분석");
       
       // 헤더 스타일
       CellStyle headerStyle = workbook.createCellStyle();
       Font headerFont = workbook.createFont();
       headerFont.setBold(true);
       headerStyle.setFont(headerFont);
       headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
       headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
       
       // 헤더 생성
       Row header = sheet.createRow(0);
       Cell headerCell1 = header.createCell(0);
       headerCell1.setCellValue("날짜");
       headerCell1.setCellStyle(headerStyle);
       
       Cell headerCell2 = header.createCell(1);
       headerCell2.setCellValue("매출");
       headerCell2.setCellStyle(headerStyle);
       
       Cell headerCell3 = header.createCell(2);
       headerCell3.setCellValue("주문건수");
       headerCell3.setCellStyle(headerStyle);
       
       // 데이터 정렬 (날짜순)
       List<String> sortedDates = new ArrayList<>(salesData.keySet());
       Collections.sort(sortedDates);
       
       // 데이터 입력
       int rowNum = 1;
       for (String date : sortedDates) {
           Row row = sheet.createRow(rowNum++);
           row.createCell(0).setCellValue(date);
           row.createCell(1).setCellValue(salesData.getOrDefault(date, 0));
           row.createCell(2).setCellValue(ordersData.getOrDefault(date, 0));
       }
       
       // 합계 행 추가
       Row totalRow = sheet.createRow(rowNum);
       Cell totalLabelCell = totalRow.createCell(0);
       totalLabelCell.setCellValue("총 합계");
       totalLabelCell.setCellStyle(headerStyle);
       
       int totalSales = salesData.values().stream().mapToInt(Integer::intValue).sum();
       int totalOrders = ordersData.values().stream().mapToInt(Integer::intValue).sum();
       
       Cell totalSalesCell = totalRow.createCell(1);
       totalSalesCell.setCellValue(totalSales);
       totalSalesCell.setCellStyle(headerStyle);
       
       Cell totalOrdersCell = totalRow.createCell(2);
       totalOrdersCell.setCellValue(totalOrders);
       totalOrdersCell.setCellStyle(headerStyle);
       
       // 열 너비 자동 맞춤
       for (int i = 0; i < 3; i++) {
           sheet.autoSizeColumn(i);
       }
       
       workbook.write(response.getOutputStream());
       workbook.close();
   }

   // Shop 데이터 조회를 위한 새로운 메서드 
   private Map<String, Object> getShopActivityData(String period, String startDate, String endDate) {
       try {
           SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
           Timestamp startTs;
           Timestamp endTs;

           // period 기준 시작, 끝 날짜 계산
           java.util.Date now = new java.util.Date();
           Calendar cal = Calendar.getInstance();
           
           switch (period) {
               case "today":
                   cal.setTime(now);
                   cal.set(Calendar.HOUR_OF_DAY, 0);
                   cal.set(Calendar.MINUTE, 0);
                   cal.set(Calendar.SECOND, 0);
                   cal.set(Calendar.MILLISECOND, 0);
                   startTs = new Timestamp(cal.getTimeInMillis());
                   
                   cal.set(Calendar.HOUR_OF_DAY, 23);
                   cal.set(Calendar.MINUTE, 59);
                   cal.set(Calendar.SECOND, 59);
                   endTs = new Timestamp(cal.getTimeInMillis());
                   break;
                   
               case "week":
                   cal.setTime(now);
                   cal.add(Calendar.DAY_OF_MONTH, -6);
                   cal.set(Calendar.HOUR_OF_DAY, 0);
                   cal.set(Calendar.MINUTE, 0);
                   cal.set(Calendar.SECOND, 0);
                   cal.set(Calendar.MILLISECOND, 0);
                   startTs = new Timestamp(cal.getTimeInMillis());
                   endTs = new Timestamp(now.getTime());
                   break;
                   
               case "month":
                   cal.setTime(now);
                   cal.add(Calendar.DAY_OF_MONTH, -29);
                   cal.set(Calendar.HOUR_OF_DAY, 0);
                   cal.set(Calendar.MINUTE, 0);
                   cal.set(Calendar.SECOND, 0);
                   cal.set(Calendar.MILLISECOND, 0);
                   startTs = new Timestamp(cal.getTimeInMillis());
                   endTs = new Timestamp(now.getTime());
                   break;
                   
               case "custom":
                   if (startDate == null || endDate == null) {
                       throw new IllegalArgumentException("사용자 지정 기간에는 시작일과 종료일이 필요합니다.");
                   }
                   startTs = Timestamp.valueOf(startDate + " 00:00:00");
                   endTs = Timestamp.valueOf(endDate + " 23:59:59");
                   break;
                   
               default:
                   cal.setTime(now);
                   cal.add(Calendar.DAY_OF_MONTH, -6);
                   cal.set(Calendar.HOUR_OF_DAY, 0);
                   cal.set(Calendar.MINUTE, 0);
                   cal.set(Calendar.SECOND, 0);
                   cal.set(Calendar.MILLISECOND, 0);
                   startTs = new Timestamp(cal.getTimeInMillis());
                   endTs = new Timestamp(now.getTime());
           }
           
           // 실제 데이터베이스에서 조회 (기존 getOrdersData 메소드 활용)
           return adpageService.getOrdersData(startTs, endTs);
           
       } catch (Exception e) {
           e.printStackTrace();
           // 오류 발생 시 빈 데이터 반환
           Map<String, Object> result = new HashMap<>();
           result.put("sales", new LinkedHashMap<String, Integer>());
           result.put("orders", new LinkedHashMap<String, Integer>());
           return result;
       }
   }
   
	// 상품 목록 페이지
   @GetMapping("/shop")
   public String shopPage(
           @RequestParam(name="page", defaultValue = "1") int page,
           Model model) {
       try {
           System.out.println("상품 목록 페이지 접근");
           System.out.println("받은 page 파라미터: " + page);
           
           // Pageable 하단넘버링
           int currentPage = page - 1; // pageable은 첫페이지가 0부터 시작
           int size = 6; // 1페이지당
           int rowperpage = 5; // 하단넘버링 개수 5개
           
           // 정렬 - productId 기준 내림차순 (최신순)
           Sort sort = Sort.by(Sort.Order.desc("productId"));
           
           // 상품 가져오기
           Pageable pageable = PageRequest.of(currentPage, size, sort);
           Page<Product> pageList = productService.findAll(pageable);
           
           // 페이지 정보 계산 - 수정된 부분
           List<Product> products = pageList.getContent();
           int totalElements = (int) pageList.getTotalElements(); // 총 상품 수
           int totalPages = pageList.getTotalPages(); // 총 페이지 수
           
           // 페이지네이션 계산
           int startpage = ((page - 1) / rowperpage) * rowperpage + 1;
           int endpage = Math.min(startpage + rowperpage - 1, totalPages);
           
           // 디버깅 로그
           System.out.println("현재 페이지: " + page);
           System.out.println("총 상품 수: " + totalElements);
           System.out.println("총 페이지 수: " + totalPages);
           System.out.println("시작 페이지: " + startpage);
           System.out.println("끝 페이지: " + endpage);
           
           // 모델에 데이터 추가
           model.addAttribute("products", products);
           model.addAttribute("page", page);
           model.addAttribute("maxpage", totalPages);
           model.addAttribute("startpage", startpage);
           model.addAttribute("endpage", endpage);
           model.addAttribute("totalProducts", totalElements);
           
           return "adpage/shop";
       } catch (Exception e) {
           System.err.println("상품 목록 페이지 로드 중 오류: " + e.getMessage());
           e.printStackTrace();
           model.addAttribute("products", List.of());
           model.addAttribute("error", "상품 목록을 불러오는데 실패했습니다.");
           return "adpage/shop";
       }
   }

   // 상품 등록
   @PostMapping("/shop")
   @ResponseBody
   public ResponseEntity<?> saveProduct(@RequestParam(value="image", required=false) MultipartFile file,
                                        @RequestParam(name = "productName") String productName,
                                        @RequestParam(name = "productPrice") int productPrice,
                                        @RequestParam(name = "productQuantity") int productQuantity,
                                        @RequestParam(name = "delfee") int delfee,
                                        @RequestParam(value="productContent", required=false) String productContent) {
       try {
           System.out.println("상품 등록 요청 받음");
           System.out.println("파일 있음: " + (file != null && !file.isEmpty()));
           System.out.println("파일 이름: " + (file != null ? file.getOriginalFilename() : "없음"));
           
           Product product = new Product();
           product.setProductName(productName);
           product.setProductPrice(productPrice);
           product.setProductQuantity(productQuantity);
           product.setDelfee(delfee);
           product.setProductContent(productContent != null ? productContent : "");

           // 이미지 처리 - 절대 경로 사용
           if (file != null && !file.isEmpty()) {
               // 사용자 홈 디렉토리에 uploads 폴더 생성
               String userHome = System.getProperty("user.home");
               String uploadDir = userHome + File.separator + "uploads" + File.separator + "productimage" + File.separator;
               File uploadDirFile = new File(uploadDir);
               if (!uploadDirFile.exists()) {
                   boolean created = uploadDirFile.mkdirs();
                   System.out.println("디렉토리 생성: " + created + " -> " + uploadDir);
               }
               
               String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
               File destinationFile = new File(uploadDir + fileName);
               
               file.transferTo(destinationFile);
               
               String imageUrl = "/uploads/productimage/" + fileName;
               product.setProductImg(imageUrl);
               System.out.println("이미지 저장됨: " + imageUrl);
               System.out.println("실제 파일 경로: " + destinationFile.getAbsolutePath());
           } else {
               // 기본 이미지 설정
               product.setProductImg("/images/hispark.png");
               System.out.println("기본 이미지 설정됨");
           }

           Product savedProduct = productService.save(product);
           System.out.println("상품 저장 완료: " + savedProduct.getProductName());
           return ResponseEntity.ok(savedProduct);

       } catch (Exception e) {
           System.err.println("상품 등록 중 오류: " + e.getMessage());
           e.printStackTrace();
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("상품 등록 실패: " + e.getMessage());
       }
   }

   
   // 상품 상세 조회
   @GetMapping("/shop/detail")
   @ResponseBody
   public ResponseEntity<?> getProductDetail(@RequestParam("productId") int productId) {
       try {
           System.out.println("상품 상세 조회 요청: productId = " + productId);
           
           if (productId <= 0) {
               return ResponseEntity.badRequest().body("유효하지 않은 상품 ID입니다.");
           }
           
           Optional<Product> productOpt = productService.findById(productId);
           
           if (productOpt.isPresent()) {
               Product product = productOpt.get();
               System.out.println("상품 조회 성공: " + product.getProductName());
               return ResponseEntity.ok(product);
           } else {
               System.out.println("상품을 찾을 수 없음: productId = " + productId);
               return ResponseEntity.notFound().build();
           }
           
       } catch (Exception e) {
           System.err.println("상품 상세 조회 중 오류: " + e.getMessage());
           e.printStackTrace();
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                              .body("상품 조회 실패: " + e.getMessage());
       }
   }
   
   // 상품 수정
   @PostMapping("/shop/update")
   @ResponseBody
   public ResponseEntity<?> updateProduct(@RequestParam(name = "productId") int productId,
                                          @RequestParam(value="image", required=false) MultipartFile file,
                                          @RequestParam(name = "productName") String productName,
                                          @RequestParam(name = "productPrice") int productPrice,
                                          @RequestParam(name = "productQuantity") int productQuantity,
                                          @RequestParam(name ="delfee") int delfee,
                                          @RequestParam(value="productContent", required=false) String productContent) {
       try {
           System.out.println("상품 수정 요청 받음: productId = " + productId);
           System.out.println("새 파일 업로드됨: " + (file != null && !file.isEmpty()));
           
           Optional<Product> productOpt = productService.findById(productId);
           if (!productOpt.isPresent()) {
               return ResponseEntity.badRequest().body("상품을 찾을 수 없습니다.");
           }

           Product product = productOpt.get();
           String originalImage = product.getProductImg(); // 기존 이미지 백업
           
           product.setProductName(productName);
           product.setProductPrice(productPrice);
           product.setProductQuantity(productQuantity);
           product.setDelfee(delfee);
           product.setProductContent(productContent != null ? productContent : "");

           // 이미지 처리 - 절대 경로 사용
           if (file != null && !file.isEmpty()) {
               // 새 이미지 업로드됨
               String userHome = System.getProperty("user.home");
               String uploadDir = userHome + File.separator + "uploads" + File.separator + "productimage" + File.separator;
               File uploadDirFile = new File(uploadDir);
               if (!uploadDirFile.exists()) {
                   boolean created = uploadDirFile.mkdirs();
                   System.out.println("디렉토리 생성: " + created + " -> " + uploadDir);
               }
               
               String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
               File destinationFile = new File(uploadDir + fileName);
               
               file.transferTo(destinationFile);
               
               String imageUrl = "/uploads/productimage/" + fileName;
               product.setProductImg(imageUrl);
               System.out.println("새 이미지 저장됨: " + imageUrl);
               System.out.println("실제 파일 경로: " + destinationFile.getAbsolutePath());
           } else {
               // 새 이미지가 없으면 기존 이미지 유지
               product.setProductImg(originalImage);
               System.out.println("기존 이미지 유지: " + originalImage);
           }

           Product updatedProduct = productService.update(productId, product);
           System.out.println("상품 수정 완료: " + updatedProduct.getProductName());
           return ResponseEntity.ok(updatedProduct);

       } catch (Exception e) {
           System.err.println("상품 수정 중 오류: " + e.getMessage());
           e.printStackTrace();
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("상품 수정 실패: " + e.getMessage());
       }
   }

   // 상품 삭제
   @PostMapping("/shop/delete")
   @ResponseBody
   public ResponseEntity<?> deleteProduct(@RequestParam("productId") int productId) {
       try {
           System.out.println("상품 삭제 요청: productId = " + productId);
           
           if (productId <= 0) {
               return ResponseEntity.badRequest().body("유효하지 않은 상품 ID입니다.");
           }
           
           productService.delete(productId);
           System.out.println("상품 삭제 성공: productId = " + productId);
           
           return ResponseEntity.ok("상품이 성공적으로 삭제되었습니다.");
           
       } catch (IllegalArgumentException e) {
           System.err.println("상품 삭제 유효성 검사 오류: " + e.getMessage());
           return ResponseEntity.badRequest().body(e.getMessage());
       } catch (Exception e) {
           System.err.println("상품 삭제 중 오류: " + e.getMessage());
           e.printStackTrace();
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                              .body("상품 삭제 실패: " + e.getMessage());
       }
   }
}
