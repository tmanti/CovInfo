package dev.tmanti.backend;

import dev.tmanti.backend.utilities.CryptoUtils;
import dev.tmanti.backend.utilities.DatabaseInteface;
import dev.tmanti.backend.utilities.Resource;
import dev.tmanti.backend.utilities.ResourceType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.security.SecureRandom;
import java.util.*;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) throws IOException {
        //read environment variables
        String db_url = "jdbc:postgresql://localhost:5432/covinfo", db_user = "postgres", db_pass = "root";

        byte[] hash_key = new byte[16];
        byte[] jwt_key = new byte[16];

        Properties prop = new Properties();
        try{
            FileInputStream input = new FileInputStream(".env");
            prop.load(input);
        } catch(IOException e){
            System.out.println("No config file found!");
        }

        try{
            Writer output = new FileWriter(".env");
            SecureRandom random = new SecureRandom();

            String url = prop.getProperty("url");
            if(url == null){
                prop.setProperty("url", db_url);
            } else {
                db_url = url;
            }

            String dbuser = prop.getProperty("dbuser");
            if(dbuser == null){
                prop.setProperty("dbuser", db_user);
            } else {
                db_user = dbuser;
            }

            String dbpass = prop.getProperty("dbpass");
            if(dbpass == null){
                prop.setProperty("dbpass", db_pass);
            } else {
                db_pass = dbpass;
            }

            String hash = prop.getProperty("jwt_key");
            if(hash == null){
                random.nextBytes(hash_key);
                prop.setProperty("hash_key", CryptoUtils.BytesToHex(hash_key));
            } else {
                hash_key = CryptoUtils.StringtoBytes(hash);
            }

            String jwt = prop.getProperty("jwt_key");
            if(jwt == null){
                random.nextBytes(jwt_key);
                prop.setProperty("jwt_key", CryptoUtils.BytesToHex(jwt_key));
            } else {
                jwt_key = CryptoUtils.StringtoBytes(jwt);
            }

            prop.store(output, "config");
        } catch (IOException ex){
            ex.printStackTrace();
            SecureRandom random = new SecureRandom();

            random.nextBytes(hash_key);

            random.nextBytes(jwt_key);
        }

        System.out.println(db_url + db_user + db_pass);

        new DatabaseInteface(db_url, db_user, db_pass);
        new CryptoUtils(hash_key, jwt_key);

        SpringApplication.run(BackendApplication.class, args);
    }

}
