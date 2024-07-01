package dev.chafon.datajpa.customer;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.utility.TestcontainersConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles(value = "test")
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerRepository customerRepository;
    private Customer customerWithAddress;
    private Customer customerWithoutAddress;

    @BeforeAll
    void beforeAll() {
        CustomerAddress address = CustomerAddress.builder()
                .street("9404 Bald Hill")
                .city("LaneBrooklyn")
                .state("NY")
                .zipCode("11228")
                .build();
        customerWithAddress = Customer.builder()
                .name("John Doe")
                .address(address)
                .build();
        address.setCustomer(customerWithAddress);
        customerRepository.save(customerWithAddress);

        customerWithoutAddress = Customer.builder()
                .name("Tom Smith")
                .build();
        customerRepository.save(customerWithoutAddress);
    }

    @Test
    void shouldReturnCustomerWithAddress() {
        CustomerDto customerDto = customerService.getCustomer(customerWithAddress.getId());

        assertThat(customerDto).isNotNull();
        assertThat(customerDto.id()).isNotNull();
        assertThat(customerDto.name()).isEqualTo(customerWithAddress.getName());
        assertThat(customerDto.street()).isEqualTo(customerWithAddress.getAddress().getStreet());
        assertThat(customerDto.city()).isEqualTo(customerWithAddress.getAddress().getCity());
        assertThat(customerDto.state()).isEqualTo(customerWithAddress.getAddress().getState());
        assertThat(customerDto.zipCode()).isEqualTo(customerWithAddress.getAddress().getZipCode());
    }

    @Test
    void shouldReturnCustomer() {
        CustomerDto customerDto = customerService.getCustomer(customerWithoutAddress.getId());

        assertThat(customerDto).isNotNull();
        assertThat(customerDto.id()).isNotNull();
        assertThat(customerDto.name()).isEqualTo(customerWithoutAddress.getName());
        assertThat(customerDto.street()).isNull();
        assertThat(customerDto.city()).isNull();
        assertThat(customerDto.state()).isNull();
        assertThat(customerDto.zipCode()).isNull();
    }

    @Test
    void shouldThrowCustomerNotFoundException() {
        int id = 999;
        assertThatThrownBy(() -> customerService.getCustomer(id))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessageContaining("Could not find customer with id : " + id);
    }
}


