package dev.tmanti.backend.controller;

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
    public void AddResource(HttpServletRequest req, @PathVariable String id, @RequestBody Resource new_resource){
        //AUTHENTICATE
        //GRAB TOKEN FROM HEADER
        //DECODE TOKEN AND GET UUID
        //GET USER FROM DB
        //CHECK THAT PRIVILEGE LEVEL IN TOKEN MATCHES FROM DB
        //THEN PROCEED
        //CAN DO IN JWT FUNCTION
        //JUST RETURN BOOLEAN HERE c:

        UUID uuid;
        try {
            uuid = UUID.fromString(id);
        } catch(IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ID");
        }

        new_resource.setId(uuid);

        DatabaseInteface db = DatabaseInteface.getInstance();
        db.UpdateResource(new_resource);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code=HttpStatus.OK)
    public void AddResource(HttpServletRequest req, @PathVariable String id){
        //AUTHENTICATE
        //GRAB TOKEN FROM HEADER
        //DECODE TOKEN AND GET UUID
        //GET USER FROM DB
        //CHECK THAT PRIVILEGE LEVEL IN TOKEN MATCHES FROM DB
        //THEN PROCEED
        //CAN DO IN JWT FUNCTION
        //JUST RETURN BOOLEAN HERE c:

        UUID uuid;
        try {
            uuid = UUID.fromString(id);
        } catch(IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ID");
        }

        DatabaseInteface db = DatabaseInteface.getInstance();
        db.DeleteResource(uuid);
    }

    @PostMapping("/add")
    @ResponseStatus(code=HttpStatus.OK)
    public void AddResource(HttpServletRequest req, @RequestBody Resource new_resource){
        //AUTHENTICATE
        //GRAB TOKEN FROM HEADER
        //DECODE TOKEN AND GET UUID
        //GET USER FROM DB
        //CHECK THAT PRIVILEGE LEVEL IN TOKEN MATCHES FROM DB
        //THEN PROCEED
        //CAN DO IN JWT FUNCTION
        //JUST RETURN BOOLEAN HERE c:

        DatabaseInteface db = DatabaseInteface.getInstance();
        db.AddResource(new_resource);
    }

}
