package step1.app;

import step1.enums.Menu;
import step1.service.ChickenService;
import step1.service.PizzaService;

public class OrderApplication implements Consumer{

    private ChickenService chickenService = new ChickenService();
    private PizzaService pizzaService = new PizzaService();


    @Override
    public void chickenOrder(Menu menu, int amount) {
        chickenService.saveOrder(menu, amount);
    }

    @Override
    public void pizzaOrder(Menu menu, int amount) {
        pizzaService.saveOrder(menu, amount);

    }
}
