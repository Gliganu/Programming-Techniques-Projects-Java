package model.model.products;

/**
 * Created by gliga on 3/29/2015.
 */
public abstract class Product {

    protected int price;
    protected ProductType type;

    /**
     * Simple constructor
     * @param type
     */
    protected Product(ProductType type) {
        this.type = type;
    }

    /**
     * Constructor with parameters
     * @param price
     * @param type
     */
    protected Product(int price, ProductType type) {
        this.price = price;
        this.type = type;
    }


    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Product{" +
                "price=" + price +
                ", type=" + type +
                '}';
    }


    public abstract ProductEnum getEnum();

    public enum ProductType{

        IT, Furniture,Other;

    }

    public enum ProductEnum{

        Closet,Computer,Desk,Monitor,Statistic;

    }

}
