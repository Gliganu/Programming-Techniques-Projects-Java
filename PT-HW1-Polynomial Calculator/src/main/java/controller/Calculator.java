package controller;

import model.Polynomial;
import model.PolynomialUtils;
import model.Term;

import java.util.*;

/**
 * Created by gliga on 3/1/2015.
 */

/**
 * Class which performs different polynomial operations on given inputs
 */
public class Calculator {

    /**
     * Polynomial addition
     *
     * @param p1 first term
     * @param p2 second term
     * @return the addition polynomial
     */
    public static Polynomial add(Polynomial p1, Polynomial p2) {
        List<Term> sumTermList = new ArrayList<Term>();

        List<Term> biggerTermList;
        List<Term> smallerTermList;

        if (p1.getSize() > p2.getSize()) {
            biggerTermList = p1.getTermList();
            smallerTermList = p2.getTermList();
        } else {
            biggerTermList = p2.getTermList();
            smallerTermList = p1.getTermList();
        }

        for (Term biggerTerm : biggerTermList) {

            boolean added = false;

            for (Term smallerTerm : smallerTermList) {

                if (smallerTerm.getDegree() == biggerTerm.getDegree()) {

                    sumTermList.add(new Term(biggerTerm.getCoefficient() + smallerTerm.getCoefficient(), biggerTerm.getDegree()));
                    added=true;
                }

            }

            if(!added){
                    sumTermList.add(new Term(biggerTerm.getCoefficient(), biggerTerm.getDegree()));
            }
        }


        Polynomial finalPolynomial = new Polynomial(sumTermList);
        PolynomialUtils.eliminateZeroCoefficientTerms(finalPolynomial);

        return new Polynomial(sumTermList);

    }

    /**
     * Polynomial subtraction
     *
     * @param p1 first term
     * @param p2 second term
     * @return the subtraction polynomial
     */
    public static Polynomial substract(Polynomial p1, Polynomial p2) {

        Polynomial valuePolynomial = new Polynomial(-1);

        Polynomial inversePolynomial = multiplyWithScalar(p2, valuePolynomial);

        return add(p1, inversePolynomial);

    }

    /**
     * Polynomial multiplication
     *
     * @param p1 the first term
     * @param p2 the second term
     * @return the multiplication polynomial
     */
    public static Polynomial multiply(Polynomial p1, Polynomial p2) {

        List<Term> finalTermList = new ArrayList<Term>();
        List<Term> firstTermList = p1.getTermList();
        List<Term> secondTermList = p2.getTermList();

        for (Term firstTerm : firstTermList) {

            for (Term secondTerm : secondTermList) {

                Term finalTerm = new Term(firstTerm.getCoefficient() * secondTerm.getCoefficient(), firstTerm.getDegree() + secondTerm.getDegree());
                PolynomialUtils.insertTermIntoTermList(finalTermList, finalTerm);
            }
        }

        Polynomial finalPolynomial = new Polynomial(finalTermList);

        PolynomialUtils.eliminateZeroCoefficientTerms(finalPolynomial);
        return finalPolynomial;

    }

    /**
     * Polynomial division
     *
     * @param numerator   the first term
     * @param denominator the second term
     * @return the result of the division polynomial
     */
    public static Polynomial simpleDivide(Polynomial numerator, Polynomial denominator) {

        List<Term> finalTermList = new ArrayList<Term>();
        List<Term> denominatorTermList = denominator.getTermList();

        while (true) {

            Term firstNumeratorTerm = numerator.getTermList().get(0);
            Term firstDenominatorTerm = denominatorTermList.get(0);

            Term divisionTerm = PolynomialUtils.getDivisionTerm(firstNumeratorTerm, firstDenominatorTerm);

            finalTermList.add(divisionTerm);

            Polynomial multiplicationPolynomial = multiply(new Polynomial(divisionTerm), denominator);

            Polynomial substractionPolynomial = substract(numerator, multiplicationPolynomial);

            if (PolynomialUtils.getDegreeOfPolynomial(substractionPolynomial) == -1) {
                return new Polynomial(finalTermList);
            }

            numerator = substractionPolynomial;
            PolynomialUtils.eliminateZeroCoefficientTerms(numerator);

        }

    }

    /**
     * Polynomial integration
     *
     * @param polynomial the polynomial to be integrated
     * @return the result polynomial
     */
    public static Polynomial integrate(Polynomial polynomial) {
        List<Term> finalTermList = new ArrayList<Term>();

        double currentCoefficient;
        int currentDegree;
        for (Term term : polynomial.getTermList()) {

            currentCoefficient = term.getCoefficient();
            currentDegree = term.getDegree();

            finalTermList.add(new Term(currentCoefficient / (currentDegree + 1), currentDegree + 1));
        }

        return new Polynomial(finalTermList);
    }

    /**
     * Polynomial differentiation
     *
     * @param polynomial the polynomial to be differentiated
     * @return the result polynomial
     */
    public static Polynomial differentiate(Polynomial polynomial) {
        List<Term> finalTermList = new ArrayList<Term>();

        double currentCoefficient;
        int currentDegree;
        for (Term term : polynomial.getTermList()) {

            currentCoefficient = term.getCoefficient();
            currentDegree = term.getDegree();

            finalTermList.add(new Term(currentCoefficient * currentDegree, currentDegree - 1));
        }

        Polynomial finalPolynomial = new Polynomial(finalTermList);
        PolynomialUtils.eliminateZeroCoefficientTerms(finalPolynomial);

        return finalPolynomial;
    }

    /**
     * Calculates the total value of a polynomial for a given variable value
     *
     * @param polynomial      the polynomial to be calculated
     * @param valuePolynomial the value of the variable
     * @return the final value
     */
    public static Polynomial getValueOfPolynomial(Polynomial polynomial, Polynomial valuePolynomial) {

        if (!PolynomialUtils.isScalarPolynomial(valuePolynomial)) {
            throw new IllegalArgumentException("The value you introduced in not a scalar");
        }


        int sum = 0;
        int currentDegree;
        double currentCoefficient;

        int variable = (int) valuePolynomial.getTermList().get(0).getCoefficient();

        for (Term term : polynomial.getTermList()) {

            currentCoefficient = term.getCoefficient();
            currentDegree = term.getDegree();

            sum += currentCoefficient * Math.pow(variable, currentDegree);
        }

        return new Polynomial(sum);

    }

    /**
     * Multiplies each term of the polynomial with that value
     *
     * @param polynomial      the polynomial to be multiplied
     * @param valuePolynomial the value of the scalar
     * @return the result polynomial
     */
    public static Polynomial multiplyWithScalar(Polynomial polynomial, Polynomial valuePolynomial) {

        if (!PolynomialUtils.isScalarPolynomial(valuePolynomial)) {
            throw new IllegalArgumentException("The value you introduced in not a scalar");
        }
        int value = (int) valuePolynomial.getTermList().get(0).getCoefficient();

        if (value == 0) {
            return new Polynomial(new ArrayList<Term>());
        }
        if (value == 1) {
            return polynomial;
        }

        List<Term> finalTermList = new ArrayList<Term>();


        for (Term term : polynomial.getTermList()) {
            finalTermList.add(new Term(value * term.getCoefficient(), term.getDegree()));
        }

        return new Polynomial(finalTermList);
    }

}
