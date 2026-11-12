package com.example.order.processing;

import com.example.order.processing.internal.ProductValidator;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OrderProcessor<T extends Product> {
    private final T product;

    public void process() throws OrderProcessingException {
        ProductValidator.validate(product);
        System.out.println(Thread.currentThread().getName() + ": " + product.getName() + " — " + product.getPrice() + " грн");
    }

    public static <T extends Product> void processOrder(T product) {
        try {
            new OrderProcessor<>(product).process();
        } catch (OrderProcessingException e) {
            System.out.println(Thread.currentThread().getName() + ": помилка — " + e.getMessage());
        }
    }
}
