package main.Entity;

import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "Tags")
public class Tags {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private double weight;

    public double getWeight() {
        return weight;
    }

    public void setWeight(@NonNull double weight) {
        this.weight = weight;
    }

    public int getId() {
        return id;
    }
    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }


}
