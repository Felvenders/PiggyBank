package com.piggyBank.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "income_categories")
public class IncomeCategories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "incomeCategories", fetch = FetchType.LAZY)
    private Set<IncomeDetails> incomeDetails = new HashSet<>();


    public IncomeCategories() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<IncomeDetails> getIncomeDetails() {
        return incomeDetails;
    }

    public void setIncomeDetails(Set<IncomeDetails> incomeDetails) {
        this.incomeDetails = incomeDetails;
    }
}
