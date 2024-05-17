package com.qindel.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "PRICES")
@Data
@AllArgsConstructor
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "BRAND_ID")
    private Long brandId;

    @Column(name = "START_DATE")
    private LocalDateTime startDate;

    @Column(name = "END_DATE")
    private LocalDateTime endDate;

    @Column(name = "PRICE_LIST")
    private Long priceList;

    @Column(name = "PRODUCT_ID")
    private Long productId;

    @Column(name = "PRIORITY")
    private Integer priority;

    @Column(name = "PRICE")
    private Double price;

    @Column(name = "CURR")
    private String currency;
    public Price(Long brandId, LocalDateTime startDate, LocalDateTime endDate, Long priceList, Long productId, Integer priority, Double price, String curr) {
        this.brandId = brandId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.priceList = priceList;
        this.productId = productId;
        this.priority = priority;
        this.price = price;
        this.currency = curr;
    }

    public Price (){}
}