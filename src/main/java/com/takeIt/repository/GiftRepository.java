package com.takeIt.repository;

import com.takeIt.entity.Gift;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface GiftRepository extends JpaRepository<Gift, Long>, JpaSpecificationExecutor<Gift> {
    List<Gift> findAllByStatus(int status);

    List<Gift> findByStatusAndCity_IdAndDistrict_Id(int status, long ct_Id, long d_Id);

    Page<Gift> findByCategory_IdAndStatus(long id,int status,Pageable pageable);
}
