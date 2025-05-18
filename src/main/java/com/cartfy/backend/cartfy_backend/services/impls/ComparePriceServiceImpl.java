package com.cartfy.backend.cartfy_backend.services.impls;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.cartfy.backend.cartfy_backend.entities.ProductItem;
import com.cartfy.backend.cartfy_backend.models.markets.Markets;
import com.cartfy.backend.cartfy_backend.models.requests.ComparePriceRequest;
import com.cartfy.backend.cartfy_backend.models.requests.ProductDto;
import com.cartfy.backend.cartfy_backend.models.responses.ComparePriceListResponse;
import com.cartfy.backend.cartfy_backend.models.responses.ListProductsResponse;
import com.cartfy.backend.cartfy_backend.models.responses.RetrieveResponse;
import com.cartfy.backend.cartfy_backend.services.ComparePriceService;
import com.cartfy.backend.cartfy_backend.services.ProductListService;
import com.cartfy.backend.cartfy_backend.services.marketService.MarketService;
import com.cartfy.backend.cartfy_backend.services.marketService.MarketServiceFactory;

@Service
public class ComparePriceServiceImpl implements ComparePriceService {

    @Autowired
    private  MarketServiceFactory marketServiceFactory;
    @Autowired
    private ProductListService _productListService;


    public ComparePriceServiceImpl(MarketServiceFactory factory){
        this.marketServiceFactory = factory;
    }

    @Override
    // @Cacheable(value = "compareListCache")
    public RetrieveResponse<List<ComparePriceListResponse>> comparePricesList(ComparePriceRequest request) {
        List<ComparePriceListResponse> lists = new ArrayList<>();

        for (Markets market : request.markets()) {
            MarketService marketService = marketServiceFactory.getService(market);
            
            List<ProductItem> productsItems = _productListService.getProductsItemsById(request.idList());
            List<ProductDto> productsDtoFromMarket = marketService.getProductList(productsItems);

            for (int i = 0; i < productsDtoFromMarket.size(); i++) {
                productsDtoFromMarket.get(i).setQuantidade(
                    productsItems.get(i).getQuantidade()
                    );
            }
    
            lists.add(new ComparePriceListResponse(market, productsDtoFromMarket, true, ""));
        }

        return new RetrieveResponse<List<ComparePriceListResponse>>(true, "", Optional.of(lists));
        
    }

    @Override
    public RetrieveResponse<ListProductsResponse> getListPricesByMarket(ComparePriceRequest request) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
