package com.java.dto;

import com.java.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private int product_id;
    private String productName;
    private int productPrice;
    private String productImage;
    private int productQuantity;
    private String productContent;
    private int delfee;
    
    public static ProductDto from(Product product) {
        ProductDto dto = new ProductDto();
        dto.setProduct_id(product.getProductId());
        dto.setProductName(product.getProductName());
        dto.setProductPrice(product.getProductPrice());
        dto.setProductImage(product.getProductImg());
        dto.setProductQuantity(product.getProductQuantity());
        dto.setProductContent(product.getProductContent());
        dto.setDelfee(product.getDelfee());
        return dto;
    }
}