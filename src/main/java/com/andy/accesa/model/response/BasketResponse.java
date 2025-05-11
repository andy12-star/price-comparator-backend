package com.andy.accesa.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BasketResponse {

    private String store;
    private double basketPrice;
    private List<String> missingProducts;
}
