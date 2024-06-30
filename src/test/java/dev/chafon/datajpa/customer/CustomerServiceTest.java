package dev.chafon.datajpa.customer;

import dev.chafon.datajpa.TestcontainersConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
@ActiveProfiles(value = "test")
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Test
    void shouldReturnCustomerWithAddress() {
        CustomerDto customerDto = customerService.getCustomer(1);
        System.out.println(customerDto);
    }
}


