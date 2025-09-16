package com.java.controller;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/adpage")
public class AdpageController {

	@GetMapping("/graphCommu")
	public String main(Model model) {
		model.addAttribute("headerTitle", "ADMIN"); 
		return "adpage/graphCommu";
	}
	
	@GetMapping("/excelCommu")
	public void exportExcelCommu(HttpServletResponse response) throws Exception {
		// 엑셀 파일 이름
		String fileName = "CommunityStats.xlsx";
		
		// 설정
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
		
        // 엑셀 생성
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("커뮤니티 활동 분석");
        
        // 헤더
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("날짜");
        header.createCell(1).setCellValue("신규 게시물");
        header.createCell(2).setCellValue("신규 댓글");
        
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
	
	
	@GetMapping("/graphShop")
	public String notice(Model model) {
		model.addAttribute("headerTitle", "ADMIN"); 
		return "adpage/graphShop";
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
	
	@GetMapping("/shop")
	public String shop(Model model) {
		model.addAttribute("headerTitle", "ADMIN"); 
		return "adpage/shop";
	}
	
	
	
	
}
