package com.java.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.java.entity.Product;
import com.java.repository.ProductRepository;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        try {
            List<Product> products = productRepository.findAll();
            System.out.println("전체 상품 조회 결과: " + products.size() + "개");
            return products;
        } catch (Exception e) {
            System.err.println("전체 상품 조회 중 오류: " + e.getMessage());
            throw new RuntimeException("상품 목록을 가져오는데 실패했습니다.", e);
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findById(int productId) {
        try {
            Optional<Product> product = productRepository.findById(productId);
            if (product.isPresent()) {
                System.out.println("상품 조회 성공: ID = " + productId);
            } else {
                System.out.println("상품을 찾을 수 없음: ID = " + productId);
            }
            return product;
        } catch (Exception e) {
            System.err.println("상품 조회 중 오류: " + e.getMessage());
            throw new RuntimeException("상품 조회에 실패했습니다.", e);
        }
    }
    

	@Override
	public Product save(Product product) {
	    try {
	        // 필수값 검증
	        validateProduct1(product);
	        
	        // 새로운 Product 객체 생성 - ID는 null로 유지
	        Product newProduct = Product.builder()
	            .productName(product.getProductName())
	            .productImg(product.getProductImg() != null ? product.getProductImg() : "")
	            .productPrice(product.getProductPrice())
	            .delfee(product.getDelfee() > 0 ? product.getDelfee() : 0)
	            .productQuantity(product.getProductQuantity())
	            .productContent(product.getProductContent() != null ? product.getProductContent() : "")
	            // productId는 설정하지 않음 - null 상태로 유지하여 시퀀스가 자동 할당
	            .build();
	        
	        System.out.println("저장 전 ID 상태: " + newProduct.getProductId()); // null이어야 함
	        
	        Product savedProduct = productRepository.save(newProduct);
	        System.out.println("상품 등록 성공: " + savedProduct.getProductName() + " (새 ID: " + savedProduct.getProductId() + ")");
	        return savedProduct;
	        
	    } catch (DataIntegrityViolationException e) {
	        System.err.println("PRIMARY KEY 제약 조건 위반: " + e.getMessage());
	        throw new RuntimeException("상품 ID 중복 오류입니다. 시스템 관리자에게 문의하세요. 상세: " + e.getRootCause().getMessage());
	        
	    } catch (IllegalArgumentException e) {
	        System.err.println("유효성 검사 오류: " + e.getMessage());
	        throw e;
	    } catch (Exception e) {
	        System.err.println("예상치 못한 오류: " + e.getMessage());
	        e.printStackTrace();
	        throw new RuntimeException("상품 등록에 실패했습니다: " + e.getMessage());
	    }
	}
	
	// 유효성 검사 메소드
	private void validateProduct1(Product product) {
	    if (product.getProductName() == null || product.getProductName().trim().isEmpty()) {
	        throw new IllegalArgumentException("상품명은 필수입니다.");
	    }
	    if (product.getProductName().length() > 100) {
	        throw new IllegalArgumentException("상품명은 100자를 초과할 수 없습니다.");
	    }
	    if (product.getProductPrice() < 0) {
	        throw new IllegalArgumentException("상품 가격은 0 이상이어야 합니다.");
	    }
	    if (product.getProductQuantity() < 0) {
	        throw new IllegalArgumentException("상품 수량은 0 이상이어야 합니다.");
	    }
	}

    @Override
    public Product update(int productId, Product product) {
        try {
            // 기존 상품 존재 확인
            Optional<Product> existingProductOpt = productRepository.findById(productId);
            if (!existingProductOpt.isPresent()) {
                throw new IllegalArgumentException("수정할 상품을 찾을 수 없습니다. ID: " + productId);
            }
            
            Product existingProduct = existingProductOpt.get();
            
            // 업데이트할 필드들 설정
            updateProductFields(existingProduct, product);
            
            Product updatedProduct = productRepository.save(existingProduct);
            return updatedProduct;
            
        } catch (DataIntegrityViolationException e) {
            System.err.println("상품 수정 시 데이터 무결성 제약 조건 위반: " + e.getMessage());
            throw new RuntimeException("상품 수정 중 데이터베이스 제약 조건에 위반되었습니다.");
        } catch (IllegalArgumentException e) {
            System.err.println("상품 수정 유효성 검사 오류: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("상품 수정 중 오류: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("상품 수정에 실패했습니다.", e);
        }
    }
    
    @Override
    public void delete(int productId) {
        try {
            // 상품 존재 확인
            if (!productRepository.existsById(productId)) {
                throw new IllegalArgumentException("삭제할 상품을 찾을 수 없습니다. ID: " + productId);
            }
            
            productRepository.deleteById(productId);
            System.out.println("상품 삭제 성공: ID = " + productId);
            
        } catch (DataIntegrityViolationException e) {
            System.err.println("상품 삭제 시 데이터 무결성 제약 조건 위반: " + e.getMessage());
            throw new RuntimeException("다른 테이블에서 참조하고 있어 삭제할 수 없습니다.");
        } catch (IllegalArgumentException e) {
            System.err.println("상품 삭제 유효성 검사 오류: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("상품 삭제 중 오류: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("상품 삭제에 실패했습니다.", e);
        }
    }
    
    // 유효성 검사 메소드
    private void validateProduct(Product product) {
        if (product.getProductName() == null || product.getProductName().trim().isEmpty()) {
            throw new IllegalArgumentException("상품명은 필수입니다.");
        }
        if (product.getProductName().length() > 100) {
            throw new IllegalArgumentException("상품명은 100자를 초과할 수 없습니다.");
        }
        if (product.getProductPrice() < 0) {
            throw new IllegalArgumentException("상품 가격은 0 이상이어야 합니다.");
        }
        if (product.getProductQuantity() < 0) {
            throw new IllegalArgumentException("상품 수량은 0 이상이어야 합니다.");
        }
    }
    
    
    // 상품 필드 업데이트 메소드
    private void updateProductFields(Product existingProduct, Product newProduct) {
        if (newProduct.getProductName() != null && !newProduct.getProductName().trim().isEmpty()) {
            if (newProduct.getProductName().length() > 100) {
                throw new IllegalArgumentException("상품명은 100자를 초과할 수 없습니다.");
            }
            existingProduct.setProductName(newProduct.getProductName());
        }
        
        if (newProduct.getProductPrice() >= 0) {
            existingProduct.setProductPrice(newProduct.getProductPrice());
        }
        
        if (newProduct.getProductQuantity() >= 0) {
            existingProduct.setProductQuantity(newProduct.getProductQuantity());
        }
        
        if (newProduct.getDelfee() >= 0) {
            existingProduct.setDelfee(newProduct.getDelfee());
        }
        
        if (newProduct.getProductImg() != null) {
            String img = newProduct.getProductImg().length() > 500 ? 
                        newProduct.getProductImg().substring(0, 500) : 
                        newProduct.getProductImg();
            existingProduct.setProductImg(img);
        }
        
        if (newProduct.getProductContent() != null) {
            String content = newProduct.getProductContent().length() > 4000 ? 
                            newProduct.getProductContent().substring(0, 4000) : 
                            newProduct.getProductContent();
            existingProduct.setProductContent(content);
        }
    }

	@Override
	public List<Product> findAllOrderByIdDesc() {
		// productId 기준 내림차순 정렬 (최신 등록순)
        return productRepository.findAllOrderByIdDesc();
	}

	@Override
	public Page<Product> findAll(Pageable pageable) {
		return productRepository.findAll(pageable);
	}
}