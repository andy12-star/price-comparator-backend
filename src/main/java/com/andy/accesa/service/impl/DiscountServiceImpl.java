package com.andy.accesa.service.impl;

import com.andy.accesa.model.entity.Discount;
import com.andy.accesa.service.api.DiscountService;
import com.andy.accesa.service.data.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DiscountServiceImpl implements DiscountService {

    private final DataService dataService;

    @Override
    public List<Discount> findAll() {
      return dataService.getDiscountsByStore()
              .values()
              .stream()
              .flatMap(List::stream)
              .collect(Collectors.toList());
    }

    @Override
    public List<Discount> getDiscountsByStore(String store) {
        List<Discount> discounts= dataService.getDiscountsByStore()
                .getOrDefault(store, new ArrayList<>());

        // if there is no store with that name
        if (discounts.isEmpty()) {
            throw new NoSuchElementException("No discount found for store: " + store);
        }

        return discounts;
    }

    @Override
    public List<Discount> getBestDiscounts() {
        return findAll().stream()
                .sorted(Comparator.comparingInt(Discount::getPercentage_of_discount).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Discount> getNewestDiscounts() {
        return dataService.getDiscountsByStore()
                .values()
                .stream()
                .flatMap(List::stream)
                .filter(discount -> discount.getFrom_date().isAfter(LocalDate.now().minusDays(3)))
                .toList();
    }
}
