package pl.com.wikann.springboot.utils;

// Klasa reprezentująca zmienną
class Variable {
    float value;

    public Variable(float value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return Float.toString(value);
    }
}