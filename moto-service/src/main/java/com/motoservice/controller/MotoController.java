package com.motoservice.controller;

import com.motoservice.domain.Moto;
import com.motoservice.service.MotoService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MotoController {

    private final MotoService motoService;
    
    public MotoController(MotoService motoService) {
		super();
		this.motoService = motoService;
	}
	@GetMapping("/motos")
    public ResponseEntity<?> getAllMotos(){
        List<Moto> motoList = this.motoService.getAll();
        if(motoList.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(motoList);
    }
    @GetMapping("/motos/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id){
        Moto moto = this.motoService.getMotoById(id);
        if(moto == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(moto);
    }

    @PostMapping("/motos")
    public ResponseEntity<?> post(@RequestBody Moto moto){
        this.motoService.save(moto);
        return ResponseEntity.ok("Moto guardado correctamente");
    }

    @GetMapping("/motos/users/{userId}")
    public ResponseEntity<List<Moto>> getMotosByUserId(@PathVariable("userId") Long userId){
        List<Moto> carList = this.motoService.findByUserId(userId);
        if(carList.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(carList);
    }



}
