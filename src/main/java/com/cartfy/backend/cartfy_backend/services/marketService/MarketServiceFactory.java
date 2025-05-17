package com.cartfy.backend.cartfy_backend.services.marketService;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cartfy.backend.cartfy_backend.models.markets.Markets;
import com.cartfy.backend.cartfy_backend.services.annotations.MarketServiceType;

@Component
public class MarketServiceFactory {

    private final Map<Markets, MarketService> mapaServicos = new EnumMap<>(Markets.class);

    @Autowired
    public MarketServiceFactory(List<MarketService> servicos) {
        for (MarketService servico : servicos) {            
            Class<?> targetClass = AopProxyUtils.ultimateTargetClass(servico);
            MarketServiceType anotacao = targetClass.getAnnotation(MarketServiceType.class);
            if (anotacao != null) {
                mapaServicos.put(anotacao.value(), servico);
            }
        }
    }

    public MarketService getService(Markets tipo) {
        MarketService servico = mapaServicos.get(tipo);
        if (servico == null) {
            throw new IllegalArgumentException("Serviço não encontrado para tipo: " + tipo);
        }
        return servico;
    }
}