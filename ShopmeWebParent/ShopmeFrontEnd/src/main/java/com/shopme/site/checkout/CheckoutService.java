package com.shopme.site.checkout;

import com.shopme.common.entity.CartItem;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CheckoutService {
    public CheckoutInfo prepareCheckout(List<CartItem> cartItems) {
        CheckoutInfo checkoutInfo = new CheckoutInfo();

        float productCost = calculateProductCost(cartItems);
        float productTotal = calculateProductTotal(cartItems);

        checkoutInfo.setProductCost(productCost);
        checkoutInfo.setProductTotal(productTotal);

        return checkoutInfo;
    }

    private float calculateProductTotal(List<CartItem> cartItems) {
        float total = 0.0f;
        for (CartItem item : cartItems) {
            total += item.getSubtotal();
        }

        return total;
    }

    private float calculateProductCost(List<CartItem> cartItems) {
        float cost = 0.0f;
        for (CartItem item : cartItems) {
            cost += item.getQuantity() * item.getProduct().getCost();
        }

        return cost;
    }
}
