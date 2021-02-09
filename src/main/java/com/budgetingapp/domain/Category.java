package com.budgetingapp.domain;

import org.hibernate.annotations.SortNatural;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Table(name = "category")
public class Category implements Comparable<Category> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "bedget")
    private BigDecimal budget;

    @Column(name = "name")
    private String name;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "groupp_id")
    private Group groupp;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "category")
    @OrderBy("date DESC ")
    @SortNatural
    private Set<Transaction> transactions = new TreeSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public String getName() {
        return name;
    }

    @Transient
    public BigDecimal getSpent() {
        LocalDate start = this.getGroupp().getBudget().getStartDate();
        LocalDate end = this.getGroupp().getBudget().getEndDate();
        double sum = this.getTransactions().stream().filter(tx->tx.getType().equalsIgnoreCase("debit"))
                .filter(tx->tx.getDate().isEqual(start)|tx.getDate().isAfter(start)
                        &&tx.getDate().isEqual(end)|tx.getDate().isBefore(end))
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
    public BigDecimal income() {
        LocalDate start = this.getGroupp().getBudget().getStartDate();
        LocalDate end = this.getGroupp().getBudget().getEndDate();
        double sum = this.getTransactions().stream()
                .filter(t->t.getType().equalsIgnoreCase("credit"))
                .filter(tx->tx.getDate().isEqual(start)|tx.getDate().isAfter(start)
                        &&tx.getDate().isEqual(end)|tx.getDate().isBefore(end))
                .mapToDouble(tx-> {
                    if (tx.getTotal()==null)
                        return 0.0;
                    else
                        return tx.getTotal().doubleValue();
                }).sum();
        return BigDecimal.valueOf(sum);
    }

    @Transient
    public BigDecimal getRemaining() {
        if(getSpent()==null||getSpent().doubleValue()==0.0||income()==null){
            return BigDecimal.valueOf(0.0);
        }
        return (this.getBudget().subtract(this.getSpent()).add(this.income()));
    }

    public void setName(String name) {
        this.name = name;
    }


    public Group getGroupp() {
        return groupp;
    }

    public void setGroupp(Group groupp) {
        this.groupp = groupp;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public int compareTo(Category o) {
        if(this.name==null||o.name==null){
            return 0;
        }
        return this.name.compareTo(o.name);
    }
}
