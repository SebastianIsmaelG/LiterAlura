package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BooksRepository extends JpaRepository<Book,Long> {

    @Query("SELECT COUNT(l) > 0 FROM Book l WHERE l.title = :title")
    boolean existsByTitulo(String title);

    @Query("SELECT l FROM Book l WHERE l.language ILIKE %:language%")
    List<Book> findByLanguage(String language);
}
