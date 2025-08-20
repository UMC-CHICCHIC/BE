package chic_chic.spring.domain.repository;

import chic_chic.spring.domain.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    // 홈 인기상품용
    List<Product> findTop4ByOrderByItemRatingDesc();

    // 이름 부분일치에서 제일 먼저 매칭되는 1개
    Optional<Product> findFirstByNameContainingIgnoreCase(String name);

    //정확히 같은 이름 1개가 우선이면 이 메서드도 같이 두고 서비스에서 우선 사용
    Optional<Product> findFirstByNameIgnoreCase(String name);
}