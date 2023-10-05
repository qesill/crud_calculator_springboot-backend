package pl.com.wikann.springboot.model;

import jakarta.persistence.*;
import pl.com.wikann.springboot.utils.MathExpressionCalculator;

@Entity
@Table(name = "calculations")

public class Calculation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "equation")
    private String equation;
    @Column(name = "result")
    private Float result;

    public Calculation() {

    }

    public Calculation(String equation, Float result) {
        this.equation = equation;
        this.result = MathExpressionCalculator.calculateResult(equation);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEquation() {
        return equation;
    }

    public void setEquation(String equation) {
        this.equation = equation;
        this.result = MathExpressionCalculator.calculateResult(equation);
    }

    public Float getResult() {
        return result;
    }

    public void setResult(Float result) {
        this.result = result;
    }
}