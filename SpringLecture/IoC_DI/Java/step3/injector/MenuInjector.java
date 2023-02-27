package step3.injector;

import step3.service.OrderService;

public interface MenuInjector {
    OrderService getService();
}
