package com.example.vladimir.uzbekistanexplorer.entity;


public class ContentItem {

    private   String name, description, address, image, images, map;

    public void setMap(String map) {
        this.map = map;
    }

    public String getMap() {
        return map;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return this.description;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public String getAddress(){
        return this.address;
    }

    public void setImage(String image){
        this.image = image;
    }

    public String getImage(){
        return this.image;
    }

    public void setImages(String images){
        this.images = images;
    }

    public String getImages(){
        return this.images;
    }
}
