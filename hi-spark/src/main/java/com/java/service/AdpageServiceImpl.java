package com.java.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.entity.Orders;
import com.java.entity.Product;
import com.java.repository.BoardRepository;
import com.java.repository.sCommentRepository;
import com.java.repository.OrdersRepository;
import com.java.repository.ProductRepository;

@Service
public class AdpageServiceImpl implements AdpageService{

   @Autowired BoardRepository boardRepository;
   @Autowired sCommentRepository commentsRepository;
   @Autowired OrdersRepository ordersRepository;
   @Autowired ProductRepository productRepository;

   
   @Override
   public Map<String, Map<String, Integer>> getStats(LocalDateTime start, LocalDateTime end) {
       try {
           System.out.println("=== 서비스 디버깅 시작 ===");
           System.out.println("Start: " + start);
           System.out.println("End: " + end);
           
           // 기간 내 모든 날짜를 먼저 초기화 (빈 날짜도 0으로 표시하기 위해)
           Map<String, Integer> postMap = new LinkedHashMap<>();
           Map<String, Integer> commentMap = new LinkedHashMap<>();
           
           LocalDate currentDate = start.toLocalDate();
           LocalDate endDate = end.toLocalDate();
           
           // 모든 날짜를 0으로 초기화
           while (!currentDate.isAfter(endDate)) {
               String dateStr = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
               postMap.put(dateStr, 0);
               commentMap.put(dateStr, 0);
               currentDate = currentDate.plusDays(1);
           }

           // 게시글 통계 조회
           List<Object[]> postResults = boardRepository.countPostsByDateBetween(start, end);
           System.out.println("Post results size: " + postResults.size());

           for (Object[] result : postResults) {
               String dateStr = (String) result[0];
               Integer count = ((Number) result[1]).intValue();
               postMap.put(dateStr, count);
               System.out.println("Post: " + dateStr + " = " + count);
           }

           // 댓글 통계 조회
           List<Object[]> commentResults = commentsRepository.countCommentsByDate(start, end);
           System.out.println("Comment results size: " + commentResults.size());

           for (Object[] result : commentResults) {
               String dateStr = (String) result[0];
               Integer count = ((Number) result[1]).intValue();
               commentMap.put(dateStr, count);
               System.out.println("Comment: " + dateStr + " = " + count);
           }

           Map<String, Map<String, Integer>> statsMap = new HashMap<>();
           statsMap.put("posts", postMap);
           statsMap.put("comments", commentMap);

           System.out.println("Final statsMap: " + statsMap);
           return statsMap;
           
       } catch (Exception e) {
           e.printStackTrace();
           // 오류 발생 시 빈 데이터 반환
           Map<String, Map<String, Integer>> result = new HashMap<>();
           result.put("posts", new LinkedHashMap<String, Integer>());
           result.put("comments", new LinkedHashMap<String, Integer>());
           return result;
       }
   }


   @Override
   public Map<String, Object> getOrdersData(Timestamp start, Timestamp end) {
       try {
           List<Orders> orders = ordersRepository.findOrdersBetween(start, end);
           
           // LinkedHashMap을 사용하여 날짜 순서 유지
           Map<String, Integer> dailySales = new LinkedHashMap<>();
           Map<String, Integer> dailyOrders = new LinkedHashMap<>();
           
           SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
           
           // 기간 내 모든 날짜를 먼저 초기화 (빈 날짜도 0으로 표시하기 위해)
           Calendar cal = Calendar.getInstance();
           cal.setTime(new Date(start.getTime()));
           
           while (!cal.getTime().after(new Date(end.getTime()))) {
               String dateStr = sdf.format(cal.getTime());
               dailySales.put(dateStr, 0);
               dailyOrders.put(dateStr, 0);
               cal.add(Calendar.DAY_OF_MONTH, 1);
           }
           
           // 실제 주문 데이터로 값 업데이트
           for (Orders o : orders) {
               // 주문 상태가 취소(-1)가 아닌 경우만 집계
               if (o.getOrderState() != -1) {
                   String date = sdf.format(o.getCreatedAt());
                   dailySales.put(date, dailySales.getOrDefault(date, 0) + o.getTotalAmount());
                   dailyOrders.put(date, dailyOrders.getOrDefault(date, 0) + 1);
               }
           }
           
           Map<String, Object> result = new LinkedHashMap<>();
           result.put("sales", dailySales);
           result.put("orders", dailyOrders);
           
           
           return result;
           
       } catch (Exception e) {
           e.printStackTrace();
           // 오류 발생 시 빈 데이터 반환
           Map<String, Object> result = new LinkedHashMap<>();
           result.put("sales", new LinkedHashMap<String, Integer>());
           result.put("orders", new LinkedHashMap<String, Integer>());
           return result;
       }
   }
   
   

