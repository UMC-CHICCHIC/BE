package chic_chic.spring.domain.repository;

import chic_chic.spring.domain.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.*;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    @Query("select b from Brand b where lower(b.name) = lower(:name)")
    Optional<Brand> findByNameIgnoreCase(String name);
}