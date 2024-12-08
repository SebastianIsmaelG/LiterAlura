package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    Author findByName(String name);

    @Query("SELECT a FROM Author a WHERE a.birth_date >= :selectDate ORDER BY a.death_date ASC")
    List<Author> authorByDate(@Param("selectDate") int selectDate);


}
