package controller;


import model.OperationType;
import model.Polynomial;

/**
 * Created by user on 3/6/2015.
 */

/**
 * Controller class which links the View and the Model in the MVC Architecture. It's purpose is to return appropriate results based on user's choices
 */
public class Controller {

    /**
     * Performs a specific operation based on the input from the user
     * @param p1 the first term
     * @param p2 the second term
     * @param operationType the operation which the user wants to perform
     * @return the result type
     */
        public Polynomial doOperation(Polynomial p1, Polynomial p2, OperationType operationType){

            switch (operationType){

                case ADD:
                    return Calculator.add(p1,p2);
                case SUBSTRACT:
                    return Calculator.substract(p1,p2);
                case MULTIPLY:
                    return Calculator.multiply(p1,p2);
                case DIVIDE:
                    return Calculator.simpleDivide(p1,p2);
                case INTEGRATE:
                    return Calculator.integrate(p1);
                case DIFFERENTIATE:
                    return Calculator.differentiate(p1);
                case CALCULATE_VALUE:
                    return Calculator.getValueOfPolynomial(p1,p2);
                case MULTIPLY_WITH_SCALAR:
                    return Calculator.multiplyWithScalar(p1,p2);
                default:
                    return null;
            }

        }



}