   @Override
    public Product save(Product product) {
        if (product.getProductImg() == null || product.getProductImg().isEmpty()) {
            product.setProductImg("default-image.png"); // 기본 이미지
        }
        if (product.getDelfee() < 0) {
            product.setDelfee(0); // 배송비 기본값
        }
        return productRepository.save(product);
    }

    @Override
    public Product update(int id, Product product) {
        Optional<Product> existingOpt = productRepository.findById(id);
        if (existingOpt.isPresent()) {
            Product existing = existingOpt.get();
            existing.setProductName(product.getProductName());
            existing.setProductPrice(product.getProductPrice());
            existing.setProductQuantity(product.getProductQuantity());
            existing.setProductContent(product.getProductContent());
            existing.setProductImg(
                (product.getProductImg() == null || product.getProductImg().isEmpty()) 
                    ? existing.getProductImg() 
                    : product.getProductImg()
            );
            existing.setDelfee(product.getDelfee());
            return productRepository.save(existing);
        } else {
            throw new RuntimeException("상품을 찾을 수 없습니다. id=" + id);
        }
    }

    @Override
    public void delete(int id) {
        productRepository.deleteById(id);
    }

    @Override
    public Optional<Product> findById(int id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }


    @Override
    public Map<String, Integer> getDailyPostCounts(LocalDate startDate, LocalDate endDate) {
        Map<String, Integer> result = new LinkedHashMap<>();
        
        // 날짜 범위 내의 모든 날짜를 0으로 초기화
        LocalDate current = startDate;
        while (!current.isAfter(endDate)) {
            result.put(current.format(DateTimeFormatter.ofPattern("M/d")), 0);
            current = current.plusDays(1);
        }
        
        // 실제 게시물 수 조회
        List<Object[]> posts = boardRepository.countPostsByDateRange(startDate, endDate);
        
        for (Object[] row : posts) {
            // Timestamp를 LocalDate로 변환
            LocalDate date;
            if (row[0] instanceof java.sql.Timestamp) {
                date = ((java.sql.Timestamp) row[0]).toLocalDateTime().toLocalDate();
            } else if (row[0] instanceof java.sql.Date) {
                date = ((java.sql.Date) row[0]).toLocalDate();
            } else {
                // 다른 타입일 경우 처리
                continue;
            }
            
            // COUNT 결과를 안전하게 처리
            Number count = (Number) row[1];
            String dateKey = date.format(DateTimeFormatter.ofPattern("M/d"));
            result.put(dateKey, count.intValue());
        }
        
        return result;
    }

    @Override
    public Map<String, Integer> getDailyCommentCounts(LocalDate startDate, LocalDate endDate) {
        Map<String, Integer> result = new LinkedHashMap<>();
        
        // 날짜 범위 내의 모든 날짜를 0으로 초기화
        LocalDate current = startDate;
        while (!current.isAfter(endDate)) {
            result.put(current.format(DateTimeFormatter.ofPattern("M/d")), 0);
            current = current.plusDays(1);
        }
        
        // 실제 댓글 수 조회
        List<Object[]> comments = commentsRepository.countCommentsByDateRange(startDate, endDate);
        
        for (Object[] row : comments) {
            // Timestamp를 LocalDate로 변환
            LocalDate date;
            if (row[0] instanceof java.sql.Timestamp) {
                date = ((java.sql.Timestamp) row[0]).toLocalDateTime().toLocalDate();
            } else if (row[0] instanceof java.sql.Date) {
                date = ((java.sql.Date) row[0]).toLocalDate();
            } else {
                // 다른 타입일 경우 처리
                continue;
            }
            
            // COUNT 결과를 안전하게 처리
            Number count = (Number) row[1];
            String dateKey = date.format(DateTimeFormatter.ofPattern("M/d"));
            result.put(dateKey, count.intValue());
        }
        
        return result;
    }

    
}
