package com.andy.accesa.service.impl;

import com.andy.accesa.model.entity.Product;
import com.andy.accesa.model.request.BasketRequest;
import com.andy.accesa.model.response.BasketResponse;
import com.andy.accesa.service.api.BasketService;
import com.andy.accesa.service.data.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {

    private final DataService dataService;

    @Override
    public BasketResponse findBestBasket(BasketRequest basketRequest) {
        Map<String, List<Product>> productsByStore = dataService.getProductsByStore();
        BasketResponse bestBasket = null;

        for (Map.Entry<String, List<Product>> entry : productsByStore.entrySet()) {
            String storeName = entry.getKey();
            List<Product> products = entry.getValue();

            double totalPrice = 0;
            List<String> missingProducts = new ArrayList<>();

            for(String productId: basketRequest.getProductIds()){
                Optional<Product> found = products.stream()
                        .filter(product -> product.getProduct_id().equals(productId))
                        .findFirst();
                if (found.isPresent()) {
                    totalPrice += found.get().getPrice();
                } else {
                    missingProducts.add(productId);
                }
            }

            if (missingProducts.isEmpty()) {
                if (bestBasket==null || totalPrice< bestBasket.getBasketPrice()) {
                    bestBasket = BasketResponse.builder()
                            .store(storeName)
                            .basketPrice(totalPrice)
                            .missingProducts(Collections.emptyList())
                            .build();
                }
            }
        }
        if (bestBasket==null) {
            bestBasket = BasketResponse.builder()
                    .store("No store")
                    .basketPrice(0)
                    .missingProducts(basketRequest.getProductIds())
                    .build();
        }
        return bestBasket;
    }
}
