package com.piggyBank.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
//import java.sql.Date;
import java.util.Date;

@Entity
@Table(name = "expense_details")
public class ExpenseDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "amount")
    private Double amount;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Users users;

    @Column(name = "user_id", insertable = false, updatable = false)
    private Integer user_id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "operation_id")
    private Operations operations;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private ExpenseCategories expenseCategories;

    @Column(name = "operation_id", insertable = false, updatable = false)
    private Integer operation_id;


    @Column(name = "category_id", insertable = false, updatable = false)
    private Integer category_id;

    @Column(name = "note")
    private String note;

    public ExpenseDetails() {
    }

    public ExpenseDetails(Double amount) {
        this.amount = amount;
    }

    public ExpenseDetails(int id, Date date, Double amount, Users users, Operations operations, ExpenseCategories expenseCategories, Integer category_id, String note) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.users = users;
        this.operations = operations;
        this.expenseCategories = expenseCategories;
        this.category_id = category_id;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Operations getOperations() {
        return operations;
    }

    public void setOperations(Operations operations) {
        this.operations = operations;
    }

    public ExpenseCategories getExpenseCategories() {
        return expenseCategories;
    }

    public void setExpenseCategories(ExpenseCategories expenseCategories) {
        this.expenseCategories = expenseCategories;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getOperation_id() {
        return operation_id;
    }

    public void setOperation_id(Integer operation_id) {
        this.operation_id = operation_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

        @Override
    public String toString() {
        if (amount != null) {
            return "Траты: " + amount;
        } else {
            return "Траты: " + 0;
        }
    }
}
