package com.example.order.storage;

import com.example.order.processing.Product;

import java.util.ArrayList;
import java.util.List;

public class OrderStorage<T extends Product> {
    private final List<T> orders = new ArrayList<>();

    public synchronized void add(T order) {
        orders.add(order);
    }

    public synchronized <R extends T> List<R> findByType(Class<R> type) {
        return orders.stream()
                .filter(type::isInstance)
                .map(type::cast)
                .toList();
    }
}
