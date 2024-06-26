package dev.chafon.datajpa.order;

import dev.chafon.datajpa.TestcontainersConfiguration;
import dev.chafon.datajpa.customer.Customer;
import dev.chafon.datajpa.customer.CustomerRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderControllerTest {

    private static final String BASE_URL = "/api/v1/orders";

    private final Customer customerWithOrders = Customer.builder()
            .name("John Doe")
            .build();

    private final Customer customerWithOutOrders = Customer.builder()
            .name("Jean Gray")
            .build();
    private final Order order1 = Order.builder()
            .orderNumber("ORD001")
            .orderCreatedTime(LocalDateTime.of(
                    2024,
                    5,
                    1,
                    10,
                    30,
                    44))
            .customer(customerWithOrders)
            .build();

    private final Order order2 = Order.builder()
            .orderNumber("ORD002")
            .orderCreatedTime(LocalDateTime.of(
                    2024,
                    6,
                    20,
                    19,
                    52,
                    56))
            .customer(customerWithOrders)
            .build();

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TestOrderRepository orderRepository;

    @BeforeAll
    void beforeAll() {
        customerRepository.save(customerWithOrders);
        customerRepository.save(customerWithOutOrders);
    }

    @BeforeEach
    void setUp() {
        orderRepository.deleteAll();
    }

    @Test
    void shouldReturnAllOrders() {
        orderRepository.save(order1);
        orderRepository.save(order2);

        ResponseEntity<Order[]> response = restTemplate
                .getForEntity(BASE_URL, Order[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(2);
        assertThat(response.getBody()).contains(order1, order2);
    }
}
