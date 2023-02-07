package com.userservice.controllers;

import com.userservice.domains.Car;
import com.userservice.domains.Moto;
import com.userservice.domains.Usuario;
import com.userservice.services.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;
    
    public UserController(UserService userService) {
		super();
		this.userService = userService;
	}
    
	@GetMapping("/users")
    public ResponseEntity<?> getUserList(){
        List<Usuario> userList = this.userService.getUserList();
        if(userList.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(userList);
    }
	
    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id){
        Usuario user = this.userService.getUserById(id);
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/users")
    public ResponseEntity<?> saveUser(@RequestBody Usuario user){
        this.userService.save(user);
        return ResponseEntity.ok("Usuario guardado correctamente");
    }

    @GetMapping("/users/{userId}/cars")
    @CircuitBreaker(name = "carsCB", fallbackMethod = "fallBackGetCars")
    public ResponseEntity<?> getCars(@PathVariable Long userId){
        return ResponseEntity.ok(this.userService.getCarsRestTemplate(userId));
    }
    
    @GetMapping("/users/{userId}/motos")
    @CircuitBreaker(name = "motosCB", fallbackMethod = "fallBackGetMotos")
    public ResponseEntity<?> getMotoList(@PathVariable Long userId){
        return ResponseEntity.ok(this.userService.getMotosRestTemplate(userId));
    }

    @PostMapping("/users/{userId}/cars")
    @CircuitBreaker(name = "carsCB", fallbackMethod = "fallBackSaveCar")
    public ResponseEntity<?> saveNewCar(@PathVariable Long userId,
    		@RequestBody Car car) {
    	return ResponseEntity.ok(this.userService.saveCar(userId, car));
    }
    
    @PostMapping("/users/{userId}/motos")
    @CircuitBreaker(name = "motosCB", fallbackMethod = "fallBackSaveMoto")
    public ResponseEntity<?> getCarListByUserId(@PathVariable Long userId,
    		@RequestBody Moto moto){
    	return ResponseEntity.ok(this.userService.saveMoto(userId, moto));
    }
    
    
    private ResponseEntity<?> fallBackGetCars(@PathVariable Long userId,
    		RuntimeException ex){
    	System.out.println("=== Error fallBackGetCars() ===");
    	System.out.println(ex.getMessage());
    	return ResponseEntity.ok("Los carros del usuario "+userId+""
    			+ " se encuentran en mantenimiento, por favor espere 24 hrs.");
    }
    private ResponseEntity<?> fallBackGetMotos(@PathVariable Long userId,
    		RuntimeException ex){
    	System.out.println("=== Error fallBackGetMotos() ===");
    	System.out.println(ex.getMessage());
    	return ResponseEntity.ok("Las motos del usuario "+userId+""
    			+ " se encuentran en mantenimiento, por favor espere 24 hrs.");
    }
    private ResponseEntity<?> fallBackSaveCar(@PathVariable Long userId,
    		@RequestBody Car car, RuntimeException ex){
    	System.out.println("=== Error fallBackSaveCar() ===");
    	System.out.println(ex.getMessage());
    	return ResponseEntity.ok("No se puede guardar un carro porque el usuario"
    			+ " con id "+userId+" no tiene suficiente dinero.");
    }
    private ResponseEntity<?> fallBackSaveMoto(@PathVariable Long userId,
    		@RequestBody Moto moto, RuntimeException ex){
    	System.out.println("=== Error fallBackSaveMoto() ===");
    	System.out.println(ex.getMessage());
    	return ResponseEntity.ok("No se puede guardar una moto porque el usuario"
    			+ " con id "+userId+" no tiene suficiente dinero.");
    }
    
    
    
}
