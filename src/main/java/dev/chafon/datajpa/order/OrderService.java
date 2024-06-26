package dev.chafon.datajpa.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
class OrderService {

    private final OrderRepository orderRepository;

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    public Order getOrder(Integer id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
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
