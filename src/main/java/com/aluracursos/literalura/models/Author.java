package com.aluracursos.literalura.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "Authors")
public class Author {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(name = "name_author")private String name;
    private Integer birth_date;
    private Integer death_date;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> books = new ArrayList<>();

    public Author(){}

    public Author(AuthorsData dataAuthors) {
        this.name = dataAuthors.nombreAutor();
        this.birth_date = dataAuthors.nacimiento();
        this.death_date = dataAuthors.defuncion();
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
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

    public Integer getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Integer birth_date) {
        this.birth_date = birth_date;
    }

    public Integer getDeath_date() {
        return death_date;
    }

    public void setDeath_date(Integer death_date) {
        this.death_date = death_date;
    }
}
