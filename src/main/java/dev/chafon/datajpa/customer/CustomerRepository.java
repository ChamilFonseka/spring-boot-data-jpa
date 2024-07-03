package dev.chafon.datajpa.customer;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Customer.class, idClass = Integer.class)
public interface CustomerRepository {

  @Query(
      """
			SELECT new dev.chafon.datajpa.customer.CustomerDto
			(c.id, c.name, a.street, a.city, a.state, a.zipCode)
			FROM Customer c LEFT JOIN c.address a
			""")
  List<CustomerDto> findAllCustomerDto();

  @Query(
      """
			SELECT new dev.chafon.datajpa.customer.CustomerDto
			(c.id, c.name, a.street, a.city, a.state, a.zipCode)
			FROM Customer c LEFT JOIN c.address a where c.id = :id
			""")
  Optional<CustomerDto> findCustomerDtoById(Integer id);

  Optional<Customer> findById(Integer id);

  Customer save(Customer customer);

  void deleteById(Integer id);
}
