package com.andy.accesa.service.impl;

import com.andy.accesa.model.entity.Product;
import com.andy.accesa.model.entity.ProductRecommendation;
import com.andy.accesa.model.response.ProductPriceHistoryResponse;
import com.andy.accesa.service.api.ProductService;
import com.andy.accesa.service.data.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
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

    // returns product recommendations by best price/unit
    // filtered by category and by brand(optional)
    @Override
    public List<ProductRecommendation> getRecommendations(String category, String brand) {
        return dataService.getProductsByStore()
                .entrySet()
                .stream()
                .flatMap(entry->entry.getValue().stream()
                        .filter(product -> product.getProduct_category().equalsIgnoreCase(category))
                        .filter(product -> brand == null||product.getBrand().equalsIgnoreCase(brand))
                        .map(product -> ProductRecommendation.builder()
                                .productId(product.getProduct_id())
                                .productName(product.getProduct_name())
                                .brand(product.getBrand())
                                .store(product.getStore())
                                .price(product.getPrice())
                                .package_quantity(product.getPackage_quantity())
                                .package_unit(product.getPackage_unit())
                                .pricePerUnit(product.getPrice()/ product.getPackage_quantity())
                                .build())
                )
                .sorted(Comparator.comparingDouble(ProductRecommendation::getPricePerUnit))
                .toList();
    }

    // returns a list of all the prices a product had across all stores
    @Override
    public List<ProductPriceHistoryResponse> getPriceHistory(String productId) {
        return dataService.getProductsByStore().entrySet().stream()
                .flatMap(entry->entry.getValue().stream()
                        .filter(product -> product.getProduct_id().equalsIgnoreCase(productId))
                        .map(product -> ProductPriceHistoryResponse.builder()
                                .store(entry.getKey())
                                .date(product.getCreatedAt())
                                .price(product.getPrice())
                                .package_quantity(product.getPackage_quantity())
                                .package_unit(product.getPackage_unit())
                                .pricePerUnit(product.getPrice()/product.getPackage_quantity())
                                .build()
                        )
                        .sorted(Comparator.comparing(ProductPriceHistoryResponse::getDate))
                ).toList();
    }

}
