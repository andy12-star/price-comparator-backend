package com.andy.accesa.model.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class BasketRequest {
    private List<String> productIds;

}
