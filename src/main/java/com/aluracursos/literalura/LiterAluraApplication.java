package com.aluracursos.literalura;

import com.aluracursos.literalura.main.Main;
import com.aluracursos.literalura.repository.AuthorRepository;
import com.aluracursos.literalura.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {

	public static void main(String[] args)
	{
		SpringApplication.run(LiterAluraApplication.class, args);
	}

	@Autowired
	private BooksRepository repository;
	@Autowired
	private AuthorRepository authorRepository;

	public void run(String[] args) {
		Main main = new Main(repository,authorRepository);
		main.mainMenu();
	}
}
