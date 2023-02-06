package com.userservice.controllers;

import com.userservice.domains.Car;
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
        return ResponseEntity.ok(this.userService.getCars(userId));
    }
    
    @GetMapping("/users/{userId}/motos")
    @CircuitBreaker(name = "motosCB", fallbackMethod = "fallBackGetMotos")
    public ResponseEntity<?> getMotoList(@PathVariable Long userId){
        return ResponseEntity.ok(this.userService.getMotos(userId));
    }

    @PostMapping("/cars/{userId}")
    @CircuitBreaker(name = "carsCB", fallbackMethod = "fallBackSaveCar")
    public ResponseEntity<Car> saveNewCar(@PathVariable Long userId,
    		@RequestBody Car car) {
    	return ResponseEntity.ok(this.userService.saveCar(userId, car));
    }
    
    @GetMapping("/cars/{userId}")
    public ResponseEntity<List<Car>> getCarListByUserId(@PathVariable Long userId){
    	return ResponseEntity.ok(this.userService.getCarsByUserId(userId));
    }
    
    public ResponseEntity<?> fallBackGetCars(@PathVariable Long userId){
    	return ResponseEntity.ok("Los carros del usuario "+userId+" se encuentran en"
    			+ " mantenimiento, por favor espere 24hrs.");
    }
    
    public ResponseEntity<?> fallBackGetMotos(@PathVariable Long userId){
    	return ResponseEntity.ok("Las motos del usuario "+userId+" se encuentran en"
    			+ " mantenimiento, por favor espere 24hrs.");
    }
    
    public ResponseEntity<?> fallBackSaveCar(@PathVariable Long userId,
    		@RequestBody Car car){
    	return ResponseEntity.ok("No se puede guardar el carro del usuario "+userId+" "
    			+ "porque no tiene suficiente dinero en la tarjeta registrada.");
    }
    
}
