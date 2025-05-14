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
import java.util.NoSuchElementException;
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
        List<Product> products =  dataService.getProductsByStore().getOrDefault(store, new ArrayList<>());

        // if there is no store with that name
        if(products.isEmpty()) {
            throw new NoSuchElementException("There is no product with store: " + store);
        }

        return products;
    }

    @Override
    public List<Product> getProductsByName(String name) {
        List<Product> producst = findAll().stream()
                .filter(p->p.getProduct_name().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());

        // if there is no product found
        if (producst.isEmpty()) {
            throw new NoSuchElementException("No product found with name: " + name);
        }

        return producst;
    }

    // returns product recommendations by best price/unit
    // filtered by category and by brand(optional)
    @Override
    public List<ProductRecommendation> getRecommendations(String category, String brand) {
        List<ProductRecommendation> recommendations = dataService.getProductsByStore()
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

        // if there is no product with that category or brand
        if (recommendations.isEmpty()) {
            throw new NoSuchElementException(
                    "No products found for category: "+ category +(brand!=null ? "and brand: " + brand : "")
            );
        }
        return recommendations;
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
