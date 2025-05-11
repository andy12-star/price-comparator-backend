package com.andy.accesa.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    private String product_id;
    private String product_name;
    private String product_category;
    private String brand;
    private Double package_quantity;
    private String package_unit;
    private Double price;
    private String currency;
    private String store;
    private LocalDate createdAt;

}
