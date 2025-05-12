package com.andy.accesa.controller;

import com.andy.accesa.model.entity.Product;
import com.andy.accesa.model.request.PriceAlertRequest;
import com.andy.accesa.service.api.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alerts")
@RequiredArgsConstructor
public class AlertController {
    private final AlertService alertService;

    @PostMapping
    public String setAlert(@RequestBody PriceAlertRequest priceAlertRequest) {
        alertService.addAlert(priceAlertRequest.getProductId(),priceAlertRequest.getTargetPrice());
        return "An alert was set for product with id: " + priceAlertRequest.getProductId() + " at the price of " + priceAlertRequest.getTargetPrice();
    }

    @GetMapping("/check")
    public List<Product> checkAlerts() {
        return alertService.checkAlerts();
    }
}
