package model;

/**
 * Created by gliga on 3/2/2015.
 */

/**
 * Types of operations which are available to the user to perform
 */
public enum OperationType {

    ADD("Add"),
    SUBSTRACT("Substract"),
    MULTIPLY("Multiply"),
    DIVIDE("Divide"),
    INTEGRATE("Integrate"),
    DIFFERENTIATE("Differentiate"),
    CALCULATE_VALUE("Calculate Value"),
    MULTIPLY_WITH_SCALAR("Multiply with scalar");

    private String text;

    private OperationType(String text){
        this.text=  text;
    }

    /**
     * By overriding toString method we can manipulate what gets to be displayed in the combo box of the UI
     * @return
     */
    @Override
    public String toString() {
        return text;
    }

}
