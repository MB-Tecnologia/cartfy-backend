package com.cartfy.backend.cartfy_backend.services.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

import com.cartfy.backend.cartfy_backend.models.markets.Markets;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MarketServiceType {
    Markets value();
}