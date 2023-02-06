package com.jollykai.carcleaner.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Project: CarCleaner
 *
 * @author Dmitry Istomin
 * https://github.com/Jollykai
 */
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    private Date date;
    private String customerData;

    public Order() {
    }

    public Order(long productId, Date date, String customerData) {
        this.productId = productId;
        this.date = date;
        this.customerData = customerData;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Date getDate() {
        return date;
    }

    public void setDare(Date date) {
        this.date = date;
    }

    public String getCustomerData() {
        return customerData;
    }

    public void setCustomerData(String customerData) {
        this.customerData = customerData;
    }
}
