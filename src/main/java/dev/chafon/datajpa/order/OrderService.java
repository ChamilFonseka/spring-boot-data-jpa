package dev.chafon.datajpa.order;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
class OrderService {
    public List<Order> getOrders() {
        return null;
    }

    public Order getOrder(Integer id) {
        return null;
    }

    public List<Order> getOrdersByCustomer(Integer customerId) {
        return null;
    }

    public Order createOrder(OrderDto orderDto) {
        return null;
    }

    public void updateOrder(OrderDto orderDto) {

    }

    public void deleteOrder(Integer id) {

    }
}
