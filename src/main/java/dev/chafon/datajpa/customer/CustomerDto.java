package dev.chafon.datajpa.customer;

public record CustomerDto(
        Integer id,
        String name,
        String street,
        String city,
        String state,
        String zipCode) {
    public static CustomerDto from(Customer customer) {
        return new CustomerDto(
                customer.getId(),
                customer.getName(),
                customer.getAddress().getStreet(),
                customer.getAddress().getCity(),
                customer.getAddress().getState(),
                customer.getAddress().getZipCode()
        );
    }
}
