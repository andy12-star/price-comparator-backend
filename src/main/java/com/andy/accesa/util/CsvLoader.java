package com.andy.accesa.util;

import com.andy.accesa.entity.Discount;
import com.andy.accesa.entity.Product;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.SneakyThrows;

import java.io.FileReader;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvLoader {

    @SneakyThrows
    public static List<Product> loadProducts(Path filePath, String store, LocalDate date){
        List<Product> products = new ArrayList<>();
        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
        try( CSVReader reader = new CSVReaderBuilder(new FileReader(filePath.toFile()))
                        .withCSVParser(parser)
                        .build();
                ) {
            reader.readNext();
            String[] line;
            while ((line = reader.readNext()) != null) {
                Product product = Product.builder()
                        .product_id(line[0])
                        .product_name(line[1])
                        .product_category(line[2])
                        .brand(line[3])
                        .package_quantity(Double.parseDouble(line[4]))
                        .package_unit(line[5])
                        .price(Double.parseDouble(line[6]))
                        .currency(line[7])
                        .store(store)
                        .createdAt(date)
                        .build();
                products.add(product);
            }
        }
        return products;
    }

    @SneakyThrows
    public static List<Discount> loadDiscounts(Path filePath, String store){
        List<Discount> discounts = new ArrayList<>();
        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
        try( CSVReader reader = new CSVReaderBuilder(new FileReader(filePath.toFile()))
                .withCSVParser(parser)
                .build();
        )  {
            reader.readNext();
            String[] line;
            while ((line = reader.readNext()) != null) {
                Discount discount = Discount.builder()
                        .product_id(line[0])
                        .product_name(line[1])
                        .brand(line[2])
                        .package_quantity(Double.parseDouble(line[3]))
                        .package_unit(line[4])
                        .product_category(line[5])
                        .from_date(LocalDate.parse(line[6]))
                        .to_date(LocalDate.parse(line[7]))
                        .percentage_of_discount(Integer.parseInt(line[8]))
                        .store(store)
                        .build();
                discounts.add(discount);
            }
        }
        return discounts;
    }
}
