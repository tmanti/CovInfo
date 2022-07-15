package dev.tmanti.backend;

import dev.tmanti.backend.utilities.DatabaseInteface;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        DatabaseInteface database = new DatabaseInteface();
        SpringApplication.run(BackendApplication.class, args);
    }

}
