package com.shopme.site.checkout;

public class CheckoutInfo {
    private float productCost;
    private float productTotal;
    private float paymentTotal;
    private boolean codSupported;

    public float getProductCost() {
        return productCost;
    }

    public void setProductCost(float productCost) {
        this.productCost = productCost;
    }

    public float getProductTotal() {
        return productTotal;
    }

    public void setProductTotal(float productTotal) {
        this.productTotal = productTotal;
    }

    public float getPaymentTotal() {
        return paymentTotal;
    }

    public void setPaymentTotal(float paymentTotal) {
        this.paymentTotal = paymentTotal;
    }

    public boolean isCodSupported() {
        return codSupported;
    }

    public void setCodSupported(boolean codSupported) {
        this.codSupported = codSupported;
    }
}
