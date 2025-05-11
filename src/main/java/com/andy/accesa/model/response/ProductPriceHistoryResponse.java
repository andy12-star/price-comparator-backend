package com.andy.accesa.model.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ProductPriceHistoryResponse {
    private String store;
    private LocalDate date;
    private double price;
    private double package_quantity;
    private String package_unit;
    private double pricePerUnit;
}
