package com.sebastianismaelg.literalura;

import com.sebastianismaelg.literalura.main.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {
	private Main main;

	@Autowired
	public LiterAluraApplication(Main main){
		this.main = main;
	}

	public static void main(String[] args) {

		SpringApplication.run(LiterAluraApplication.class, args);
	}


	public void run(String... args) throws Exception {
		this.main.menu();

	}
}
