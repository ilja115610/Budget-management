package com.budgetingapp.domain;

import org.hibernate.annotations.SortNatural;

import javax.persistence.*;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Table(name = "groupp")
public class Group implements Comparable<Group> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "budget_id")
    private Budget budget;


    @OneToMany(mappedBy = "groupp", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @SortNatural
    private Set<Category> categories = new TreeSet<>();

    public Group() {
    }

    public Group(Long id, String name, Budget budget) {
        this.id = id;
        this.name = name;
        this.budget = budget;
    }

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
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

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    @Override
    public int compareTo(Group o)
    {
        int compareTo = 0;
        if (this.getId() != null && o.getId() != null)
            compareTo = this.getId().compareTo(o.getId());

        return compareTo;
    }
}
