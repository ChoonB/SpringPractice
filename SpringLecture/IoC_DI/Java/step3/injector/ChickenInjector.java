package step3.injector;

import step3.order.OrderList;
import step3.service.ChickenService;
import step3.service.OrderService;

public class ChickenInjector implements MenuInjector{
    @Override
    public OrderService getService() {
        return new ChickenService(new OrderList("치킨", "Robbie", "1234"));
    }
}
