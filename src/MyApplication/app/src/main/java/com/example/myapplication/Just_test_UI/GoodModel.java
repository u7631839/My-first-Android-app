package com.example.myapplication.Just_test_UI;
/**
 * @Author Xuejie Dong u7602840
 * The test UI for Good Model
 */
public class GoodModel {
    private String name;
    private String info;
    private int imageResource;
    public GoodModel(String name, String info, int imageResource) {
        this.name = name;
        this.info = info;
        this.imageResource = imageResource;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    public int getImageResource() {
        return imageResource;
    }

}
