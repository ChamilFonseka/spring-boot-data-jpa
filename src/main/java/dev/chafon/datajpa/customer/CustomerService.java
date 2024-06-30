package dev.chafon.datajpa.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerDto getCustomer(int id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() ->
                        new CustomerNotFoundException(id));

        return CustomerDto.from(customer);
    }
}
