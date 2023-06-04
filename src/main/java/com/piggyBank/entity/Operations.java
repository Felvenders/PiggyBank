package com.piggyBank.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
//import java.sql.Date;

@Entity
@Table(name = "operations")
public class Operations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

//    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @Column(name = "amount")
    private Double amount;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Users users;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "operation_type_id")
    private OperationType operationType;

    @Column(name = "operation_type_id", insertable = false, updatable = false)
    private Integer operation_type_id;

    @OneToMany(mappedBy = "operations", fetch = FetchType.LAZY)
    private Set<ExpenseDetails> expenseDetails = new HashSet<>();

    @OneToMany(mappedBy = "operations", fetch = FetchType.LAZY)
    private Set<IncomeDetails> incomeDetails = new HashSet<>();

    public Operations() {
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

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public Integer getOperation_type_id() {
        return operation_type_id;
    }

    public void setOperation_type_id(Integer operation_type_id) {
        this.operation_type_id = operation_type_id;
    }

    public Set<ExpenseDetails> getExpenseDetails() {
        return expenseDetails;
    }

    public void setExpenseDetails(Set<ExpenseDetails> expenseDetails) {
        this.expenseDetails = expenseDetails;
    }

    public Set<IncomeDetails> getIncomeDetails() {
        return incomeDetails;
    }

    public void setIncomeDetails(Set<IncomeDetails> incomeDetails) {
        this.incomeDetails = incomeDetails;
    }


}
