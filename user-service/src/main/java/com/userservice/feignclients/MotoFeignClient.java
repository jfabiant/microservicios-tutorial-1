package com.userservice.feignclients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.userservice.domains.Moto;

@FeignClient(name = "moto-service", path = "/motos")
public interface MotoFeignClient {
	
	@GetMapping("/users/{userId}")
	List<Moto> motoList(@PathVariable("userId") Long userId);
	
	@PostMapping("/")
	Moto save(Moto moto);

}
