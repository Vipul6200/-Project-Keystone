package com.zidio.keystone.service;
import com.zidio.keystone.entity.Customer;
import com.zidio.keystone.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
    public Customer updateCustomer(Long id, Customer customerDetails) {
        Optional<Customer> existing = customerRepository.findById(id);

        if (existing.isPresent()) {
            Customer updated = existing.get();

            updated.setName(customerDetails.getName());
            updated.setEmail(customerDetails.getEmail());
            updated.setPhone(customerDetails.getPhone());

            return customerRepository.save(updated);
        }

        return null;
    }
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
