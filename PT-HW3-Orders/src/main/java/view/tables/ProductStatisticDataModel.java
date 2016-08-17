package view.tables;

import model.Customer;
import model.Order;
import model.ProductStatistic;
import model.model.products.Closet;
import model.model.products.Product;

import static model.ProductStatistic.StockStatus;
import static model.model.products.Product.ProductEnum;

/**
 * Created by gliga on 4/1/2015.
 */
public class ProductStatisticDataModel extends AbstractOrderTableModel {



    private String[] columnNames={"Name","Number of items in stock","Preferred number of items in stock","Status"};

    /**
     * data model used for the statistic
     */
    public ProductStatisticDataModel() {

    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public int getRowCount() {
        return orderList.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex){
            case 0:
                return ProductEnum.class;
            case 1:
                return Integer.class;
            case 2:
                return Integer.class;
            case 3:
                return StockStatus.class;
            default:
                return null;
        }

    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Order order =  orderList.get(rowIndex);
        ProductStatistic statistic = (ProductStatistic) order.getProduct();
        switch (columnIndex){
            case 0:
                return statistic.getProductEnum();
            case 1:
                return statistic.getNumberItemsInStock();
            case 2:
                return statistic.getPrefferedItemsInStock();
            case 3:
                if(statistic.getNumberItemsInStock() > statistic.getPrefferedItemsInStock()){
                    return StockStatus.Overstock;
                }else{
                    return StockStatus.Understock;
                }

        }
        return null;
    }

}
