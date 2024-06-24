package dev.chafon.datajpa.customer;

import dev.chafon.datajpa.TestcontainersConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Import(TestcontainersConfiguration.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerRepositoryTest {

    @Autowired
    private TestCustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();
    }

    @Test
    void shouldReturnAllCustomers() {
        Customer john = Customer.builder()
                .name("John Doe")
                .address(CustomerAddress.builder()
                        .city("New York")
                        .state("NY")
                        .street("Main St")
                        .zipCode("10001")
                        .build())
                .build();

        Customer tom = Customer.builder()
                .name("Tom Hanks")
                .address(CustomerAddress.builder()
                        .city("Los Angeles")
                        .state("CA")
                        .street("Main St")
                        .zipCode("90001")
                        .build())
                .build();
        List<Customer> customersToSave = List.of(john, tom);
        customerRepository.saveAll(customersToSave);

        List<Customer> customers = customerRepository.findAllWithAddresses();
        assertThat(customers).hasSize(2);
        assertThat(customers).containsExactlyInAnyOrder(john, tom);
    }
}
