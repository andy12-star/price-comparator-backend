package com.andy.accesa.service.data;

import com.andy.accesa.model.entity.Discount;
import com.andy.accesa.model.entity.Product;
import com.andy.accesa.util.CsvLoader;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
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

@Service
@Getter
public class DataService {

    // paths to the folders containing product and discount csv files
    public static final String PRODUCTS = "src/main/resources/csv/products";
    public static final String DISCOUNTS = "src/main/resources/csv/discounts";

    // initialize in-memory storage of products and discounts
    private final Map<String, List<Product>> productsByStore = new HashMap<>();
    private final Map<String, List<Discount>> discountsByStore = new HashMap<>();

    // loading the products and discounts from the csv files
    @PostConstruct
    public void init() throws IOException {
        loadProducts();
        loadDiscounts();

        // check if all the products are loading
        productsByStore.forEach((storeName, products) -> {
            System.out.println("Store: " + storeName+" has "+products.size()+" products");
        });
        discountsByStore.forEach((storeName, discounts) -> {
            System.out.println("Store: " + storeName+" has "+discounts.size()+" discounts");
        });
    }

    // populates the productsByStore map with the products from file
    private void loadProducts() throws IOException {
        Path productsPath = Paths.get(PRODUCTS);
        if (!Files.exists(productsPath)) return;

        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(productsPath)) {
            for (Path file : directoryStream) {
                String fileName = file.getFileName().toString();

                if (!fileName.endsWith(".csv") || fileName.startsWith(".")) continue;

                try {
                    // extract store and date from file name
                    String[] parts = fileName.replace(".csv", "").split("_");
                    if (parts.length != 2) continue;

                    String storeName = parts[0].trim();
                    String date = parts[1];
                    LocalDate localDate = LocalDate.parse(date);

                    // load products and groups them by store
                    List<Product> products = CsvLoader.loadProducts(file, storeName, localDate);
                    productsByStore.computeIfAbsent(storeName, k -> new ArrayList<>()).addAll(products);

                } catch (Exception e) {
                    System.out.println("failed to parse file" + fileName + e.getMessage());
                }

            }
        }
    }

    // populates the discountByStore map from the discounts file
    private void loadDiscounts() throws IOException {
        Path discountsPath = Paths.get(DISCOUNTS);
        if(!Files.exists(discountsPath)) return;

        try(DirectoryStream<Path> directoryStream = Files.newDirectoryStream(discountsPath)) {
            for (Path file : directoryStream) {
                String fileName = file.getFileName().toString();

                if (!fileName.endsWith(".csv") || fileName.startsWith(".")) continue;

                try{
                    // extract store from file name
                    String storeName = fileName.split("_")[0].trim();

                    // load discounts and groups them by store
                    List<Discount> discounts = CsvLoader.loadDiscounts(file, storeName);
                    discountsByStore.computeIfAbsent(storeName, k -> new ArrayList<>()).addAll(discounts);
                } catch (Exception e) {
                    System.out.println("failed to parse file" + fileName + e.getMessage());
                }
            }
        }
    }


}
