package dev.chafon.datajpa.customer;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class CustomerService {

  private final CustomerRepository customerRepository;

  public List<CustomerDto> getCustomers() {
    return customerRepository.findAllCustomerDto();
  }

  public CustomerDto getCustomer(int id) {
    return customerRepository
        .findCustomerDtoById(id)
        .orElseThrow(() -> new CustomerNotFoundException(id));
  }

  public Integer createCustomer(CustomerDto customerDto) {
    Customer customer = Customer.of(customerDto);
    return customerRepository.save(customer).getId();
  }

  public void updateCustomer(Integer id, CustomerDto customerDto) {
    customerRepository
        .findById(id)
        .ifPresentOrElse(
            customer -> {
              Customer.map(customer, customerDto);
              customerRepository.save(customer);
            },
            () -> {
              throw new CustomerNotFoundException(id);
            });
  }

  public void deleteCustomer(Integer id) {
    customerRepository.deleteById(id);
  }
}
