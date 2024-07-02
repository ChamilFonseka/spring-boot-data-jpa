package dev.chafon.datajpa.customer;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.utility.TestcontainersConfiguration;

import java.util.List;
import java.util.Optional;

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
        assertThat(customerDto.id()).isEqualTo(customerWithAddress.getId());
        assertThat(customerDto.name()).isEqualTo(customerWithAddress.getName());
        assertThat(customerDto.street()).isEqualTo(customerWithAddress.getAddress().getStreet());
        assertThat(customerDto.city()).isEqualTo(customerWithAddress.getAddress().getCity());
        assertThat(customerDto.state()).isEqualTo(customerWithAddress.getAddress().getState());
        assertThat(customerDto.zipCode()).isEqualTo(customerWithAddress.getAddress().getZipCode());
    }

    @Test
    void shouldReturnCustomerWithoutAddress() {
        CustomerDto customerDto = customerService.getCustomer(customerWithoutAddress.getId());

        assertThat(customerDto).isNotNull();
        assertThat(customerDto.id()).isEqualTo(customerWithoutAddress.getId());
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

    @Test
    void shouldReturnAllCustomers() {
        List<CustomerDto> customers = customerService.getCustomers();
        assertThat(customers).hasSize(2);

        assertThat(customers.getFirst()).isNotNull();
        assertThat(customers.getFirst().id()).isEqualTo(customerWithAddress.getId());
        assertThat(customers.getFirst().name()).isEqualTo(customerWithAddress.getName());
        assertThat(customers.getFirst().street()).isEqualTo(customerWithAddress.getAddress().getStreet());
        assertThat(customers.getFirst().city()).isEqualTo(customerWithAddress.getAddress().getCity());
        assertThat(customers.getFirst().state()).isEqualTo(customerWithAddress.getAddress().getState());
        assertThat(customers.getFirst().zipCode()).isEqualTo(customerWithAddress.getAddress().getZipCode());

        assertThat(customers.get(1)).isNotNull();
        assertThat(customers.get(1).id()).isEqualTo(customerWithoutAddress.getId());
        assertThat(customers.get(1).name()).isEqualTo(customerWithoutAddress.getName());
        assertThat(customers.get(1).street()).isNull();
        assertThat(customers.get(1).city()).isNull();
        assertThat(customers.get(1).state()).isNull();
        assertThat(customers.get(1).zipCode()).isNull();
    }

    @Test
    void shouldCreateCustomer() {
        CustomerDto customerDtoToCreate = new CustomerDto(
                null,
                "Michael Gibson",
                "123 Main Street",
                "Rochester",
                "NY",
                "54321"
        );
        Integer id = customerService.createCustomer(customerDtoToCreate);
        assertThat(id).isNotNull();

        Optional<CustomerDto> createdCustomerOptional = customerRepository.findCustomerDtoById(id);
        assertThat(createdCustomerOptional).isPresent();

        CustomerDto savedCustomer = createdCustomerOptional.get();
        assertThat(savedCustomer.name()).isEqualTo(customerDtoToCreate.name());
        assertThat(savedCustomer.street()).isEqualTo(customerDtoToCreate.street());
        assertThat(savedCustomer.city()).isEqualTo(customerDtoToCreate.city());
        assertThat(savedCustomer.state()).isEqualTo(customerDtoToCreate.state());
        assertThat(savedCustomer.zipCode()).isEqualTo(customerDtoToCreate.zipCode());

        customerRepository.deleteById(id);
    }

    @Test
    void shouldUpdateCustomerNameAndAddress() {
        CustomerAddress addressToCreate = CustomerAddress.builder()
                .street("street to update")
                .city("city to update")
                .state("state to update")
                .zipCode("zipCode to update")
                .build();
        Customer customerToCreate = Customer.builder()
                .name("name to update")
                .address(addressToCreate)
                .build();
        addressToCreate.setCustomer(customerToCreate);
        Integer id = customerRepository.save(customerToCreate).getId();

        CustomerDto customerDtoToUpdate = new CustomerDto(
                null,
                "name updated",
                "street updated",
                "city updated",
                "state updated",
                "zipCode updated"
        );
        customerService.updateCustomer(id, customerDtoToUpdate);

        Optional<CustomerDto> updatedCustomerOptional = customerRepository.findCustomerDtoById(id);
        assertThat(updatedCustomerOptional).isPresent();

        CustomerDto updatedCustomer = updatedCustomerOptional.get();
        assertThat(updatedCustomer.name()).isEqualTo(customerDtoToUpdate.name());
        assertThat(updatedCustomer.street()).isEqualTo(customerDtoToUpdate.street());
        assertThat(updatedCustomer.city()).isEqualTo(customerDtoToUpdate.city());
        assertThat(updatedCustomer.state()).isEqualTo(customerDtoToUpdate.state());
        assertThat(updatedCustomer.zipCode()).isEqualTo(customerDtoToUpdate.zipCode());

        customerRepository.deleteById(id);
    }

    @Test
    void shouldUpdateCustomerNameAndSetAddressToNull() {
        CustomerAddress addressToCreate = CustomerAddress.builder()
                .street("street to update")
                .city("city to update")
                .state("state to update")
                .zipCode("zipCode to update")
                .build();
        Customer customerToCreate = Customer.builder()
                .name("name to update")
                .address(addressToCreate)
                .build();
        addressToCreate.setCustomer(customerToCreate);
        Integer id = customerRepository.save(customerToCreate).getId();

        CustomerDto customerDtoToUpdate = new CustomerDto(
                null,
                "name updated",
                null,
                null,
                null,
                null
        );
        customerService.updateCustomer(id, customerDtoToUpdate);

        Optional<CustomerDto> updatedCustomerOptional = customerRepository.findCustomerDtoById(id);
        assertThat(updatedCustomerOptional).isPresent();

        CustomerDto updatedCustomer = updatedCustomerOptional.get();
        assertThat(updatedCustomer.name()).isEqualTo(customerDtoToUpdate.name());
        assertThat(updatedCustomer.street()).isNull();
        assertThat(updatedCustomer.city()).isNull();
        assertThat(updatedCustomer.state()).isNull();
        assertThat(updatedCustomer.zipCode()).isNull();

        customerRepository.deleteById(id);
    }

    @Test
    void shouldDeleteCustomer() {
        Integer id = customerRepository.save(
                Customer.builder()
                        .name("name to delete")
                        .build()).getId();

        customerService.deleteCustomer(id);
        assertThat(customerRepository.findById(id)).isEmpty();
    }
}


