package com.sebastianismaelg.literalura.models;


import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class Books {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long Id;
    @Column(unique = true) private String author;
    private String title;
    private String languages;
    private int downloads;
}
