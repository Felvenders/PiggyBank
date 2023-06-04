package com.piggyBank.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "operation_type")
public class OperationType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "operationType", fetch = FetchType.LAZY)
    private Set<Operations> operations = new HashSet<>();

    public OperationType() {
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

    public Set<Operations> getOperations() {
        return operations;
    }

    public void setOperations(Set<Operations> operations) {
        this.operations = operations;
    }
}
