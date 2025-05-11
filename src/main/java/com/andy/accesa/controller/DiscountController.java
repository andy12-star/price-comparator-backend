package com.andy.accesa.controller;

import com.andy.accesa.entity.Discount;
import com.andy.accesa.service.api.DiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/discounts")
public class DiscountController {

    private final DiscountService discountService;

    @GetMapping
    public List<Discount> getAllDiscounts() {
        return discountService.findAll();
    }

    @GetMapping("/store/{store}")
    public List<Discount> getDiscountsByStore(@PathVariable String store) {
        return discountService.getDiscountsByStore(store);
    }

    @GetMapping("/best")
    public List<Discount> getBestDiscounts() {
        return discountService.getBestDiscounts();
    }
}
