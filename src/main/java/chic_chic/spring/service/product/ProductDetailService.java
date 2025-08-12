package chic_chic.spring.service.product;

import chic_chic.spring.domain.Brand;
import chic_chic.spring.domain.Product;
import chic_chic.spring.domain.repository.BrandRepository;
import chic_chic.spring.domain.repository.ProductRepository;
import chic_chic.spring.web.dto.ProductDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductDetailService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository; // [추가]

    public ProductDetailResponse getProductDetail(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 제품입니다."));

        // product.brand 문자열과 brand 테이블 name 매칭 (대소문자/공백 보정)
        String brandName = product.getBrand() == null ? "" : product.getBrand().trim();
        String brandUrl = brandRepository.findByNameIgnoreCase(brandName)
                .map(Brand::getBrandUrl)
                .orElse(null);

        // brandUrl 주입
        return ProductDetailResponse.from(product, brandUrl);
    }
}
