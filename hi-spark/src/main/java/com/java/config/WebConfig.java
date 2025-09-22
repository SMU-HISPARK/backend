package com.java.config;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.java.Interceptor.AdminCheckInterceptor;
import com.java.Interceptor.LoginCheckInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Autowired private AdminCheckInterceptor adminCheckInterceptor;
    
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .addPathPatterns("/mypage/**")
                .excludePathPatterns("/", "/member/login");
        
        registry.addInterceptor(adminCheckInterceptor)
                .addPathPatterns("/adpage/**")
                .excludePathPatterns("/css/**", "/js/**", "/images/**");
    }
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 업로드된 파일을 제공하기 위한 설정 - 사용자 홈 디렉토리 사용
        String userHome = System.getProperty("user.home");
        String uploadPath = "file:" + userHome + File.separator + "uploads" + File.separator;
        
        System.out.println("Upload path configured: " + uploadPath);
        
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(uploadPath);
        
        // 기존 static resources 설정은 유지
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");
                
        registry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/static/images/");
                
        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/");
    }
	
}
