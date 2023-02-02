package com.carservice.services;

import com.carservice.domain.Car;
import com.carservice.repositories.ICarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    private final ICarRepository carRepository;
    
    public CarService(ICarRepository carRepository) {
		super();
		this.carRepository = carRepository;
	}

	public List<Car> getAll(){
        return this.carRepository.findAll();
    }

    public Car getCarById(Long id){
        return carRepository.findById(id).orElse(null);
    }

    public Car save(Car car){
    	System.out.println("==== Service Car ====");
    	System.out.println(car.toString());
        return this.carRepository.save(car);
    }

    public List<Car> findByUser(Long userId){
        return this.carRepository.findByUserId(userId);
    }

}
