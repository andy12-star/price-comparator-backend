package com.andy.accesa.service.api;

import com.andy.accesa.model.entity.Product;
import com.andy.accesa.model.entity.ProductRecommendation;
import com.andy.accesa.model.response.ProductPriceHistoryResponse;

import java.util.List;

public interface ProductService {

    List<Product> findAll();
    List<Product> getProductsByStore(String store);
    List<Product> getProductsByName(String name);

    List<ProductRecommendation> getRecommendations(String category, String brand);

    List<ProductPriceHistoryResponse> getPriceHistory(String productId);
}
