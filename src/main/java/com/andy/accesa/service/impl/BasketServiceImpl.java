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

    // finds the store with the basket has the lowest price or shows missing products & "No store"
    @Override
    public BasketResponse findBestBasket(BasketRequest basketRequest) {
        Map<String, List<Product>> productsByStore = dataService.getProductsByStore();
        BasketResponse bestBasket = null;

        // check product list for each store
        for (Map.Entry<String, List<Product>> entry : productsByStore.entrySet()) {
            String storeName = entry.getKey();
            List<Product> products = entry.getValue();

            double totalPrice = 0;
            List<String> missingProducts = new ArrayList<>();

            // calculate price or find the missing products
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

            // check for missing products
            if (missingProducts.isEmpty() ) {
                // check is the store is the cheapest
                if (bestBasket==null || totalPrice< bestBasket.getBasketPrice()) {
                    bestBasket = BasketResponse.builder()
                            .store(storeName)
                            .basketPrice(totalPrice)
                            .missingProducts(Collections.emptyList())
                            .build();
                }
            }
        }

        // no store has all the products, return default response
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
