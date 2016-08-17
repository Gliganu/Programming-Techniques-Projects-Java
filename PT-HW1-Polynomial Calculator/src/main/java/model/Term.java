package model;

/**
 * Created by gliga on 2/28/2015.
 */

/**
 * Basic class which represents a monome of a polynomial
 */
public class Term implements Comparable<Term>{

    //the coefficient of that term
    private double coefficient;

    //the degree of that term
    private int degree;

    public Term(double coefficient, int degree) {
        this.coefficient = coefficient;
        this.degree = degree;
    }

    public Term() {

    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Term term = (Term) o;

        if (Double.compare(term.coefficient, coefficient) != 0) return false;
        if (degree != term.degree) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(coefficient);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + degree;
        return result;
    }

    @Override
    public int compareTo(Term otherTerm) {

        return ((Integer) otherTerm.degree).compareTo(degree);
    }

    /**
     * Used for printing purposes, trims the double to maximum 2 decimals
     * @return
     */
    public double getNormalizedCoefficient(){
        return Math.floor(coefficient * 100) / 100;
    }


    @Override
    public String toString() {
        if(coefficient % 1 == 0){

            int printableCoefficient = (int) coefficient;
            return  Math.abs(printableCoefficient)+"*x^"+degree+"";
        }else{
            return  Math.abs(getNormalizedCoefficient())+"*x^"+degree+"";
        }

    }
}
