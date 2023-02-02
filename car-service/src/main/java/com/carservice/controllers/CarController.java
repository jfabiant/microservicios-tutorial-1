package com.carservice.controllers;

import com.carservice.domain.Car;
import com.carservice.services.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CarController {

    private final CarService carService;
    
    public CarController(CarService carService) {
		super();
		this.carService = carService;
	}
    
	@GetMapping("/cars")
    public ResponseEntity<?> getUserList(){
        List<Car> carList = this.carService.getAll();
        if(carList.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(carList);
    }
	
    @GetMapping("/cars/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id){
        Car car = this.carService.getCarById(id);
        if(car == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(car);
    }

    @PostMapping("/cars")
    public ResponseEntity<?> saveUser(@RequestBody Car car){
    	System.out.println("==== PostMapping Cars ====");
    	System.out.println(car.toString());
        this.carService.save(car);
        return ResponseEntity.ok("Carro guardado correctamente");
    }

    @GetMapping("/cars/users/{userId}")
    public ResponseEntity<List<Car>> getCarsByUser(@PathVariable("userId") Long userId){
        List<Car> carList = this.carService.findByUser(userId);
        if(carList.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(carList);
    }

}
