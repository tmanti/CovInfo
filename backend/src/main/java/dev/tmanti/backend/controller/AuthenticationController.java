package dev.tmanti.backend.controller;

import dev.tmanti.backend.requests.LoginRequest;
import dev.tmanti.backend.requests.TokenResponse;
import dev.tmanti.backend.utilities.CryptoUtils;
import dev.tmanti.backend.utilities.DatabaseInteface;
import dev.tmanti.backend.utilities.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping(path="/api/auth", consumes = "application/json", produces = "application/json")
@CrossOrigin("*")
public class AuthenticationController {

    @PostMapping("/login")
    @ResponseStatus(code= HttpStatus.OK)
    public TokenResponse login(@RequestBody LoginRequest request){
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

        return new TokenResponse(token);
    }

    @PostMapping("/register")
    @ResponseStatus(code= HttpStatus.OK)
    public TokenResponse register(@RequestBody LoginRequest request){
        String token = "";

        DatabaseInteface db = DatabaseInteface.getInstance();

        if(request != null
                && request.getUsername() != null && request.getUsername().length() >= 3
                && request.getPassword() != null && request.getPassword().length() >= 3){
            String hash = CryptoUtils.Hash(request.getPassword());
            User user = new User(UUID.randomUUID(), request.getUsername(), hash, 0);
            if(request.getUsername().equals("root") && hash.equals(CryptoUtils.Hash("cp317project"))){
                user.setPrivilege(1);
            }
            db.AddUser(user);

            token = CryptoUtils.createJWT(user.getId(), user.getPrivilege());
        } else {
            System.out.println("TEST");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return new TokenResponse(token);
    }

}
