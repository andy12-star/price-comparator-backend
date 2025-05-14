package com.andy.accesa.controller;

import com.andy.accesa.model.entity.Product;
import com.andy.accesa.model.entity.ProductRecommendation;
import com.andy.accesa.model.response.ProductPriceHistoryResponse;
import com.andy.accesa.service.api.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    @GetMapping("/store/{store}")
    public List<Product> getProductsByStore(@PathVariable String store) {
        return productService.getProductsByStore(store);
    }

    @GetMapping("/find/{name}")
    public List<Product> getProductsByName(@PathVariable String name) {
        return productService.getProductsByName(name);
    }

    @GetMapping("/recommendations/{category}")
    public List<ProductRecommendation> getProductsByRecommendation(@PathVariable String category, @RequestParam(required = false) String brand) {
        return productService.getRecommendations(category,brand);
    }

    @GetMapping("/{productId}/history")
    public List<ProductPriceHistoryResponse> getPriceHistory(@PathVariable String productId) {
        return productService.getPriceHistory(productId);
    }
}
