package step2.service;

import step2.enums.Menu;

public interface OrderService {
    void saveOrder(Menu menu, int amount);
}
