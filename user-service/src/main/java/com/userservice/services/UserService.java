package com.userservice.services;

import com.userservice.domains.Car;
import com.userservice.domains.Moto;
import com.userservice.domains.Usuario;
import com.userservice.dto.UserResponse;
import com.userservice.feignclients.CarFeignClient;
import com.userservice.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserService {
	
    private final RestTemplate restTemplate;

    private final IUserRepository userRepository;
    private final CarFeignClient carFeignClient;
    
    public UserService(RestTemplate restTemplate, IUserRepository userRepository,
    		CarFeignClient carFeignClient) {
		super();
		this.restTemplate = restTemplate;
		this.userRepository = userRepository;
		this.carFeignClient = carFeignClient;
	}

	public List<Usuario> getUserList(){
        return this.userRepository.findAll();
    }

    public Usuario getUserById(Long id){
        return this.userRepository.findById(id).orElse(null);
    }
    public void save(Usuario user){
        this.userRepository.save(user);
    }
    public List<Car> getCars(Long userId){
        List<Car> cars = restTemplate.getForObject("http://localhost:5002/cars/users/"+userId,
                List.class);
        return cars;
    }
    public List<Moto> getMotos(Long userId){
        List<Moto> motos = restTemplate.getForObject("http://localhost:5003/motos/users/"+userId,
                List.class);
        return motos;
    }
    
    public Car saveCar(Long usuarioId, Car car) {
    	car.setUserId(usuarioId);
    	return this.carFeignClient.save(car);
    }
    
    public List<Car> getCarsByUserId(Long userId){
    	return this.carFeignClient.carList(userId);
    }

}
