package step2.app;

import step2.enums.Menu;

public interface Consumer {
    void order(Menu menu, int amount);

}
