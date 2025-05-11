package com.andy.accesa.service.api;

import com.andy.accesa.model.entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> findAll();
    List<Product> getProductsByStore(String store);
    List<Product> getProductsByName(String name);
}
