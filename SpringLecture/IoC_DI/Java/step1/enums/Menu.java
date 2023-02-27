package step1.enums;

public enum Menu {

    Chicken("치킨", 30000),
    Pizza("피자", 50000);

    private String foodname;
    private int price;

    Menu(String foodname, int price) {
        this.foodname = foodname;
        this.price = price;
    }

    public String getFoodname() {
        return foodname;
    }

    public int getPrice() {
        return price;
    }
}
