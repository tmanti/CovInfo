package dev.tmanti.backend.controller;

import dev.tmanti.backend.utilities.CryptoUtils;
import dev.tmanti.backend.utilities.DatabaseInteface;
import dev.tmanti.backend.utilities.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@RequestMapping(path="/api/resource", produces = "application/json")
@CrossOrigin("*")
public class ResourceController {

    @GetMapping("/{id}")
    @ResponseStatus(code=HttpStatus.OK)
    public Resource GetResource(@PathVariable("id") String id){
        UUID uuid;
        try {
            uuid = UUID.fromString(id);
        } catch(IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ID");
        }

        DatabaseInteface db = DatabaseInteface.getInstance();
        Resource res = db.GetResource(uuid);
        if(res == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found with ID: " + id);
        return res;
    }

    @PutMapping("/{id}")
    @ResponseStatus(code=HttpStatus.OK)
    public void UpdateResource(@RequestHeader("authorization") String token, @PathVariable String id, @RequestBody Resource new_resource){
        if(token != null) {
            if(CryptoUtils.authorizeJWT(token) == 1) {
                UUID uuid;
                try {
                    uuid = UUID.fromString(id);
                } catch (IllegalArgumentException e) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ID");
                }

                new_resource.setId(uuid);

                DatabaseInteface db = DatabaseInteface.getInstance();
                db.UpdateResource(new_resource);
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No Authorization Token");
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code=HttpStatus.OK)
    public void DeleteResource(@RequestHeader("authorization") String token, @PathVariable String id){
        if(token != null) {
            if(CryptoUtils.authorizeJWT(token) == 1) {
                UUID uuid;
                try {
                    uuid = UUID.fromString(id);
                } catch (IllegalArgumentException e) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ID");
                }

                DatabaseInteface db = DatabaseInteface.getInstance();
                db.DeleteResource(uuid);
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No Authorization Token");
        }
    }

    @PostMapping("/add")
    @ResponseStatus(code=HttpStatus.OK)
    public void AddResource(@RequestHeader("authorization") String token, @RequestBody Resource new_resource){
        if(token != null) {
            if(CryptoUtils.authorizeJWT(token) == 1) {
                DatabaseInteface db = DatabaseInteface.getInstance();
                db.AddResource(new_resource);
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No Authorization Token");
        }
    }

}
