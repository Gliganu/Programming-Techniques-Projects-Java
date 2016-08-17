import controller.Calculator;
import model.Polynomial;
import model.PolynomialUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by user on 3/9/2015.
 */
public class PolynomialTests{


    @Test
    public void testAdd(){

        Polynomial p1 = PolynomialUtils.getPolynomialFromText("3*x^2 - 2*x^1 + 4");
        Polynomial p2 = PolynomialUtils.getPolynomialFromText("4*x^1 + 2");
        Polynomial sum = PolynomialUtils.getPolynomialFromText("3*x^2 + 2*x^1 + 6");

        Polynomial calculatedSum = Calculator.add(p1,p2);

        assertEquals("Sum is:"+sum+" and calculatedSum is: "+calculatedSum,sum, calculatedSum);
    }

    @Test
    public void testSubstract(){

        Polynomial p1 = PolynomialUtils.getPolynomialFromText(" - 4*x^2 - 5*x^1 + 1");
        Polynomial p2 = PolynomialUtils.getPolynomialFromText("1*x^1 + 1");
        Polynomial difference = PolynomialUtils.getPolynomialFromText("- 4*x^2 - 6");

        Polynomial calculatedDifference = Calculator.substract(p1,p2);

        assertEquals("Difference is:"+difference+" and calculatedDifference is: "+calculatedDifference,difference, calculatedDifference);
    }


    @Test
    public void testMultiply(){

        Polynomial p1 = PolynomialUtils.getPolynomialFromText(" - 3*x^2 + 2*x^1 + 2");
        Polynomial p2 = PolynomialUtils.getPolynomialFromText(" - 5*x^1 - 4");
        Polynomial multiplication = PolynomialUtils.getPolynomialFromText("15*x^3 + 2*x^2 - 18*x^1 - 8");

        Polynomial calculatedMultiplication = Calculator.multiply(p1, p2);

        assertEquals("Multiplication is:"+multiplication+" and calculatedMultiplication is: "+calculatedMultiplication,multiplication, calculatedMultiplication);
    }


    @Test
    public void testDivision(){

        Polynomial p1 = PolynomialUtils.getPolynomialFromText("x^3+3*x^2+3*x^1+1");
        Polynomial p2 = PolynomialUtils.getPolynomialFromText("x^1+1");
        Polynomial division = PolynomialUtils.getPolynomialFromText("1*x^2 + 2*x^1 + 1 ");

        Polynomial calculatedDivision = Calculator.simpleDivide(p1, p2);

        assertEquals("Division is:"+division+" and calculatedDivision is: "+calculatedDivision,division, calculatedDivision);
    }

    @Test
    public void testIntegration(){

        Polynomial p1 = PolynomialUtils.getPolynomialFromText("2*x^1+3");
        Polynomial integration = PolynomialUtils.getPolynomialFromText("1*x^2 + 3*x^1");

        Polynomial calculatedIntegration = Calculator.integrate(p1);

        assertEquals("Integration is:"+integration+" and calculatedIntegration is: "+calculatedIntegration,integration, calculatedIntegration);
    }

    @Test
    public void testDifferentiation(){

        Polynomial p1 = PolynomialUtils.getPolynomialFromText("1*x^2 - 2*x^1 - 2");
        Polynomial differentiation = PolynomialUtils.getPolynomialFromText("2*x^1 - 2");

        Polynomial calculatedDifferentation = Calculator.differentiate(p1);

        assertEquals("Differentiation is:"+differentiation+" and calculatedDifferentation is: "+calculatedDifferentation,differentiation, calculatedDifferentation);
    }

    @Test
    public void testCalculateValue(){

        Polynomial p1 = PolynomialUtils.getPolynomialFromText(" - 4*x^2 - 1*x^1 + 3");
        Polynomial valuePolynomial = new Polynomial(2);

        Polynomial result = PolynomialUtils.getPolynomialFromText(" - 15*x^0");

        Polynomial calculatedResult = Calculator.getValueOfPolynomial(p1,valuePolynomial);

        assertEquals("Result is:"+result+" and calculatedResult is: "+calculatedResult,result, calculatedResult);
    }

    @Test
    public void testMultiplyByScalar(){

        Polynomial p1 = PolynomialUtils.getPolynomialFromText(" 3*x^2 + 2*x^1 - 2");
        Polynomial valuePolynomial = new Polynomial(3);

        Polynomial result = PolynomialUtils.getPolynomialFromText(" 9*x^2 + 6*x^1 - 6");

        Polynomial calculatedResult = Calculator.getValueOfPolynomial(p1,valuePolynomial);

        assertEquals("Result is:"+result+" and calculatedResult is: "+calculatedResult,result, calculatedResult);
    }




}
