package step2;


import step2.app.Consumer;
import step2.app.OrderApplication;
import step2.enums.Menu;
import step2.order.Order;
import step2.order.OrderList;
import step2.service.ChickenService;
import step2.service.OrderService;
import step2.service.PizzaService;

public class Main {
    public static void main(String[] args) {

        // 주문
        Consumer consumer;
        OrderService orderService;

        orderService = new ChickenService(new OrderList("치킨", "Robbie", "1234"));
        consumer = new OrderApplication(orderService);
        consumer.order(Menu.Chicken, 2);

        // 주문 내역 확인
        OrderList orderList = new OrderList("치킨", "Robbie", "1234");
        for (Order order : orderList.getOrderList()) {
            System.out.println(order.getFoodName());
            System.out.println(order.getPrice());
            System.out.println(order.getAmount());
            System.out.println();
        }

        orderService = new PizzaService(new OrderList("피자", "Robbie", "1234"));
        consumer = new OrderApplication(orderService);
        consumer.order(Menu.Pizza, 2);

        // 주문 내역 확인
        orderList = new OrderList("피자", "Robbie", "1234");
        for (Order order : orderList.getOrderList()) {
            System.out.println(order.getFoodName());
            System.out.println(order.getPrice());
            System.out.println(order.getAmount());
        }

    }
}
