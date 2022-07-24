package dev.tmanti.backend.utilities;

import java.time.LocalDate;
import java.util.UUID;

public class Resource {

    private UUID id;
    private String name;
    private ResourceType type;
    private String location = null;
    private LocalDate date = null;
    private String comment = null;

    public Resource(){}

    public Resource(UUID id, String name, ResourceType type, String location, LocalDate date, String comment){
        this.id = id;
        this.name = name;
        this.type = type;
        this.location = location;
        this.date = date;
        this.comment = comment;
    }

    public UUID getId(){
        return this.id;
    }

    public void setId(UUID id){
        this.id = id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ResourceType getType() {
        return type;
    }

    public void setType(ResourceType type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
