package com.piggyBank.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import java.util.HashSet;
import java.util.Set;

@Entity
@DynamicUpdate
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "email")
    @NotBlank(message = "Поле не может быть пустым!")
    @Length(min = 5, message = "")
    private String email;

    @Column(name = "username")
    @NotBlank(message = "Поле не может быть пустым!")
    private String username;

    @Column(name = "password")
    @NotBlank(message = "Поле не может быть пустым!")
    @Length(min = 5, message = "Пароль слишком короткий!")
    private String password;

    @Column(name = "status")
    private boolean status;

//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinColumn(name = "role_id")
//    private UserRoles userRoles;

    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private Set<Operations> operations = new HashSet<>();

    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private Set<ExpenseDetails> expenseDetails = new HashSet<>();

    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private Set<IncomeDetails> incomeDetails = new HashSet<>();

    public Users() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

/*    public Set<ExpenseCategories> getExpenseCategories() {
        return expenseCategories;
    }

    public void setExpenseCategories(Set<ExpenseCategories> expenseCategories) {
        this.expenseCategories = expenseCategories;
    }*/

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

//    public UserRoles getUserRoles() {
//        return userRoles;
//    }
//
//    public void setUserRoles(UserRoles userRoles) {
//        this.userRoles = userRoles;
//    }

    public Set<Operations> getOperations() {
        return operations;
    }

    public void setOperations(Set<Operations> operations) {
        this.operations = operations;
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
