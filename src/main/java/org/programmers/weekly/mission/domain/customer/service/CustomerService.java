package org.programmers.weekly.mission.domain.customer.service;

import org.programmers.weekly.mission.domain.customer.model.BlackCustomer;
import org.programmers.weekly.mission.domain.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<BlackCustomer> getBlackList() {
        return customerRepository.getBlackList();
    }
}
