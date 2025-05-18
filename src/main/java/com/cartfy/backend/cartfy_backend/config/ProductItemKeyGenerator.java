package com.cartfy.backend.cartfy_backend.config;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import com.cartfy.backend.cartfy_backend.entities.ProductItem;

@Component("productItemKeyGenerator")
public class ProductItemKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        if (params.length == 0 || !(params[0] instanceof List<?>)) {
            return "vazio";
        }

        List<?> lista = (List<?>) params[0];

        return lista.stream()
            .map(obj -> {
                if (obj instanceof ProductItem) {
                    ProductItem productItem = (ProductItem) obj;
                    String gtin = productItem.getGtin();         
                    return gtin != null ? gtin.toString() : "null";
                }
                return "x";
            })
            .collect(Collectors.joining("-"));
    }
}