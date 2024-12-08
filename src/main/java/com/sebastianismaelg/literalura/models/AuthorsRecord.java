package com.sebastianismaelg.literalura.models;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AuthorsRecord(
        @JsonAlias("name") String nameAuthor,
        @JsonAlias("birth_year") Integer birthDateAuthor,
        @JsonAlias("death_year") Integer deathDateAuthor
) {
}
