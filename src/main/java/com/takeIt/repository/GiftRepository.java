package com.takeIt.repository;

import com.takeIt.entity.Gift;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface GiftRepository extends JpaRepository<Gift, Long>, JpaSpecificationExecutor<Gift> {
//    Page<Gift> findByStatus(Specification<Gift> specification, Pageable pageable, int status);
    Optional<Gift> findByIdAndStatus(long id, int status);
}
