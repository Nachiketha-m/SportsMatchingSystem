package com.example;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

@Entity
@Table(name = "elo")
public class Elo {
    @Id
    private Long userId;

    @Column(name = "rating")
    private int rating;

    // Constructors, getters, and setters
    // Default constructor
    public Elo() {
        // Default constructor
    }

    // Parameterized constructor
    public Elo(Long userId, int rating) {
        this.userId = userId;
        this.rating = rating;
    }

    // Getters and setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}

