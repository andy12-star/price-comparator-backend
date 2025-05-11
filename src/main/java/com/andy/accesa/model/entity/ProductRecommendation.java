package com.andy.accesa.model.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductRecommendation {
    private String productId;
    private String productName;
    private String brand;
    private String store;
    private double price;
    private double package_quantity;
    private String package_unit;
    private double pricePerUnit;
}
