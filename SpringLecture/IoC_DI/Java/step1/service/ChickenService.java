package step1.service;


import step1.enums.Menu;
import step1.order.Order;
import step1.order.OrderList;

public class ChickenService {
    private final OrderList orderList = new OrderList("치킨");

    public void saveOrder(Menu menu, int amount) {
        System.out.println("치킨 주문 저장");
        Order order = new Order(menu.getFoodname(), menu.getPrice(), amount);
        orderList.setOrderList(order);
    }
}
