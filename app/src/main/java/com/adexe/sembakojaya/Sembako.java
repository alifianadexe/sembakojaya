package com.adexe.sembakojaya;

public class Sembako {
    private String name;
    private int price;
    private final int imageResource;

    Sembako(String name, int price, int imageResource){
        this.name =name;
        this.price = price;
        this.imageResource = imageResource;
    }

    String getName() { return name; }
    int getPrice() { return price; }
    int getImageResource() { return imageResource; }
}
