package com.cartfy.backend.cartfy_backend.services.marketService;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.cartfy.backend.cartfy_backend.entities.ProductItem;
import com.cartfy.backend.cartfy_backend.models.markets.ExtractorRequest;
import com.cartfy.backend.cartfy_backend.models.markets.Markets;
import com.cartfy.backend.cartfy_backend.models.requests.ProductDto;
import com.cartfy.backend.cartfy_backend.services.annotations.MarketServiceType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Component
@MarketServiceType(Markets.EXTRA)
public class ExtraService implements MarketService{

    private String URL_DATABASE = "http://localhost:8000";

    private String CEP = "06444-000";

    WebClient webClient;
    HttpClient client;

    ObjectMapper mapper;

    public ExtraService(){
        webClient = WebClient.create(URL_DATABASE);
        client = HttpClient.newBuilder().build();

        mapper = new ObjectMapper();
    }

    @Override
    // @Cacheable(value = "getProductListMarket", keyGenerator = "productItemKeyGenerator")
    public List<ProductDto> getProductList(List<ProductItem> productsItems) {

        ExtractorRequest requestBody = new ExtractorRequest(productsItems.stream().map(p -> p.getGtin()).toArray(String[]::new), Markets.EXTRA.getValue(), CEP);
                        
        String requestStr;

        List<ProductDto> list = new ArrayList<>();
        try {
            requestStr = mapper.writeValueAsString(requestBody);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL_DATABASE + "/mercado"))    
                    .POST(BodyPublishers.ofString(requestStr))    
                    .header("Content-Type", "application/json")        
                    .build();
                                
            HttpResponse<String> response = client.sendAsync(request, BodyHandlers.ofString()).get();

            ProductDto[] resposta = mapper.readValue(response.body(), ProductDto[].class);        

            return Arrays.asList(resposta);
            
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
    @Cacheable(value = "getProductByGtinExtras")
    public ProductDto getProductByGtin(String gtin) {                  
        try {
            URI uri = new URIBuilder(URL_DATABASE + "/produto/" + gtin)
            .addParameter("cep", "06433220")
            .addParameter("market", String.valueOf(Markets.EXTRA.getValue()))
            .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)    
                    .GET()
                    .build();
                                
            HttpResponse<String> response = client.sendAsync(request, BodyHandlers.ofString()).get();

            if(response.statusCode() == 200){
                ProductDto resposta = mapper.readValue(response.body(), ProductDto.class);        
    
                if (resposta != null) {
                    return resposta;                    
                }            

            }

            
        } catch(Exception e){
            e.printStackTrace();
        }

        return new ProductDto();

    }
    
    public List<ProductDto> getProductByTerm(String term){
                try {
            URI uri = new URIBuilder(URL_DATABASE + "/produto")
            .addParameter("cep", "06433220")
            .addParameter("market", String.valueOf(Markets.EXTRA.getValue()))
            .addParameter("term", term).build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()                    
                    .build();
                                
            HttpResponse<String> response;
            
            response = client.sendAsync(request, BodyHandlers.ofString()).get();
                
            ProductDto[] resposta = mapper.readValue(response.body(), ProductDto[].class);
        
            return Arrays.asList(resposta);
            
        } 
        catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }      
        catch (InterruptedException | ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        
        return null;
    }
}
