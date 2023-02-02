package com.userservice.feignclients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.userservice.domains.Car;

@FeignClient(name = "car-service", url = "http://localhost:5002", path = "/cars")
public interface CarFeignClient {
	
	@PostMapping("/")
	Car save(@RequestBody Car car);
	
	@GetMapping("/users/{userId}")
	List<Car> carList(@PathVariable("userId") Long userId);
	
}
