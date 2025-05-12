package com.andy.accesa.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PriceAlertRequest {
    private String productId;
    private double targetPrice;
}
