package com.example.rentalapp;

public class PropertyItem {
    private String name;
    private String description;

    public PropertyItem(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
