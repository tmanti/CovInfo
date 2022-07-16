package dev.tmanti.backend;

import dev.tmanti.backend.utilities.DatabaseInteface;
import dev.tmanti.backend.utilities.Resource;
import dev.tmanti.backend.utilities.ResourceType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        //read environment variables
        DatabaseInteface database = new DatabaseInteface();
        SpringApplication.run(BackendApplication.class, args);
    }

}
