package com.josa.discox.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String albumName;

    @ManyToOne
    @JoinColumn(name = "band_id", nullable = false)
    private Bands band;

    @Column(nullable = false)
    @Digits(integer = 4, fraction = 0)
    private int year;

    @Column(length = 500)
    private String review;

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public void setBand(Bands band) {
        this.band = band;
    }

    public void setYear(int year) {
        this.year = year;
    }
    
    public void setReview(String review) {
        this.review = review;
    }

    public Bands getBand() {
        return this.band;
    }

    public int getYear() {
        return this.year;
    }
    
    public String getReview() {
        return this.review;
    }
    
    public String getAlbumName() {
        return albumName;
    }

    public void setId(Long id) {
        this.id = id;
    }
}