package step1.order;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderList {

    private String foodName;

    public OrderList(String foodName) {
        this.foodName = foodName;
    }

    private static List<Order> orderList = new ArrayList<>();

    public void setOrderList(Order order) {

        orderList.add(order);
        System.out.println(foodName + " 주문 저장완료");
    }

    public List<Order> getOrderList() {

        if (foodName.equals("치킨")){
            return orderList.stream().filter(f ->f.getFoodName().equals("치킨")).collect(Collectors.toList());

        } else if(foodName.equals("피자")){
            return orderList.stream().filter(f ->f.getFoodName().equals("피자")).collect(Collectors.toList());
        } else {
            return orderList;
        }
    }
}
