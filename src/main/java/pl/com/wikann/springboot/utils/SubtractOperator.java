package pl.com.wikann.springboot.utils;

class SubtractOperator extends BiOperator {
    @Override
    float apply(float left, float right) {
        return left - right;
    }

    @Override
    public String toString() {
        return "-";
    }
}
