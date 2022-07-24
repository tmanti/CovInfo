package dev.tmanti.backend.controller;

import dev.tmanti.backend.requests.LoginRequest;
import dev.tmanti.backend.utilities.CryptoUtils;
import dev.tmanti.backend.utilities.DatabaseInteface;
import dev.tmanti.backend.utilities.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping(path="/api/auth", produces = "application/json")
@CrossOrigin("*")
public class AuthenticationController {

    @PostMapping("/login")
    @ResponseStatus(code= HttpStatus.OK)
    public String logic(@RequestBody LoginRequest request){
        String token = "";

        DatabaseInteface db = DatabaseInteface.getInstance();

        if(request != null && request.getUsername() != null && request.getPassword() != null){
            String hash = CryptoUtils.Hash(request.getPassword());
            User user = db.GetUser(request.getUsername());
            if(Objects.equals(user.getPasswordhash(), hash)){
                token = CryptoUtils.createJWT(user.getId(), user.getPrivilege());
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return token;
    }

    @PostMapping("/register")
    @ResponseStatus(code= HttpStatus.OK)
    public String register(@RequestBody LoginRequest request){
        String token = "";

        DatabaseInteface db = DatabaseInteface.getInstance();

        if(request != null
                && request.getUsername() != null && request.getUsername().length() >= 5
                && request.getPassword() != null && request.getPassword().length() >= 8){
            String hash = CryptoUtils.Hash(request.getPassword());
            User user = new User(UUID.randomUUID(), request.getUsername(), hash, 0);
            db.AddUser(user);

            token = CryptoUtils.createJWT(user.getId(), user.getPrivilege());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return token;
    }

}
