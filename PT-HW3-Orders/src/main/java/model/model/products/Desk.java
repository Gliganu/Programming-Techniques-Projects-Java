package model.model.products;

import java.awt.*;

/**
 * Created by gliga on 3/29/2015.
 */
public class Desk extends Product {


    private int numberOfDrawers;

    /**
     * Simple constructor
     */
    public Desk(){
        super(ProductType.Furniture);
    }

    /**
     * Constructor with parameters
     * @param price
     * @param numberOfDrawers
     */
    public Desk(int price,  int numberOfDrawers) {
        super( price, ProductType.Furniture);
        this.numberOfDrawers = numberOfDrawers;
    }


    public int getNumberOfDrawers() {
        return numberOfDrawers;
    }

    public void setNumberOfDrawers(int numberOfDrawers) {
        this.numberOfDrawers = numberOfDrawers;
    }

    @Override
    public String toString() {
        return "Desk{" +
                ", numberOfDrawers=" + numberOfDrawers +
                ", price=" + price +
                ", type=" + type +
                '}';
    }

    @Override
    public ProductEnum getEnum() {
        return ProductEnum.Desk;
    }
}
