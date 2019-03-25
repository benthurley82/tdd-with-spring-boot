package com.example.car.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car,String> {

	Car findByName(String name);

}
