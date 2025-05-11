package com.andy.accesa.service.api;

import com.andy.accesa.model.request.BasketRequest;
import com.andy.accesa.model.response.BasketResponse;

public interface BasketService {
    BasketResponse findBestBasket(BasketRequest basketRequest);
}
