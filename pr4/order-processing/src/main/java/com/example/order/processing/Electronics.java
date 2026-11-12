package com.example.order.processing;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Electronics implements Product {
    private final String name;
    private final double price;
    private final String brand;
    private final int warrantyMonths;
}
