package com.example.androidpractice.easycloud;

/**
 * Created by anuj on 6/4/2016.
 */
public class DropletModel {

    String image,title,memory,disk,region,status;

    public DropletModel(){

    }


    public DropletModel(String image, String title, String region) {

        this.image = image;
        this.title = title;
        this.region = region;

    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return "DropletModel{" +
                "image='" + image + '\'' +
                ", title='" + title + '\'' +
                ", memory='" + memory + '\'' +
                ", disk='" + disk + '\'' +
                ", region='" + region + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
