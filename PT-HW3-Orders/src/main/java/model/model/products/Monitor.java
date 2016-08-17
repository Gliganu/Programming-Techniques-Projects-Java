package model.model.products;

/**
 * Created by gliga on 3/29/2015.
 */
public class Monitor extends Product{

    private int refreshRate;
    private int diagonalSize;

    /**
     * Simple constructor
     */
    public Monitor(){
        super(ProductType.IT);
    }

    /**
     * Constructor with parameters
     * @param price
     * @param refreshRate
     * @param diagonalSize
     */
    public Monitor( int price, int refreshRate, int diagonalSize) {
        super(price, ProductType.IT);
        this.refreshRate = refreshRate;
        this.diagonalSize = diagonalSize;
    }

    public int getRefreshRate() {
        return refreshRate;
    }

    public void setRefreshRate(int refreshRate) {
        this.refreshRate = refreshRate;
    }

    public int getDiagonalSize() {
        return diagonalSize;
    }

    public void setDiagonalSize(int diagonalSize) {
        this.diagonalSize = diagonalSize;
    }

    @Override
    public String toString() {
        return "Monitor{" +
                "refreshRate=" + refreshRate +
                ", diagonalSize=" + diagonalSize +
                "price=" + price +
                ", type=" + type +
                '}';
    }

    @Override
    public ProductEnum getEnum() {
        return ProductEnum.Monitor;
    }
}
