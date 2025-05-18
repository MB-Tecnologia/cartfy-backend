package com.cartfy.backend.cartfy_backend.services.marketService;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.cartfy.backend.cartfy_backend.entities.ProductItem;
import com.cartfy.backend.cartfy_backend.models.markets.ExtractorProductResponse;
import com.cartfy.backend.cartfy_backend.models.markets.ExtractorRequest;
import com.cartfy.backend.cartfy_backend.models.markets.Markets;
import com.cartfy.backend.cartfy_backend.models.requests.ProductDto;
import com.cartfy.backend.cartfy_backend.services.annotations.MarketServiceType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Component
@MarketServiceType(Markets.PAODEACUCAR)
public class PaoDeAcucarService implements MarketService{

    private String URL_DATABASE = "http://localhost:8000/mercado";

    private String CEP = "06444-000";

    WebClient webClient;
    HttpClient client;

    ObjectMapper mapper;

    public PaoDeAcucarService(){
        webClient = WebClient.create(URL_DATABASE);
        client = HttpClient.newBuilder().build();

        mapper = new ObjectMapper();
    }

    @Override
    @Cacheable(value = "getProductListMarket", keyGenerator = "productItemKeyGenerator")
    public List<ProductDto> getProductList(List<ProductItem> productsItems) {

        ExtractorRequest requestBody = new ExtractorRequest(productsItems.stream().map(p -> p.getGtin()).toArray(String[]::new), Markets.PAODEACUCAR.getValue(), CEP);
                        
        String requestStr;

        List<ProductDto> list = new ArrayList<>();
        try {
            requestStr = mapper.writeValueAsString(requestBody);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL_DATABASE))    
                    .POST(BodyPublishers.ofString(requestStr))    
                    .header("Content-Type", "application/json")        
                    .build();
                                
            HttpResponse<String> response = client.sendAsync(request, BodyHandlers.ofString()).get();

            ExtractorProductResponse[] resposta = mapper.readValue(response.body(), ExtractorProductResponse[].class);        

            for (ExtractorProductResponse item : resposta) {                                
                list.add(ProductDto.builder()
                    .name(item.nome())
                    .preco(item.preco())
                    .quantidade(1)
                    .urlImg(item.urlImg())
                    .gtin(String.valueOf(item.gtin())).build());
            }
            
        }

        catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (JsonProcessingException e) {
        // TODO Auto-generated catch block
            e.printStackTrace();
        }    

        return list;
        
    }

    @Override
    public ProductDto getProductByGtin(String gtin) {        
        ExtractorRequest requestBody = new ExtractorRequest(new String[]{gtin}, Markets.ATACADAO.getValue(), CEP);
                        
        String requestStr;        
        try {
            requestStr = mapper.writeValueAsString(requestBody);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL_DATABASE))    
                    .POST(BodyPublishers.ofString(requestStr))    
                    .header("Content-Type", "application/json")        
                    .build();
                                
            HttpResponse<String> response = client.sendAsync(request, BodyHandlers.ofString()).get();

            ExtractorProductResponse[] resposta = mapper.readValue(response.body(), ExtractorProductResponse[].class);        

            if (resposta.length == 1) {
                return ProductDto.builder()
                    .name(resposta[0].nome())
                    .preco(resposta[0].preco())
                    .quantidade(1)
                    .urlImg(resposta[0].urlImg())
                    .gtin(String.valueOf(resposta[0].gtin())).build();
                
            }            
            
        } catch(Exception e){
            e.printStackTrace();
        }

        return ProductDto.builder()
                    .name("")
                    .preco(0)
                    .quantidade(0)
                    .urlImg("")
                    .gtin(String.valueOf(gtin)).build();

    }
    
}
