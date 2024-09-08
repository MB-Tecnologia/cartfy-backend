package com.cartfy.backend.cartfy_backend.models.products;


import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCosmos {
    String description;
    Long gtin;
    String thumbnail;

    double width;
    double height;
    double length;
    double net_weight;
    double gross_weight;

    String price;
    double avg_price;
    double max_price;
    double min_price;
    List<Gtin> gtins;

    String origin;
    String barcodeImage;

    Brand brand;
    Gpc gpc;
    Ncm ncm;
}   
