package com.andy.accesa.service.impl;

import com.andy.accesa.model.entity.Product;
import com.andy.accesa.service.api.AlertService;
import com.andy.accesa.service.data.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AlertServiceImpl implements AlertService {

    private final DataService dataService;
    private final Map<String,Double> alerts = new HashMap<>();

    @Override
    public void addAlert(String productId, double targetPrice) {
        alerts.put(productId, targetPrice);
    }


    @Override
    public List<Product> checkAlerts() {
       List<Product> products = new ArrayList<>();
       Map<String,List<Product>> productsByStore = dataService.getProductsByStore();

       // goes through all the alerts
       for(Map.Entry<String,Double> entry : alerts.entrySet()) {
           String productId = entry.getKey();
           Double targetPrice = entry.getValue();

           // finds the product with the id and the price below = target
           productsByStore.values().stream()
                   .flatMap(List::stream)
                   .filter(product -> product.getProduct_id().equals(productId))
                   .filter(product -> targetPrice >= product.getPrice())
                   .forEach(products::add);
       }
       return products;
    }
}
