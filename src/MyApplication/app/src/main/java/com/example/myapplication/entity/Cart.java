package com.example.myapplication.entity;

import com.example.myapplication.Just_test_UI.GoodModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;
/**@author Rong Sun u6591996
 *
 * the cart that contains all categoryui hand good Model List
 * */
public class Cart implements Serializable {
    private String categoryui;
    private List<Good> goodModelList;

    public Cart(String categoryui, List<Good> goodModelList) {
        this.categoryui = categoryui;
        this.goodModelList = goodModelList == null ? new ArrayList<>() : goodModelList;
    }

    public Cart() {
        this.goodModelList = new ArrayList<>();
    }


    public String getCategoryui() {
        return categoryui;
    }

    public void setCategoryui(String categoryui) {
        this.categoryui = categoryui;
    }

    public List<Good> getGoodModelList() {
        return goodModelList;
    }

    public void setGoodModelList(List<Good> goodModelList) {
        this.goodModelList = goodModelList;
    }

    public  void addGoodtoCart(Good good){

        goodModelList.add(good);

        // 创建自定义比较器，按价格从大到小排序
        Comparator<Good> priceComparator = (g1, g2) -> Double.compare(g2.getPrice(), g1.getPrice());

        // 使用Collections.sort方法排序列表
        Collections.sort(goodModelList, priceComparator);


    }
}
