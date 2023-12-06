package pl.com.wikann.springboot.utils;

// Abstrakcyjna klasa reprezentująca operator dwuargumentowy
abstract class BiOperator {
    abstract float apply(float left, float right); // Metoda abstrakcyjna, która będzie implementowana przez konkretne operatory
}
