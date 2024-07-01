package dev.chafon.datajpa.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerDto getCustomer(int id) {
        Optional<CustomerDto> customerDto = customerRepository.findById(id);
        return customerDto.orElseThrow(() ->
                new CustomerNotFoundException(id));
    }
}
