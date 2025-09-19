package com.java.controller;

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
import java.util.List;
import java.util.Map;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.entity.Product;
import com.java.repository.BoardRepository;
import com.java.repository.sCommentRepository;
import com.java.service.AdpageService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/adpage")
public class AdpageController {

    private final MemberController memberController;

   @Autowired AdpageService adpageService;

    AdpageController(MemberController memberController) {
        this.memberController = memberController;
    }
   
    @GetMapping("/graphCommu")
    public String getGraphCommuPage(Model model) {
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
   public void exportExcelShop(HttpServletResponse response) throws Exception {
      // 엑셀 파일 이름
      String fileName = "ShopStats.xlsx";
      
      // 설정
      response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
      
        // 엑셀 생성
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("굿즈 판매량 분석");
        
        // 헤더
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("날짜");
        header.createCell(1).setCellValue("매출");
        header.createCell(2).setCellValue("주문건수");
        
        // 샘플 데이터(추후 디비 연결)
        String[] dates = {"3/9", "3/10", "3/11", "3/12", "3/13", "3/14", "3/15"};
        int[] newPosts = {65, 72, 58, 89, 76, 95, 89};
        int[] comments = {320, 380, 290, 456, 420, 500, 456};
        
        for(int i = 0; i < dates.length; i++) {
           Row row = sheet.createRow(i + 1);
           row.createCell(0).setCellValue(dates[i]);
           row.createCell(1).setCellValue(newPosts[i]);
           row.createCell(2).setCellValue(comments[i]);
        }
        
        // 열너비 자동 맞춤
        for ( int i = 0; i < 3; i++) {
           sheet.autoSizeColumn(i);
        }
        
        workbook.write(response.getOutputStream());
        workbook.close();
   }
   
   // 상품 목록
    @GetMapping("/shop")
    public String shopPage(Model model) {
        List<Product> products = adpageService.findAll(); // 서비스에서 전체 상품 가져오기
        model.addAttribute("products", products); 
        return "adpage/shop"; 
    }

    // 상품 등록
    @PostMapping("/shop")
    public ResponseEntity<String> saveProduct(@RequestBody Product product) {
        try {
            adpageService.save(product);
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error");
        }
    }

    // 상품 수정
    @PutMapping("/adpage/shop/detail?productId={id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestBody Product product) {
        try {
            adpageService.update(id, product);
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error");
        }
    }

    // 상품 삭제
    @DeleteMapping("/shop/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        try {
            adpageService.delete(id);
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error");
        }
    }

   
   
}
