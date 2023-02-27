package step3.order;

public class Order {
    private String foodName;
    private int price;
    private int amount;

    public Order(String foodName, int price, int amount) {
        this.foodName = foodName;
        this.price = price;
        this.amount = amount;
    }

    public String getFoodName() {
        return foodName;
    }

    public int getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }
}
