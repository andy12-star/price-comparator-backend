package com.andy.accesa.service.api;

import com.andy.accesa.model.entity.Discount;

import java.util.List;

public interface DiscountService {
    List<Discount> findAll();
    List<Discount> getDiscountsByStore(String store);
    List<Discount> getBestDiscounts();

    List<Discount> getNewestDiscounts();
}
