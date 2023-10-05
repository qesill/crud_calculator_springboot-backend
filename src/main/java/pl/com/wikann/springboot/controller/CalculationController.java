package pl.com.wikann.springboot.controller;
import pl.com.wikann.springboot.exception.ResourceNotFoundException;
import pl.com.wikann.springboot.model.Calculation;
import pl.com.wikann.springboot.repository.CalculationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
public class CalculationController {
    @Autowired
    private CalculationRepository calculationRepository;

    //get all calculations
    @GetMapping("/calculations")
    public List<Calculation> getAllCalculations(){
        return calculationRepository.findAll();
    }

    //create calculation rest api
    @PostMapping("/calculations")
    public Calculation createCalculation(@RequestBody Calculation calculation) {
        return calculationRepository.save(calculation);
    }

    // get calculation by id rest api
    @GetMapping("/calculations/{id}")
    public ResponseEntity<Calculation> getCalculationById(@PathVariable Long id) {
        Calculation calculation = calculationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Calculation not exist with id: "+id));
        return ResponseEntity.ok(calculation);
    }

    //update calculation rest api
    @PutMapping("/calculations/{id}")
    public ResponseEntity<Calculation> updateCalculation(@PathVariable Long id, @RequestBody Calculation calculationDetails){

        Calculation calculation = calculationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Calculation not exist with id: "+id));
        calculation.setEquation(calculationDetails.getEquation());
        calculation.setResult(calculationDetails.getResult());


        Calculation updatedCalculation = calculationRepository.save(calculation);
        return ResponseEntity.ok(updatedCalculation);
    }

    //delete calculation rest api
    @DeleteMapping("/calculations/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteCalculation(@PathVariable Long id){
        Calculation calculation = calculationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Calculation not exist with id: "+id));

        calculationRepository.delete(calculation);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}