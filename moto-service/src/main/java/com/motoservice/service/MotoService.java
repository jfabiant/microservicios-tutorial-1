package com.motoservice.service;

import com.motoservice.domain.Moto;
import com.motoservice.repository.IMotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MotoService {

    private final IMotoRepository motoRepository;
    
    public MotoService(IMotoRepository motoRepository) {
		super();
		this.motoRepository = motoRepository;
	}

	public List<Moto> getAll(){
        return this.motoRepository.findAll();
    }

    public Moto getMotoById(Long id){
        return this.motoRepository.findById(id).orElse(null);
    }

    public Moto save(Moto moto){
        return this.motoRepository.save(moto);
    }

    public List<Moto> findByUserId(Long userId){
        return this.motoRepository.findByUserId(userId);
    }

}
