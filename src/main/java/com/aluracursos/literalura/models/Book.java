package com.aluracursos.literalura.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Books")
public class Book {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private String autor;
    private String title;
    private String language;
    private Integer downloads;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    public Book(){}

    public Book(ResultsData dataBooks) {

        this.title = dataBooks.titulo();
        this.autor = dataBooks.autorList().get(0).nombreAutor();
        this.language = dataBooks.language().get(0);
        this.downloads = dataBooks.descargas();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }


    public String getAutor() {
        return autor;
    }

    public String getLanguage() {
        return language;
    }

    public Integer getDownloads() {
        return downloads;
    }

    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }
}
