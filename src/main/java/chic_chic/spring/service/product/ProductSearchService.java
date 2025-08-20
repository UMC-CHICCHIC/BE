package chic_chic.spring.service.product;


import chic_chic.spring.web.dto.product.ProductListResponse;

public interface ProductSearchService {
    ProductListResponse findOneByName(String q);
}