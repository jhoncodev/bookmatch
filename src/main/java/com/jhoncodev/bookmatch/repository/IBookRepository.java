package com.jhoncodev.bookmatch.repository;

import com.jhoncodev.bookmatch.model.Author;
import com.jhoncodev.bookmatch.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IBookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitle(String title);

    List<Book> findByLanguage(String language);

    @Query("SELECT a FROM Author a")
    List<Author> listAuthors();

    @Query("SELECT a FROM Author a WHERE a.birthdate < :year AND a.deathdate > :year")
    List<Author> listAuthorsByYear(int year);
}
