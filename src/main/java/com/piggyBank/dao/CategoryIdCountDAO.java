package com.piggyBank.dao;


public class CategoryIdCountDAO {

    private Double amount;
    private String name;

    public CategoryIdCountDAO() {
    }

    public CategoryIdCountDAO(Double amount, String name) {
        this.amount = amount;
        this.name = name;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
