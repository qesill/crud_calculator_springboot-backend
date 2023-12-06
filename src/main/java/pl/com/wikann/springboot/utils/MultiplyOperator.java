package pl.com.wikann.springboot.utils;

// Klasa reprezentująca operator mnożenia
class MultiplyOperator extends BiOperator {
    @Override
    float apply(float left, float right) {
        return left * right;
    }

    @Override
    public String toString() {
        return "*";
    }
}
