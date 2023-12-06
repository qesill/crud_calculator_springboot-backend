package pl.com.wikann.springboot.utils;

class Variable {
    private float value;

    public Variable(float value) {
        this.value = value;
    }

    public float getValue() {
        return value;
    }

    @Override
    public String toString() {
        return Float.toString(value);
    }
}