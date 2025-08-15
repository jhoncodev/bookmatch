package com.jhoncodev.bookmatch.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String title;
    private String language;
    private Integer downloadCount;
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Author> authors;

    public Book() {}

    public Book(DataBook dataBook) {
        this.title = dataBook.title();
        this.authors = dataBook.authors().stream()
                .map(Author::new)
                .collect(Collectors.toList());
        this.language = dataBook.languages().getFirst();
        this.downloadCount = dataBook.downloadCount();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        authors.forEach(a -> a.setBook(this));
        this.authors = authors;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", authors=" + authors +
                ", languages=" + language +
                ", downloadCount=" + downloadCount +
                '}';
    }
}
