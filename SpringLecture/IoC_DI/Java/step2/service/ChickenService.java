package step2.service;


import step2.enums.Menu;
import step2.order.Order;
import step2.order.OrderList;

public class ChickenService implements OrderService{

    private final OrderList orderList;

    public ChickenService(OrderList orderList) {
        this.orderList = orderList;
    }

    @Override
    public void saveOrder(Menu menu, int amount) {
        System.out.println("치킨 주문 저장");
        Order order = new Order(menu.getFoodName(), menu.getPrice(), amount);
        orderList.setOrderList(order);
    }
}
