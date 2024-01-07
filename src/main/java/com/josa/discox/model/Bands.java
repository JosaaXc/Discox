package com.josa.discox.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

@Entity
@Table(name = "bands")
public class Bands {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String bandName;

    @Column(nullable = false)
    @Digits(integer = 4, fraction = 0)
    private int bandYear;

    @Column(name = "is_active")
    private boolean isActive;

    @OneToMany(mappedBy = "band", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Album> albums;

    // getters and setters
    public String getBandName() {
        return bandName;
    }
    public int getBandYear() {
        return bandYear;
    }
    public Long getId() {
        return id;
    }

    public void setBandName(String bandName) {
        this.bandName = bandName;
    }
    public void setBandYear(int bandYear) {
        this.bandYear = bandYear;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public boolean getIsActive() {
        return this.isActive;
    }
    
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}