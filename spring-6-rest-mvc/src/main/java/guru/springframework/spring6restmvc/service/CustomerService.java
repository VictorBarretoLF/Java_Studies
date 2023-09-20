package guru.springframework.spring6restmvc.service;

import java.util.List;
import java.util.UUID;

import guru.springframework.spring6restmvc.model.Customer;

public interface CustomerService {

    Customer getCustomerById(UUID uuid);

    List<Customer> getAllCustomers();

}