package com.example.car;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.car.domain.Car;
import com.example.car.domain.CarRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTests {

	@Autowired
	private TestRestTemplate testRestTemplate;
	
    @Autowired
    private CarRepository repository;

    @After
    public void resetDb() {
        repository.deleteAll();
    }

	@Test
	public void getCar_WithName_ReturnsCar() {
		// Arrange
		createTestCar("prius", "hybrid");
		
		//Act
		ResponseEntity<Car> responseEntity = this.testRestTemplate.getForEntity("/cars/{name}", Car.class, "prius");
		
		// Assert
		Car car = responseEntity.getBody();
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(car.getName()).isEqualTo("prius");
		assertThat(car.getType()).isEqualTo("hybrid");
	}

    private void createTestCar(String name, String type) {
    	Car car = new Car(name, type);
        repository.saveAndFlush(car);
}
	
}
