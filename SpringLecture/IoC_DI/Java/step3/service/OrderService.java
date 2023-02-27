package step3.service;

import step3.enums.Menu;

public interface OrderService {
    void saveOrder(Menu menu, int amount);
}
