package com.example.rentalapp.Entities;

public class Property {

    private int id;
    private String name;
    private String description;
    private double rent;
    private String type;
    private String rentalType;
    private String image;
    public Property() {}
    public Property(int id, String name, String description, double rent, String type, String rentalType, String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.rent = rent;
        this.type = type;
        this.rentalType = rentalType;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public double getRent() {
        return rent;
    }

    public void setRent(double rent) {
        this.rent = rent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRentalType() {
        return rentalType;
    }

    public void setRentalType(String rentalType) {
        this.rentalType = rentalType;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Property{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", rent=" + rent +
                ", type='" + type + '\'' +
                ", rentalType='" + rentalType + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
