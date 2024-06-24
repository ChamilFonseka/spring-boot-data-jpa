package dev.chafon.datajpa.customer;

import dev.chafon.datajpa.TestcontainersConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerControllerTest {

    private static final String BASE_URL = "/api/v1/customers";

    private final Customer john = Customer.builder()
            .name("John Doe")
            .address(CustomerAddress.builder()
                    .city("New York")
                    .state("NY")
                    .street("Main St")
                    .zipCode("10001")
                    .build())
            .build();
    private final Customer tom = Customer.builder()
            .name("Tom Hanks")
            .address(CustomerAddress.builder()
                    .city("Los Angeles")
                    .state("CA")
                    .street("Main St")
                    .zipCode("90001")
                    .build())
            .build();

    private final CustomerDto customerToCreate = new CustomerDto(
            "Nick Cage",
            "New York",
            "NY",
            "Main St",
            "10001");

    private final CustomerDto customerToUpdate = new CustomerDto(
            "Tom",
            "New York",
            "NY",
            "Main St",
            "23456");
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TestCustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();
    }

    @Test
    void shouldReturnAllCustomers() {
        List<Customer> customersToSave = List.of(john, tom);
        customerRepository.saveAll(customersToSave);

        ResponseEntity<Customer[]> response = restTemplate
                .getForEntity(BASE_URL, Customer[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(2);
        assertThat(response.getBody()).contains(john, tom);
    }

    @Test
    void shouldReturnCustomer() {
        customerRepository.save(tom);

        ResponseEntity<Customer> response = restTemplate
                .getForEntity(BASE_URL + "/{id}", Customer.class, tom.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(tom);
    }

    @Test
    void shouldReturnNotFound() {
        ResponseEntity<String> response = restTemplate
                .getForEntity(BASE_URL + "/{id}", String.class, 999);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldCreateCustomer() {
        ResponseEntity<Customer> response = restTemplate.postForEntity(BASE_URL, customerToCreate, Customer.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        URI location = response.getHeaders().getLocation();
        Customer customerCreated = restTemplate.getForObject(location, Customer.class);
        assertThat(customerCreated.getId()).isNotNull();
        assertThat(customerCreated.getName()).isEqualTo(customerToCreate.name());
        assertThat(customerCreated.getAddress().getCity()).isEqualTo(customerToCreate.city());
        assertThat(customerCreated.getAddress().getState()).isEqualTo(customerToCreate.state());
        assertThat(customerCreated.getAddress().getStreet()).isEqualTo(customerToCreate.street());
        assertThat(customerCreated.getAddress().getZipCode()).isEqualTo(customerToCreate.zipCode());
    }

    @Test
    void shouldUpdateCustomer() {
        customerRepository.save(tom);

        ResponseEntity<Void> response = restTemplate.exchange(BASE_URL + "/{id}", HttpMethod.PUT,
                new HttpEntity<>(customerToUpdate), Void.class, tom.getId());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        customerRepository.findById(tom.getId())
                .ifPresentOrElse(
                        updatedCustomer -> {
                            assertThat(updatedCustomer.getName()).isEqualTo(customerToUpdate.name());
                            assertThat(updatedCustomer.getAddress().getCity()).isEqualTo(customerToUpdate.city());
                            assertThat(updatedCustomer.getAddress().getState()).isEqualTo(customerToUpdate.state());
                            assertThat(updatedCustomer.getAddress().getStreet()).isEqualTo(customerToUpdate.street());
                            assertThat(updatedCustomer.getAddress().getZipCode()).isEqualTo(customerToUpdate.zipCode());
                        },
                        () -> fail("Customer not found")
                );
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingCustomerDoesNotExist() {
        ResponseEntity<Void> response = restTemplate.exchange(BASE_URL + "/{id}", HttpMethod.PUT,
                new HttpEntity<>(customerToUpdate), Void.class, 999);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldDeleteCustomer() {
        customerRepository.save(tom);

        ResponseEntity<Void> response = restTemplate.exchange(BASE_URL + "/{id}", HttpMethod.DELETE,
                null, Void.class, tom.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(customerRepository.findById(tom.getId())).isEmpty();
    }
}
