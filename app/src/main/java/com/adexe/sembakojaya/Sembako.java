package com.adexe.sembakojaya;

public class Sembako {
    private String name;
    private int price;
    private String imageResource;
    private String description;
    Sembako(String name, String description, int price, String imageResource){
        this.name =name;
        this.price = price;
        this.description = description;
        this.imageResource = imageResource;
    }

    String getName() { return name; }
    int getPrice() { return price; }
    String getDeskripsi() {return description;}
    String getImageResource() { return imageResource; }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageResource(String imageResource) {
        this.imageResource = imageResource;
    }


    public void setPrice(int price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }


}
