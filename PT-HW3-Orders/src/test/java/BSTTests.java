import controller.Warehouse;
import model.Customer;
import model.Order;
import model.model.products.Closet;
import model.model.products.Computer;
import model.model.products.Desk;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by gliga on 4/8/2015.
 */
public class BSTTests {

    @Test
    public void testInsert(){

        Warehouse warehouse = new Warehouse();

        assertEquals(warehouse.getAllOrders().size(), 130);

        warehouse.insertOrder(new Order(new Customer("a","b"),new Computer(10,100,500,false)));

        assertEquals(warehouse.getAllOrders().size(), 131);


    }

    @Test
    public void testDelete(){


        Warehouse warehouse = new Warehouse();

        assertEquals(warehouse.getAllOrders().size(), 130);

        Order order = new Order(new Customer("ghgfa", "terb"), new Computer(10, 100, 500, false));
        Order order2 = new Order(new Customer("sadaa", "btre"), new Desk(50,100));
        Order order3 = new Order(new Customer("agfd", "bdfg"), new Closet(100,50,10,2));

        warehouse.insertOrder(order);
        warehouse.insertOrder(order2);
        warehouse.insertOrder(order3);

        warehouse.deleteOrder(order3);

        assertEquals(warehouse.getAllOrders().size(), 132);



    }


}
