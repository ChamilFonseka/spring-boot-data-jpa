package dev.chafon.datajpa.order;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
class OrderController {

    private final OrderService orderService;

    @GetMapping
    List<Order> getOrders(@RequestParam(value = "customerId", required = false) Integer customerId) {
        if(customerId != null) {
            return orderService.getOrdersByCustomer(customerId);
        }
        return orderService.getOrders();
    }

    @GetMapping("/{id}")
    Order getOrder(@PathVariable Integer id) {
        return orderService.getOrder(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Order createOrder(@RequestBody OrderDto orderDto) {
        return orderService.createOrder(orderDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateOrder(@RequestBody OrderDto orderDto) {
        orderService.updateOrder(orderDto);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteOrder(@PathVariable Integer id) {
        orderService.deleteOrder(id);
    }
}
