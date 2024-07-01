package dev.chafon.datajpa.customer;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.Optional;

@RepositoryDefinition(domainClass = Customer.class, idClass = Integer.class)
public interface CustomerRepository {

    @Query("""
            SELECT new dev.chafon.datajpa.customer.CustomerDto
            (c.id, c.name, a.street, a.city, a.state, a.zipCode)
            FROM Customer c LEFT JOIN c.address a where c.id = :id
            """)
    Optional<CustomerDto> findById(Integer id);
    Customer save(Customer customer);
}
