package chic_chic.spring.domain.repository;

import chic_chic.spring.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findTop4ByOrderByItemRatingDesc();
}

