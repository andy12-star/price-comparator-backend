package com.andy.accesa.controller;

import com.andy.accesa.model.request.BasketRequest;
import com.andy.accesa.model.response.BasketResponse;
import com.andy.accesa.service.api.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/basket")
public class BasketController {
    private final BasketService basketService;

    @PostMapping("/best")
    public BasketResponse bestBasket(@RequestBody BasketRequest basketRequest) {
        return basketService.findBestBasket(basketRequest);
    }

}
