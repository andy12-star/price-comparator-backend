package com.andy.accesa.util;

import com.andy.accesa.entity.Discount;
import com.andy.accesa.entity.Product;
import lombok.SneakyThrows;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CsvLoader {

    @SneakyThrows
    public static List<Product> loadProducts(Path filePath, String store, LocalDate date){
        List<Product> products = new ArrayList<>();
        return products;
    }

    @SneakyThrows
    public static List<Discount> loadDiscounts(Path filePath, String store){
        List<Discount> discounts = new ArrayList<>();
        return discounts;
    }
}
