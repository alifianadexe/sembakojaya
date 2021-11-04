package com.adexe.sembakojaya;

public class Sembako {
    private String name;
    private int price;
    private final int imageResource;
    private String description;
    Sembako(String name, String description, int price, int imageResource){
        this.name =name;
        this.price = price;
        this.description = description;
        this.imageResource = imageResource;
    }

    String getName() { return name; }
    int getPrice() { return price; }
    int getImageResource() { return imageResource; }
}
