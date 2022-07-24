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
        double min = 1e9;
        Resource closest = null;
        for(int i = 0; i<resources.size(); i++){
            Resource r = resources.get(i);
            double dist = location_dist(Location, r.getLocation());
            if(dist < min){
                min = dist;
                closest = r;
            }
        }

        return closest;
    }

    private double location_dist(String loc1, String loc2){
        double x1, y1, x2, y2;
        String[] a = loc1.split(":");
        x1 = Double.parseDouble(a[0]);
        y1 = Double.parseDouble(a[1]);
        String[] b = loc2.split(":");
        x2 = Double.parseDouble(b[0]);
        y2 = Double.parseDouble(b[1]);
        return Math.sqrt(Math.pow((x2-x1), 2) + Math.pow((y2-y1), 2));
    }
}
