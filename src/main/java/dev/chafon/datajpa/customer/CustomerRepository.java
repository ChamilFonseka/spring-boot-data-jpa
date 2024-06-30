package dev.chafon.datajpa.customer;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.Optional;

@RepositoryDefinition(domainClass = Customer.class, idClass = Integer.class)
public interface CustomerRepository {
    @EntityGraph(attributePaths = "address")
    Optional<Customer> findById(Integer id);
}
