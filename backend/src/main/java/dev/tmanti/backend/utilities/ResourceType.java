package dev.tmanti.backend.utilities;

public enum ResourceType {

    TESTS("T"),
    MASKS("M"),
    CLINIC("C"),
    ANNOUNCEMENT("A");

    public final String type;

    ResourceType(String type){
        this.type = type;
    }
}
