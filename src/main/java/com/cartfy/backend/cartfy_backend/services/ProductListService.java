package com.cartfy.backend.cartfy_backend.services;

import java.util.List;

import com.cartfy.backend.cartfy_backend.entities.ProductItem;
import com.cartfy.backend.cartfy_backend.models.markets.Markets;
import com.cartfy.backend.cartfy_backend.models.requests.GetFilterModel;
import com.cartfy.backend.cartfy_backend.models.requests.ListProductRequest;
import com.cartfy.backend.cartfy_backend.models.responses.ListProductsResponse;
import com.cartfy.backend.cartfy_backend.models.responses.OperationResponse;
import com.cartfy.backend.cartfy_backend.models.responses.RetrieveResponse;
import com.cartfy.backend.cartfy_backend.models.responses.UserListsProductsResponse;

public interface ProductListService {
    
    public OperationResponse save(ListProductRequest listProduct);
    public OperationResponse update(long idList, ListProductRequest listProduct);
    public OperationResponse delete(long idList);
    public RetrieveResponse<ListProductsResponse> getProductList(long idList, Markets market);    
    public RetrieveResponse<List<UserListsProductsResponse>> getAllProductListByUser(long idUser, Markets market);
    public RetrieveResponse<List<ListProductsResponse>> getProductListByFilter(GetFilterModel filter);

    public List<ProductItem> getProductsItemsById(long idList);    

}
