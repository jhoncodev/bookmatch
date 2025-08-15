package com.jhoncodev.bookmatch.model;

import jakarta.persistence.*;

@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer birthdate;
    private Integer deathdate;
    @ManyToOne
    private Book book;

    public Author() {}

    public Author(DataAuthor dataAuthor) {
        this.name = dataAuthor.name();
        this.birthdate = dataAuthor.birthdate();
        this.deathdate = dataAuthor.deathdate();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Integer birthdate) {
        this.birthdate = birthdate;
    }

    public Integer getDeathdate() {
        return deathdate;
    }

    public void setDeathdate(Integer deathdate) {
        this.deathdate = deathdate;
    }

    @Override
    public String toString() {
        return "Author{" +
                "name='" + name + '\'' +
                ", birthdate=" + birthdate +
                ", deathdate=" + deathdate +
                '}';
    }
}
