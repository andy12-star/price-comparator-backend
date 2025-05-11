package com.andy.accesa.service.impl;

import com.andy.accesa.model.entity.Product;
import com.andy.accesa.service.api.ProductService;
import com.andy.accesa.service.data.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final DataService dataService;

    @Override
    public List<Product> findAll() {
        return dataService.getProductsByStore()
                .values()
                .stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> getProductsByStore(String store) {
        return dataService.getProductsByStore().getOrDefault(store, new ArrayList<>());
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return findAll().stream()
                .filter(p->p.getProduct_name().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

}
