package model;

import model.model.products.Product;

import static model.model.products.Product.ProductEnum;

/**
 * Created by gliga on 4/1/2015.
 */
public class ProductStatistic extends Product{

    private ProductEnum productEnum;
    private int numberItemsInStock;
    private int prefferedItemsInStock;

    /**
     * Simple constructor
     */
    public ProductStatistic() {
        super(ProductType.Other);
    }

    /**
     * Constructor with parameters
     * @param productEnum
     * @param numberItemsInStock
     * @param prefferedItemsInStock
     */
    public ProductStatistic(ProductEnum productEnum, int numberItemsInStock, int prefferedItemsInStock) {
        super(ProductType.Other);
        this.productEnum = productEnum;
        this.numberItemsInStock = numberItemsInStock;
        this.prefferedItemsInStock = prefferedItemsInStock;
    }

    public ProductEnum getProductEnum() {
        return productEnum;
    }

    public void setProductEnum(ProductEnum productEnum) {
        this.productEnum = productEnum;
    }

    public int getNumberItemsInStock() {
        return numberItemsInStock;
    }

    public void setNumberItemsInStock(int numberItemsInStock) {
        this.numberItemsInStock = numberItemsInStock;
    }

    public int getPrefferedItemsInStock() {
        return prefferedItemsInStock;
    }

    public void setPrefferedItemsInStock(int prefferedItemsInStock) {
        this.prefferedItemsInStock = prefferedItemsInStock;
    }

    @Override
    public ProductEnum getEnum() {
        return ProductEnum.Statistic;
    }


    public enum StockStatus{
        Understock,Overstock;
    }
}
