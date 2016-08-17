package model;

import java.util.*;

/**
 * Created by gliga on 3/1/2015.
 */

/**
 * Helper class which provides a range of methods which help at polynomial processing
 */
public class PolynomialUtils {


    /**
     * Inserting a term into an existing term list of an polynomial
     * @param termList the existing term list
     * @param insertedTerm the item to be inserted
     */
    public static void insertTermIntoTermList(List<Term> termList, Term insertedTerm) {

        for (Term term : termList) {
            if (term.getDegree() == insertedTerm.getDegree()) {
                term.setCoefficient(term.getCoefficient() + insertedTerm.getCoefficient());
                return;
            }
        }

        termList.add(insertedTerm);
        Collections.sort(termList);
    }

    /**
     * Retrieves a totally random polynomial. Used for testing & debugging purposes
     * @param size the size of the polynomial to be retrieved
     * @return the randomly created polynomial
     */
    public static Polynomial getRandomPolynomial(int size) {

        Polynomial polynomial = new Polynomial();
        List<Term> termList = new ArrayList<Term>();
        Random random = new Random();

        for (int i = 0; i < size; i++) {
            termList.add(new Term(random.nextInt(10) - 5, i));
        }
        polynomial.setTermList(termList);

        return polynomial;
    }

    /**
     * Method used at the process of polynomial division.
     * @param firstNumeratorTerm the highest term in the numerator
     * @param firstDenominatorTerm the highest term in the denominator
     * @return the result
     */
    public static Term getDivisionTerm(Term firstNumeratorTerm, Term firstDenominatorTerm) {

        double finalCoefficient = firstNumeratorTerm.getCoefficient() / firstDenominatorTerm.getCoefficient();
        int finalDegree = firstNumeratorTerm.getDegree() - firstDenominatorTerm.getDegree();

        return new Term(finalCoefficient, finalDegree);

    }

    /**
     * Calculates the degree of a give polynomial
     * @param polynomial the target polynomial
     * @return the requested degree
     */
    public static int getDegreeOfPolynomial(Polynomial polynomial) {

        if (polynomial.getTermList().isEmpty()) {
            return -1;
        }

        return polynomial.getTermList().get(0).getDegree();
    }

    /**
     * If by any chance, after certain operations, certain polynomials end up with terms which have as coefficient the number zero, they have to be eliminated.
     * This method serves that very purpsose
     * @param polynomial the target polynomial
     */
    public static void eliminateZeroCoefficientTerms(Polynomial polynomial) {

        Iterator<Term> termIterator = polynomial.getTermList().iterator();

        while (termIterator.hasNext()) {

            Term currentTerm = termIterator.next();

            if (currentTerm.getCoefficient() == 0) {
                termIterator.remove();
            }
        }

    }

    /**     * Check to see if the polynomial is basically just a valid number.
     * @param polynomial the target polynomial
     * @return whether it is a valid numberw or not
     */
    public static boolean isScalarPolynomial(Polynomial polynomial) {

        return polynomial.getTermList() != null && polynomial.getTermList().size() == 1 && polynomial.getTermList().get(0).getDegree() == 0;

    }


    /**
     * Complex method which parses a given string and transforms it into an Polynomial object
     * @param text the input string
     * @return the final Polynomial object
     */
    public static Polynomial getPolynomialFromText(String text) {

        if (text.length() == 0) {
            throw new IllegalArgumentException("Polynom is empty");
        }

        text = text.replace(" ", "");

        List<Term> termList = new ArrayList<Term>();
        Term currentTerm = new Term();

        PolynomialBuildPhase currentBuildPhase = PolynomialBuildPhase.COEFFICIENT_PHASE;

        boolean shouldReverseCoefficient = false;

        char previousChar = text.charAt(0);
        int currentCoefficient = 0;
        int currentDegree = 0;

        if (text.length() == 1) {
            if (previousChar == 'x') {
                termList.add(new Term(1, 1));
            } else if (previousChar > 47 && previousChar < 58) {
                termList.add(new Term(previousChar - '0', 0));
            }else{
                throw new IllegalArgumentException("Character '" + previousChar + "' is not an acceptable input");
            }
            return new Polynomial(termList);
        }


        if (previousChar == 'x') {
            currentTerm.setCoefficient(1);
        } else if (previousChar == '-') {
            shouldReverseCoefficient = true;
        } else {
            currentCoefficient = previousChar - '0';
        }

        for (int i = 1; i < text.length(); i++) {
            char currentChar = text.charAt(i);

            //If the character is a number
            if (currentChar > 47 && currentChar < 58) {

                if (previousChar > 47 && previousChar < 58) {
                    switch (currentBuildPhase) {

                        case COEFFICIENT_PHASE:
                            currentCoefficient = currentCoefficient * 10 + (currentChar - '0');
                            break;
                        case DEGREE_PHASE:
                            currentDegree = currentDegree * 10 + (currentChar - '0');
                            break;
                        case SIGN_PHASE:
                            throw new IllegalArgumentException("Number after sign phase");
                    }
                    previousChar = currentChar;
                    continue;
                }

                switch (previousChar) {
                    case 'x':
                        throw new IllegalArgumentException("Number after x");

                    case '*':
                        throw new IllegalArgumentException("Number after *");

                    case '-':
                        currentCoefficient = currentCoefficient * 10 + (currentChar - '0');
                        break;

                    case '+':
                        currentCoefficient = currentCoefficient * 10 + (currentChar - '0');
                        break;

                    case '^':
                        currentDegree = currentDegree * 10 + (currentChar - '0');
                        break;

                    default:
                        throw new IllegalArgumentException("Character '" + currentChar + "' is not an acceptable input");
                }
                previousChar = currentChar;
                continue;
            }

            switch (currentChar) {
                case 'x':
                    if (text.charAt(i + 1) != '^') {
                        currentDegree = 1;
                    }

                    if (text.charAt(i - 1) != '*') {
                        currentCoefficient = 1;
                        currentTerm.setCoefficient(currentCoefficient);
                    }
                    break;

                case '*':
                    if (shouldReverseCoefficient) {
                        currentCoefficient *= -1;
                    }
                    currentTerm.setCoefficient(currentCoefficient);
                    currentBuildPhase = PolynomialBuildPhase.SIGN_PHASE;
                    break;

                case '-':
                    currentTerm.setDegree(currentDegree);

                    termList.add(currentTerm);

                    currentTerm = new Term();

                    currentCoefficient = 0;
                    currentDegree = 0;

                    currentBuildPhase = PolynomialBuildPhase.COEFFICIENT_PHASE;
                    shouldReverseCoefficient = true;
                    break;

                case '+':
                    currentTerm.setDegree(currentDegree);
                    termList.add(currentTerm);


                    currentTerm = new Term();

                    currentCoefficient = 0;
                    currentDegree = 0;

                    currentBuildPhase = PolynomialBuildPhase.COEFFICIENT_PHASE;
                    shouldReverseCoefficient = false;
                    break;

                case '^':
                    currentBuildPhase = PolynomialBuildPhase.DEGREE_PHASE;
                    break;

                default:
                    throw new IllegalArgumentException("Character '" + currentChar + "' is not an acceptable input");
            }

            previousChar = currentChar;

        }

        if(shouldReverseCoefficient){
            currentCoefficient*=-1;
        }
        termList.add(new Term(currentCoefficient, currentDegree));

        return new Polynomial(termList);
    }
}
