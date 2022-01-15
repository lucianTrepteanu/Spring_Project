package com.shopme.site;

import com.shopme.common.entity.Customer;
import com.shopme.site.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
@Component
public class ControllerHelper {
    @Autowired
    private CustomerService customerService;

    public Customer getAuthenticatedCustomer(HttpServletRequest request) {
        String email = getEmailOfAuthenticatedCustomer(request);
        return customerService.getCustomerByEmail(email);
    }

    public static String getEmailOfAuthenticatedCustomer(HttpServletRequest request) {
        Object principal = request.getUserPrincipal();
        if (principal == null) return null;

        String customerEmail = null;

        customerEmail = request.getUserPrincipal().getName();

        return customerEmail;
    }
}
