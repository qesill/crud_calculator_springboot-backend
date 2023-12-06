package pl.com.wikann.springboot.utils;

public class MathExpressionCalculator {
    public static float calculateResult(String equation) {
        ExpressionTreeBuilder builder = new ExpressionTreeBuilder();
        return builder.calculate(equation);
    }
}