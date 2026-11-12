package com.zidio.keystone.controller;
import com.zidio.keystone.dto.CustomerDTO;
import com.zidio.keystone.entity.Customer;
import com.zidio.keystone.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController 
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping 
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        customer.setPhone(customerDTO.getPhone());
        return ResponseEntity.ok(customerService.createCustomer(customer));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Customer>updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerDTO customerDTO) {
        Customer customerDetails = new Customer();
        customerDetails.setName(customerDTO.getName());
        customerDetails.setEmail(customerDTO.getEmail());
        customerDetails.setPhone(customerDTO.getPhone());
        
        Customer updateCustomer = customerService.updateCustomer(id, customerDetails);
        if(updateCustomer != null) {
            return ResponseEntity.ok(updateCustomer);
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
