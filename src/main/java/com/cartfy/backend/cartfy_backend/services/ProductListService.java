package com.cartfy.backend.cartfy_backend.services;

import java.util.Collection;

import com.cartfy.backend.cartfy_backend.models.requests.GetFilterModel;
import com.cartfy.backend.cartfy_backend.models.requests.ListProductRequest;
import com.cartfy.backend.cartfy_backend.models.responses.ListProductsResponse;
import com.cartfy.backend.cartfy_backend.models.responses.OperationResponse;
import com.cartfy.backend.cartfy_backend.models.responses.RetrieveResponse;

public interface ProductListService {
    
    public OperationResponse save(ListProductRequest listProduct);
    public OperationResponse update(long idList, ListProductRequest listProduct);
    public OperationResponse delete(long idUser, String listName);
    public RetrieveResponse<ListProductsResponse> getProductList(long idList);
    public RetrieveResponse<Collection<ListProductsResponse>> getAllProductListByUser(long idUser);
    public RetrieveResponse<Collection<ListProductsResponse>> getProductListByFilter(GetFilterModel filter);    
}
