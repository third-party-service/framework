package com.jzg.framework.utils.test.model;

import java.util.List;


public class ProductAndImage {
    private List<ProductImage> productImages;

    private Product product;

    public List<ProductImage> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<ProductImage> productImages) {
        this.productImages = productImages;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}

