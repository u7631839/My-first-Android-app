package com.example.myapplication.entity;

import com.google.type.DateTime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @Author Rong Sun
 * U6591996
 * The user that buy and contact and share goods
 */
public class User implements Comparable<User>, Cloneable, Serializable {
    private  long registerTime;
    private String password;
    private String email;
    private String uid;
    private double lat;
    private double lon;



    private List<Good> lauchGoods = new ArrayList<>();

    private List<Cart> cartList = new ArrayList<>();

    public double getLon() {
        return lon;
    }

    public double getLat() {
        return lat;
    }
    public User(String password, String email, long registerTime, List<Good> lauchGoods, List<Cart> cartList) {
        this.registerTime = registerTime;
        this.password = password;
        this.email = email;

        this.lauchGoods = lauchGoods;
        this.cartList = cartList;
    }


    public long getRegisterTime() {
        return registerTime;
    }



    public void setCartList(List<Cart> cartList) {
        this.cartList = cartList;
    }

    public List<Cart> getCartList() {
        if(cartList==null){
            cartList = new ArrayList<>();
        }
        return cartList;
    }

    public List<Good> getLauchGoods() {
        if(lauchGoods == null){
            lauchGoods = new ArrayList<>();
        }
        return lauchGoods;
    }


    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }


    public void setLauchGoods(List<Good> lauchGoods) {
        this.lauchGoods = lauchGoods;
    }

 //   private TreeMap<String, TreeMap<Double, List<Good>>> goodCart = new TreeMap<>();


/*    public void addGood(Good good) {
        String category = good.getCategory();
        Double price = good.getPrice();

        TreeMap<Double, List<Good>> priceGoodsMap = goodCart.get(category);
        if (priceGoodsMap == null) {
            priceGoodsMap = new TreeMap<>();
            goodCart.put(category, priceGoodsMap);
        }

        List<Good> goodsList = priceGoodsMap.get(price);
        if (goodsList == null) {
            goodsList = new ArrayList<>();
            priceGoodsMap.put(price, goodsList);
        }

        goodsList.add(good);
    }*/

    public void addGood(Good good) {
        String category = good.getCategory();
        boolean hasCategory = false;

       // TreeMap<Double, List<Good>> priceGoodsMap = goodCart.get(category);
        for (Cart eachCart : this.cartList) {
            if(eachCart.getCategoryui().equals(category)){
                this.cartList.remove(eachCart);
                eachCart.addGoodtoCart(good);
                this.cartList.add(eachCart);
                hasCategory = true;
            }
        }
        if(hasCategory==false){
            List<Good> goodList = new ArrayList<>();
            goodList.add(good);
            Cart cart = new Cart(category, goodList);
            cartList.add(cart);
        }

    }

/*
    public TreeMap<String, TreeMap<Double, List<Good>>> getGoodCart() {
        return goodCart;
    }
*/

    public String getUid() {
        return uid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)

    }
    public User(String[] info){

    }

    public User(String password, String email, long registerTime) {
        this.password = password;
        this.email = email;
        this.registerTime = registerTime;

    }


    public User(String password, String email, String uid, List<Cart> goodCart) {
        this.password = password;
        this.email = email;
        this.uid = uid;
        this.cartList = goodCart;

    }

    public User(long registerTime, String password, String email, String uid, List<Good> lauchGoods, List<Cart> cartList) {
        this.registerTime = registerTime;
        this.password = password;
        this.email = email;
        this.uid = uid;
        this.lauchGoods = lauchGoods;
        this.cartList = cartList;
    }

    public User(long registerTime, String password, String email, String uid, double lat, double lon, List<Good> lauchGoods, List<Cart> cartList) {
        this.registerTime = registerTime;
        this.password = password;
        this.email = email;
        this.uid = uid;
        this.lat = lat;
        this.lon = lon;
        this.lauchGoods = lauchGoods;
        this.cartList = cartList;
    }

    public void setRegisterTime(long registerTime) {
        this.registerTime = registerTime;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public User(String password, String email) {
        this.password = password;
        this.email = email;
    }

    public User( String email,String password, String uid) {
        this.password = password;
        this.email = email;
        this.uid = uid;
        this.registerTime=System.currentTimeMillis();
        this.cartList=new ArrayList<>();
        this.lauchGoods=new ArrayList<>();
    }

    @Override
    public int compareTo(User user) {
        if (this == null && user == null) {
            return 0; // 两个对象都为null，认为它们相等
        } else if (this == null) {
            return -1; // 当前对象为null，认为它小于非null对象
        } else if (user == null) {
            return 1; // 非null对象小于null对象
        }

        // 使用hashCode进行比较
        return Integer.compare(this.hashCode(), user.hashCode());
    }
    
/*    public void toStringCart(){
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, TreeMap<Double, List<Good>>> categoryEntry : goodCart.entrySet()) {
            String category = categoryEntry.getKey();
            sb.append("Category: ").append(category).append("\n");

            TreeMap<Double, List<Good>> priceGoodsMap = categoryEntry.getValue();
            for (Map.Entry<Double, List<Good>> priceEntry : priceGoodsMap.entrySet()) {
                Double price = priceEntry.getKey();
                sb.append("  Price: ").append(price).append("\n");

                List<Good> goodsList = priceEntry.getValue();
                for (Good good : goodsList) {
                    sb.append("    ").append(good.toString()).append("\n");
                }
            }
        }
        System.out.println(sb.toString());

    }*/
}
