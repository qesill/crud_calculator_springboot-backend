package pl.com.wikann.springboot.service;

import pl.com.wikann.springboot.utils.MathExpressionCalculator;

public class CalculationService {
    public Float calculateResult(String equation) {
        return MathExpressionCalculator.calculateResult(equation);
    }
}