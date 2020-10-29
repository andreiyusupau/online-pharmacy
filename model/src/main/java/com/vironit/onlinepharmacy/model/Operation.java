package com.vironit.onlinepharmacy.model;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id")
    private long id;
    @Column(name = "date")
    private Instant date;
    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User owner;

    public Operation() {
    }

    public Operation(long id, Instant date, User owner) {
        this.id = id;
        this.date = date;
        this.owner = owner;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Operation)) return false;
        Operation operation = (Operation) o;
        return id == operation.id &&
                date.equals(operation.date) &&
                owner.equals(operation.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, owner);
    }

    @Override
    public String toString() {
        return "Operation{" +
                "id=" + id +
                ", date=" + date +
                ", owner=" + owner +
                '}';
    }
}
