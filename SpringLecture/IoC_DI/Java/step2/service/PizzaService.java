package step2.service;


import step2.enums.Menu;
import step2.order.Order;
import step2.order.OrderList;

public class PizzaService implements OrderService{

    private final OrderList orderList;

    public PizzaService(OrderList orderList) {
        this.orderList = orderList;
    }

    @Override
    public void saveOrder(Menu menu, int amount) {
        System.out.println("피자 주문 저장");
        Order order = new Order(menu.getFoodName(), menu.getPrice(), amount);
        orderList.setOrderList(order);
    }
}
