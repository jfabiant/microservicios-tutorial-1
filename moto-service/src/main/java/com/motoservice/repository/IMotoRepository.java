package com.motoservice.repository;

import com.motoservice.domain.Moto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IMotoRepository extends JpaRepository<Moto, Long> {
    List<Moto> findByUserId(Long userId);
}
