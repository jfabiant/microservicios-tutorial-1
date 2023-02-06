package com.userservice.services;

import com.userservice.domains.Car;
import com.userservice.domains.Moto;
import com.userservice.domains.Usuario;
import com.userservice.dto.UserResponse;
import com.userservice.feignclients.CarFeignClient;
import com.userservice.feignclients.MotoFeignClient;
import com.userservice.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
	
    private final RestTemplate restTemplate;
    private final IUserRepository userRepository;
    private final CarFeignClient carFeignClient;
    private final MotoFeignClient motoFeignClient;
    
    public UserService(RestTemplate restTemplate, IUserRepository userRepository,
    		CarFeignClient carFeignClient,
    		MotoFeignClient motoFeignClient) {
		super();
		this.restTemplate = restTemplate;
		this.userRepository = userRepository;
		this.carFeignClient = carFeignClient;
		this.motoFeignClient = motoFeignClient;
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
    
    public List<Car> getCarsRestTemplate(Long userId){
        List<Car> cars = restTemplate.getForObject("http://car-service/cars/users/"+userId,
                List.class);
        return cars;
    }
    
    public List<Moto> getMotosRestTemplate(Long userId){
        List<Moto> motos = restTemplate.getForObject("http://moto-service/motos/users/"+userId,
                List.class);
        return motos;
    }
    
    public Car saveCar(Long usuarioId, Car car) {
    	car.setUserId(usuarioId);
    	return this.carFeignClient.save(car);
    }
    
    public Moto saveMoto(Long userId, Moto moto) {
    	moto.setUserId(userId);
    	return this.motoFeignClient.save(moto);
    }
    
    public Map<String, Object> all(Long userId){
    	var carList = this.carFeignClient.carList(userId);
    	var motoList = this.motoFeignClient.motoList(userId);
    	
    	Map<String, Object> results = new HashMap<>();
    	results.put("carList", carList);
    	results.put("motoList", motoList);
    	
    	return results;
    }

}
