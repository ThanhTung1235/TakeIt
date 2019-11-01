package com.takeIt.repository;

import com.takeIt.entity.ExchangeRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExchangeRequestRepository extends JpaRepository<ExchangeRequest, Long> {
    ExchangeRequest findByStatus(int status);

    Page<ExchangeRequest> findByGift_Id(long id, Pageable pageable);

    Page<ExchangeRequest> findByAccount_Id(long id, Pageable pageable);
}
