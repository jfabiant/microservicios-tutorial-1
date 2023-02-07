package com.userservice.domains;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Car {
	
    private String model;
    private String brand;
    private Long userId;
    
	public Car() {
		super();
	}
	
	public Car(String model, String brand, Long userId) {
		super();
		this.model = model;
		this.brand = brand;
		this.userId = userId;
	}
	
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	@Override
	public String toString() {
		return "Car [model=" + model + ", brand=" + brand + ", userId=" + userId + "]";
	}
	
}
