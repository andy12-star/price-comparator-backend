package com.andy.accesa.service.data;

import com.andy.accesa.entity.Discount;
import com.andy.accesa.entity.Product;
import com.andy.accesa.util.CsvLoader;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.andy.accesa.util.CsvLoader.loadDiscounts;
import static com.andy.accesa.util.CsvLoader.loadProducts;

@Service
public class DataService {

    public static final String PRODUCTS = "src/main/resources/csv/products";
    public static final String DISCOUNTS = "src/main/resources/csv/discounts";

    private final Map<String, List<Product>> productsByStore = new HashMap<>();
    private final Map<String, List<Discount>> discountsByStore = new HashMap<>();

    public Map<String, List<Product>> getProductsByStore() {
        return productsByStore;
    }

    public Map<String, List<Discount>> getDiscountsByStore() {
        return discountsByStore;
    }

    @PostConstruct
    public void init() throws IOException {
        loadProducts();
        loadDiscounts();
        System.out.println("There are " + productsByStore.size() + " products");
        System.out.println("There are " + discountsByStore.size() + " discounts");
    }

    private void loadProducts() throws IOException {
        Path productsPath = Paths.get(PRODUCTS);
        if(!Files.exists(productsPath)) return;
        try(DirectoryStream<Path> directoryStream = Files.newDirectoryStream(productsPath)) {
            for (Path file : directoryStream) {
                String fileName = file.getFileName().toString();
                if(!fileName.endsWith(".csv")) continue;
                String storeName = fileName.substring(0, fileName.length() - 4);
                String date = fileName.substring(fileName.length() - 4, fileName.length());
                LocalDate localDate = LocalDate.parse(date);

                List<Product> products = CsvLoader.loadProducts(productsPath, storeName, localDate);
                productsByStore.computeIfAbsent(storeName, k -> new ArrayList<>()).addAll(products);
            }
        }
    }

    private void loadDiscounts() throws IOException {
        Path discountsPath = Paths.get(DISCOUNTS);
        if(!Files.exists(discountsPath)) return;
        try(DirectoryStream<Path> directoryStream = Files.newDirectoryStream(discountsPath)) {
            for (Path file : directoryStream) {
                String fileName = file.getFileName().toString();
                if(!fileName.endsWith(".csv")) continue;
                String storeName = fileName.substring(0, fileName.length() - 4);

                List<Discount> discounts = CsvLoader.loadDiscounts(discountsPath, storeName);
                discountsByStore.computeIfAbsent(storeName, k -> new ArrayList<>()).addAll(discounts);
            }
        }
    }


}
