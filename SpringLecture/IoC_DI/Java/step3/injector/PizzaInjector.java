package step3.injector;

import step3.order.OrderList;
import step3.service.OrderService;
import step3.service.PizzaService;

public class PizzaInjector implements MenuInjector{
    @Override
    public OrderService getService() {
        return new PizzaService(new OrderList("피자", "Robbie", "1234"));
    }
}
