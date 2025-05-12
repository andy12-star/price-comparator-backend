package com.andy.accesa.service.api;

import com.andy.accesa.model.entity.Product;

import java.util.List;

public interface AlertService {
    void addAlert(String productId, double targetPrice);
    List<Product> checkAlerts();
}
