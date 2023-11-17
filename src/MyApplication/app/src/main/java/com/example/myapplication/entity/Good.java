package com.example.myapplication.entity;
import com.example.myapplication.MyApplication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * @Author Rong Sun
 * U6591996
 * The share good to present in the sharing platform
 */
public class Good implements Comparable<Good>, Serializable {


    private String uid;
    private List<String> cate=new ArrayList<>();
    private String name;
    private String category;
    private Double price;
    private long registerTime;
    private int isDelete;
    private Integer clicks;
    private String brand;
    private Integer supermarketid;
    private int imageResources;
    private boolean isSwiped;
    private double lon;
    private double lat;


    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    private String gid;

    public String getUid() {
        return uid;
    }

    public long getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(long registerTime) {
        this.registerTime = registerTime;
    }

    public double getLon() {
        return lon;
    }

    public double getLat() {
        return lat;
    }

   /* public Good(String gid, String name, String category, Double price, Integer clicks, String brand, Integer supermarket) {
        this.gid = gid;
        this.name = name;
        this.category = category;
        this.price = price;
        this.clicks = clicks;
        this.brand = brand;
        this.supermarketid = supermarket;
        this.isSwiped = false;
        String[] c=name.split(" ");
        for (String string:c){
            if (!cate.contains(c)){
                cate.add(string.toLowerCase(Locale.ROOT));
            }
        }
        if (!cate.contains(category.toLowerCase(Locale.ROOT))){
            cate.add(category.toLowerCase(Locale.ROOT));
        }
        if (!cate.contains(brand.toLowerCase(Locale.ROOT))){
            cate.add(brand.toLowerCase(Locale.ROOT));
        }
    }*/

    public Good(String uid,  String name, String category, Double price, long registerTime, int isDelete, Integer clicks, String brand,  String gid) {
        this.uid = uid;
        String[] c=name.split(" ");
        for (String string:c){
            if (!cate.contains(c)){
                cate.add(string.toLowerCase(Locale.ROOT));
            }
        }
        if (!cate.contains(category.toLowerCase(Locale.ROOT))){
            cate.add(category.toLowerCase(Locale.ROOT));
        }
        if (!cate.contains(brand.toLowerCase(Locale.ROOT))){
            cate.add(brand.toLowerCase(Locale.ROOT));
        }
        this.name = name;
        this.category = category;
        this.price = price;
        this.registerTime = registerTime;
        this.isDelete = isDelete;
        this.clicks = clicks;
        this.brand = brand;
        this.gid = gid;
    }
    //generate Data
    public Good(String uid,  String name, String category, Double price, long registerTime, int isDelete, Integer clicks, String brand,  String gid, double lon, double lat) {
        this.uid = uid;
        String[] c=name.split(" ");
        for (String string:c){
            if (!cate.contains(c)){
                cate.add(string.toLowerCase(Locale.ROOT));
            }
        }
        if (!cate.contains(category.toLowerCase(Locale.ROOT))){
            cate.add(category.toLowerCase(Locale.ROOT));
        }
        if (!cate.contains(brand.toLowerCase(Locale.ROOT))){
            cate.add(brand.toLowerCase(Locale.ROOT));
        }
        this.name = name;
        this.category = category;
        this.price = price;
        this.registerTime = registerTime;
        this.isDelete = isDelete;
        this.clicks = clicks;
        this.brand = brand;
        this.gid = gid;
        this.lon = lon;
        this.lat = lat;
    }


    public Good(String gid, String name, String category, Double price, Integer clicks, String brand) {
        this.gid = gid;
        this.name = name;
        this.category = category;
        this.price = price;
        this.clicks = clicks;
        this.brand = brand;
        String[] c=name.split(" ");
        for (String string:c){
            if (!cate.contains(c)){
                cate.add(string.toLowerCase(Locale.ROOT));
            }
        }
        if (!cate.contains(category.toLowerCase(Locale.ROOT))){
            cate.add(category.toLowerCase(Locale.ROOT));
        }
        if (!cate.contains(brand.toLowerCase(Locale.ROOT))){
            cate.add(brand.toLowerCase(Locale.ROOT));
        }
        this.lat= MyApplication.my_lat;
        this.lon=MyApplication.my_lon;
        this.registerTime=System.currentTimeMillis();

    }

    //new Good(gid, name, category, price, brand,registerTime,lon,lat,randomUid)
    public Good(String gid, String name, String category, Double price, String brand, long registerTime, double lon, double lat, String uid) {

        this.uid = uid;
        this.name = name;
        this.category = category;
        this.price = price;
        this.registerTime = registerTime;
        this.brand = brand;
        this.lon = lon;
        this.lat = lat;
        this.gid = gid;
    }

    public Good(String gid, String name, String category, Double price, Integer clicks, String brand, Integer supermarketid, int imageResources) {
        this.gid = gid;
        this.name = name;
        this.category = category;
        this.price = price;
        this.clicks = clicks;
        this.brand = brand;
        this.supermarketid = supermarketid;
        this.imageResources = imageResources;
    }

    public Good(String gid, String name, String category, String brand) {
        this.gid = gid;
        this.name = name;
        this.category = category;
        this.brand = brand;
    }

    public Good() {
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }


    public List<String> getCate() {
        return cate;
    }

    public void setCate(List<String> cate) {
        this.cate = cate;
    }

    public Good(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Good good) {
        return 0;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getClicks() {
        return clicks;
    }

    public void setClicks(Integer clicks) {
        this.clicks = clicks;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getSupermarketid() {
        return supermarketid;
    }

    public void setSupermarketid(Integer supermarketid) {
        this.supermarketid = supermarketid;
    }


    public int getImageResources() {
        return imageResources;
    }

    public void setImageResources(int imageResources) {
        this.imageResources = imageResources;
    }

    public void setSwiped(boolean swiped) {
        isSwiped = swiped;
    }

    public boolean isSwiped() {
        return isSwiped;
    }
    @Override
    public String toString() {
        return "Good{" +
                "name='" + name + '\'' +
                '}';
    }


}
