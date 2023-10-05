package pl.com.wikann.springboot.repository;

import pl.com.wikann.springboot.model.Calculation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalculationRepository extends JpaRepository<Calculation, Long> {

}