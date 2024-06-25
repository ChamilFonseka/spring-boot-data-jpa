package dev.chafon.datajpa.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
class CustomerService {

    private final CustomerRepository customerRepository;

    List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    Customer getCustomer(Integer id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    Customer createCustomer(CustomerDto customerDto) {
        return customerRepository.save(Customer.of(customerDto));
    }

    void updateCustomer(Integer id, CustomerDto customerDto) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));

        customer.mapTo(customerDto);
        customerRepository.save(customer);
    }

    void deleteCustomer(Integer id) {
        customerRepository.deleteById(id);
    }
}
