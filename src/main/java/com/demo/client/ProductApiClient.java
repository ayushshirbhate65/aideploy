package com.demo.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ProductApiClient {
    private final RestTemplate restTemplate;

    @Value("${product.api.base-url}")
    private String baseUrl;

    public ProductApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String fetchProductsJson() {
        return restTemplate.getForObject(baseUrl + "/api/products", String.class);
    }
}