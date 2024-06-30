package dev.chafon.datajpa.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class CustomerService {

    private final CustomerRepository customerRepository;
}
