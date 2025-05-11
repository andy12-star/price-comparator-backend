package com.andy.accesa.controller;

import com.andy.accesa.entity.Product;
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

    @GetMapping("/find")
    public List<Product> getProductsByName(@RequestParam String name) {
        return productService.getProductsByName(name);
    }

}
