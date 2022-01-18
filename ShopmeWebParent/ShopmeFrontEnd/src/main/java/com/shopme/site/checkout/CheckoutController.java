package com.shopme.site.checkout;

import com.shopme.common.entity.CartItem;
import com.shopme.common.entity.Customer;
import com.shopme.site.ControllerHelper;
import com.shopme.site.shoppingcart.ShoppingCartService;
import org.apache.tomcat.jni.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
public class CheckoutController {
    @Autowired
    private CheckoutService checkoutService;
    @Autowired
    private ControllerHelper controllerHelper;
    @Autowired
    private ShoppingCartService cartService;

    @GetMapping("/checkout")
    public String showCheckoutPage(Model model, HttpServletRequest request) {
        Customer customer = controllerHelper.getAuthenticatedCustomer(request);

        List<CartItem> cartItems = cartService.listCartItems(customer);

        model.addAttribute("customer", customer);
        model.addAttribute("cartItems", cartItems);

        return "checkout/checkout";
    }

    @PostMapping("/place_order")
    public String placeOrder(HttpServletRequest request)
            throws UnsupportedEncodingException {
        String paymentType = request.getParameter("paymentMethod");

        Customer customer = controllerHelper.getAuthenticatedCustomer(request);

        List<CartItem> cartItems = cartService.listCartItems(customer);

        cartService.deleteByCustomer(customer);

        return "checkout/order_completed";
    }
}
