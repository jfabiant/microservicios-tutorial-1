package com.carservice.repositories;

import com.carservice.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICarRepository extends JpaRepository<Car, Long> {
    List<Car> findByUserId(Long userId);
}
