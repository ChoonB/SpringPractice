package step2.app;


import step2.enums.Menu;
import step2.service.ChickenService;
import step2.service.OrderService;
import step2.service.PizzaService;

public class OrderApplication implements Consumer {

    private final OrderService orderService;

    public OrderApplication(OrderService orderService) {
        this.orderService = orderService;
    }


    @Override
    public void order(Menu menu, int amount) {
        orderService.saveOrder(menu, amount);
    }
}
