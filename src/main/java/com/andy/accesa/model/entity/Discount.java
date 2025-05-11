package com.andy.accesa.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Discount {

    private String product_id;
    private String product_name;
    private String brand;
    private Double package_quantity;
    private String package_unit;
    private String product_category;
    private LocalDate from_date;
    private LocalDate to_date;
    private int percentage_of_discount;
    private String store;

}
