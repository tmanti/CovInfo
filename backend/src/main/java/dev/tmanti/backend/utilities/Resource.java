package dev.tmanti.backend.utilities;

import java.time.LocalDate;

public class Resource {

    private int id;
    private String name;
    private ResourceType type;
    private String location;
    private LocalDate event_date;
    private String comment;

    public Resource(int id, String name, ResourceType type, String location, LocalDate date, String comment){
        this.id = id;
        this.name = name;
        this.type = type;
        this.location = location;
        this.event_date = date;
        this.comment = comment;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id){
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
        return event_date;
    }

    public void setDate(LocalDate date) {
        this.event_date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


}
