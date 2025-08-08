package chic_chic.spring.service.ScrapService;

import chic_chic.spring.domain.Member;
import chic_chic.spring.domain.Product;
import chic_chic.spring.domain.Scrap;
import chic_chic.spring.domain.repository.MemberRepository;
import chic_chic.spring.domain.repository.ProductRepository;
import chic_chic.spring.domain.repository.ScrapRepository;
import chic_chic.spring.web.dto.product.ProductListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScrapServiceImpl implements ScrapService {

    private final ScrapRepository scrapRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    @Override
    public void addScrap(Long memberId, Long productId) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();

        if (scrapRepository.existsByMemberAndProduct(member, product)) {
            throw new IllegalStateException("이미 스크랩한 향수입니다.");
        }

        scrapRepository.save(Scrap.builder()
                .member(member)
                .product(product)
                .build());
    }

    @Override
    public void removeScrap(Long memberId, Long productId) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();

        scrapRepository.deleteByMemberAndProduct(member, product);
    }

    @Override
    public List<ProductListResponse> getScraps(Long memberId) {
        return scrapRepository.findByMemberId(memberId)
                .stream()
                .map(scrap -> ProductListResponse.from(scrap.getProduct()))
                .toList();
    }
}

