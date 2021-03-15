package com.budgetingapp.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.SortNatural;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Table(name = "budget")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Budget implements Comparable<Budget> {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "budget", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    @SortNatural
    private Set<Group> groups = new TreeSet<>();

    @ManyToMany()
    @JoinTable(name = "budget_user",joinColumns = @JoinColumn(name = "budget_id")
    ,inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonIgnore
    private Set<User> users = new HashSet<>();

    private LocalDate startDate;

    private LocalDate endDate;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "budget")
    private Set<Transaction> transactions = new HashSet<>();


    private double totalPlanned;

    public Budget() {
    }

    public Budget(Long id, String name, Set<Group> groups, Set<User> users) {
        this.id = id;
        this.name = name;
        this.groups = groups;
        this.users = users;
    }

    @Transient
    public BigDecimal getSpent() {
        double sum = this.getTransactions().stream().filter(tx->tx.getType().equalsIgnoreCase("debit"))
                .filter(tx->tx.getDate().isEqual(this.getStartDate())|tx.getDate().isAfter(this.getStartDate())
                        &&tx.getDate().isEqual(this.getEndDate())|tx.getDate().isBefore(this.getEndDate()))
                .mapToDouble(t -> {
                    if (t.getTotal() == null)
                        return 0.0;
                    else
                        return t.getTotal().doubleValue();
                })
                .sum();
        return BigDecimal.valueOf(sum);
    }

    @Transient
    public double getRemaining () {
        return ((totalPlanned-getSpent().doubleValue())/totalPlanned)*100;
    }

    public double getTotalPlanned() {
        return totalPlanned;
    }

    public void setTotalPlanned(double totalPlanned) {
        this.totalPlanned = totalPlanned;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public int compareTo(Budget budget)
    {
        return this.id.compareTo(budget.getId());
    }
}
