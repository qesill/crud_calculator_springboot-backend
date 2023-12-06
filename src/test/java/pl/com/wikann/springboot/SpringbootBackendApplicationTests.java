package pl.com.wikann.springboot;

import org.junit.jupiter.api.Test;
import pl.com.wikann.springboot.utils.MathExpressionCalculator;

import static org.junit.jupiter.api.Assertions.*;

class MathExpressionCalculatorTest {

	@Test
	void testCalculateResult() {
		// Test dla poprawnego równania
		assertEquals(10.0f, MathExpressionCalculator.calculateResult("5+5"), 0.0001f);

		// Test dla równania z ułamkiem
		assertEquals(2.5f, MathExpressionCalculator.calculateResult("5/2"), 0.0001f);

		// Test dla równania z różnymi operatorami
		assertEquals(15.0f, MathExpressionCalculator.calculateResult("5*3"), 0.0001f);

		// Test dla równania z wieloma operatorami
		//assertEquals(11.0f, MathExpressionCalculator.calculateResult("5+3*2"), 0.0001f);

		// Test dla równania z nawiasami
		assertEquals(14.0f, MathExpressionCalculator.calculateResult("2+((2*2)*(1+2))"), 0.0001f);

		// Test dla równania z nawiasami
		//assertEquals(16.0f, MathExpressionCalculator.calculateResult("(5+3)*(1+1)"), 0.0001f);

		// Test dla równania liczbami ujemnymi
		//assertEquals(4.0f, MathExpressionCalculator.calculateResult("2--2"), 0.0001f);

		// Test dla równania liczbami zmiennoprzecinkowymi
		//assertEquals(5.0f, MathExpressionCalculator.calculateResult("2.5+2.5"), 0.0001f);
		// Test dla równania białymi znakami
		//assertEquals(0.0f, MathExpressionCalculator.calculateResult("1 + 1  -2"), 0.0001f);
	}
}