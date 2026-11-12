package com.example.order.processing.internal;

import com.example.order.processing.OrderProcessingException;
import com.example.order.processing.Product;

public abstract class ProductValidator {

    public static void validate(Product product) throws OrderProcessingException {
        if (product.getPrice() <= 0) {
            throw new OrderProcessingException("некоректна ціна товару " + product.getName());
        }
    }
}
