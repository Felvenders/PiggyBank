package com.piggyBank.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "expense_categories")
public class ExpenseCategories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "expenseCategories", fetch = FetchType.LAZY)
    private Set<ExpenseDetails> expenseDetails = new HashSet<>();

/*    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users users;*/

    public ExpenseCategories() {
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

/*    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }*/

    public Set<ExpenseDetails> getExpenseDetails() {
        return expenseDetails;
    }

    public void setExpenseDetails(Set<ExpenseDetails> expenseDetails) {
        this.expenseDetails = expenseDetails;
    }
}
