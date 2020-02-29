package com.gmail.petrikov05.repository.model;

import java.math.BigDecimal;

public class ItemDetails {

    private BigDecimal price;
    private Long Id;

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setId(Long itemId) {
        this.Id = itemId;
    }

    public Long getId() {
        return Id;
    }

}
