package dev.tmanti.backend.controller;

import dev.tmanti.backend.utilities.DatabaseInteface;
import dev.tmanti.backend.utilities.Resource;
import dev.tmanti.backend.utilities.ResourceType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.lang.reflect.Array;
import java.util.ArrayList;

@RestController
@RequestMapping(path="/api/resources", produces = "application/json")
@CrossOrigin(origins="*")
public class ResourcesController {

    @GetMapping("/")
    @ResponseStatus(code= HttpStatus.OK)
    public ArrayList<Resource> GetAllResources(){
        DatabaseInteface db = DatabaseInteface.getInstance();
        return db.GetResources();
    }

    @GetMapping("/{type}")
    @ResponseStatus(code=HttpStatus.OK)
    public ArrayList<Resource> GetTypedResource(@PathVariable("type") String sType){
        DatabaseInteface db = DatabaseInteface.getInstance();
        if(sType.length() == 1) {
            ResourceType type = ResourceType.valueOf(sType);
            return db.GetResources(type);
        } else {
            return db.GetResources(sType);
        }
    }

    @GetMapping("/{type}/{location}")
    @ResponseStatus(code=HttpStatus.OK)
    public Resource GetClosestTypedResource(@PathVariable("type") String sType, @PathVariable("location") String Location){
        ResourceType type = ResourceType.valueOf(sType);
        DatabaseInteface db = DatabaseInteface.getInstance();
        ArrayList<Resource> resources =  db.GetResources(type);

        //find closest

        throw new NotImplementedException();
    }
}
