package com.example.order.concurrency;

import com.example.order.processing.Clothing;
import com.example.order.processing.Electronics;
import com.example.order.processing.OrderProcessor;
import com.example.order.processing.Product;
import com.example.order.storage.OrderStorage;
import com.github.javafaker.Faker;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Faker faker = new Faker();
        OrderStorage<Product> storage = new OrderStorage<>();

        for (int i = 0; i < 3; i++) {
            storage.add(Electronics.builder()
                    .name(faker.commerce().productName())
                    .price(faker.number().randomDouble(2, 1000, 50000))
                    .brand(faker.company().name())
                    .warrantyMonths(faker.number().numberBetween(6, 36))
                    .build());
            storage.add(Clothing.builder()
                    .name(faker.commerce().productName())
                    .price(faker.number().randomDouble(2, 200, 5000))
                    .size(faker.options().option("S", "M", "L", "XL"))
                    .material(faker.commerce().material())
                    .build());
        }
        storage.add(Electronics.builder().name("Бракований смартфон").price(-1).build());

        OrderThreadManager.runAll(
                () -> storage.findByType(Electronics.class).forEach(OrderProcessor::processOrder),
                () -> storage.findByType(Clothing.class).forEach(OrderProcessor::processOrder)
        );
    }
}
