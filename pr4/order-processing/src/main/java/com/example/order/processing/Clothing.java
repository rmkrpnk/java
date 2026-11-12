package com.example.order.processing;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Clothing implements Product {
    private final String name;
    private final double price;
    private final String size;
    private final String material;
}
