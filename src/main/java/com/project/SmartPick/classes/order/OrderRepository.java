package com.project.SmartPick.classes.order;

import java.util.List;

public interface OrderRepository {
    
    void createOrder(Order order);

    int getLastInsertedOrderId();

    void createOrderItem(OrderItem orderItem);

    List<OrderItem> getOrderItemsByOrderId(int orderId);

    Order findById(int orderId);

    List<Order> getAllOrdersByUserId(int userId);
}
