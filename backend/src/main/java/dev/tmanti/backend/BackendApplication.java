package dev.tmanti.backend;

import dev.tmanti.backend.utilities.CryptoUtils;
import dev.tmanti.backend.utilities.DatabaseInteface;
import dev.tmanti.backend.utilities.Resource;
import dev.tmanti.backend.utilities.ResourceType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.SecureRandom;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        //read environment variables
        boolean config = false;

        if(!config) {
            DatabaseInteface database = new DatabaseInteface();

            SecureRandom random = new SecureRandom();
            byte[] hash_key = new byte[16];
            random.nextBytes(hash_key);
            byte[] jwt_key = new byte[16];
            random.nextBytes(jwt_key);
            new CryptoUtils(hash_key, jwt_key);
        }

        SpringApplication.run(BackendApplication.class, args);
    }

}
