package com.cartfy.backend.cartfy_backend.services;

import java.util.List;

import com.cartfy.backend.cartfy_backend.models.requests.ComparePriceRequest;
import com.cartfy.backend.cartfy_backend.models.responses.ComparePriceListResponse;
import com.cartfy.backend.cartfy_backend.models.responses.ListProductsResponse;
import com.cartfy.backend.cartfy_backend.models.responses.RetrieveResponse;

public interface ComparePriceService {     
    public RetrieveResponse<List<ComparePriceListResponse>> comparePricesList (ComparePriceRequest idLists);
    public RetrieveResponse<ListProductsResponse> getListPricesByMarket (ComparePriceRequest request);
}