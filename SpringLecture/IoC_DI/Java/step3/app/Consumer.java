package step3.app;

import step3.enums.Menu;

public interface Consumer {
    void order(Menu menu, int amount);
}
