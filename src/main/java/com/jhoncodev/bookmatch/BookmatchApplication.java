package com.jhoncodev.bookmatch;

import com.jhoncodev.bookmatch.main.Main;
import com.jhoncodev.bookmatch.repository.IBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookmatchApplication implements CommandLineRunner {

	@Autowired
	private IBookRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(BookmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Main main = new Main(repository);
		main.showMenu();
	}
}
