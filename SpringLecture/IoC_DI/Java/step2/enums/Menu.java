package step2.enums;

public enum Menu {
    Chicken("치킨", 30000),
    Pizza("피자", 50000);

    private final String foodName;
    private final int price;

    Menu(String foodName, int price) {
        this.foodName = foodName;
        this.price = price;
    }

    public String getFoodName() {
        return foodName;
    }

    public int getPrice() {
        return price;
    }
}
