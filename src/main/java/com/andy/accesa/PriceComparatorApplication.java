package com.andy.accesa;

import com.andy.accesa.entity.Discount;
import com.andy.accesa.entity.Product;
import com.andy.accesa.util.CsvLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class PriceComparatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(PriceComparatorApplication.class, args);
	}
//
//	// to test if the csv loader works
//	@Bean
//	public CommandLineRunner CsvLoader_test() {
//		return args -> {
//			Path path_products = Paths.get("src/main/resources/csv/products/lidl_2025-05-08.csv");
//			List<Product> products = CsvLoader.loadProducts(path_products, "lidl", LocalDate.of(2025, 5, 8));
//			products.forEach(System.out::println);
//			Path path_discounts = Paths.get("src/main/resources/csv/discounts/lidl_discounts_2025-05-01.csv");
//			List<Discount> discounts = CsvLoader.loadDiscounts(path_discounts, "lidl");
//			discounts.forEach(System.out::println);
//		};
//	}

}
