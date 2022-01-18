package com.shopme.site.customer;

import com.shopme.common.entity.Customer;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@Transactional
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    public boolean isEmailUnique(String email) {
        Customer customer = customerRepo.findByEmail(email);
        return customer == null;
    }

    public Customer getCustomerByEmail(String email) {
        return customerRepo.findByEmail(email);
    }

    private void encodePassword(Customer customer) {
        String encodedPassword = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(encodedPassword);
    }

    public void registerCustomer(Customer customer) {
        encodePassword(customer);
        customer.setEnabled(true);
        customer.setCreatedTime(new Date());

        customerRepo.save(customer);
    }
}
