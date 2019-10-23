package com.takeIt.repository;

import com.takeIt.entity.Gift;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GiftRepository extends JpaRepository<Gift, Long> {
    List<Gift> findAllByStatus(int status);
}
