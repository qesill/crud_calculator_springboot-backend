package pl.com.wikann.springboot.utils;

// Klasa reprezentująca operator dzielenia
class DivideOperator extends BiOperator {
    @Override
    float apply(float left, float right) {
        if (right != 0) {
            return left / right;
        } else {
            throw new ArithmeticException("Division by zero");
        }
    }

    @Override
    public String toString() {
        return "/";
    }
}