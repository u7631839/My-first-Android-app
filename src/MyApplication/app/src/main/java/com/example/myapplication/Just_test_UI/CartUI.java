package com.example.myapplication.Just_test_UI;

import com.example.myapplication.entity.Good;

import java.util.List;

/**
 * @Author Xuejie Dong u7602840
 * The test the cart UI
 */
public class CartUI {
    private String categoryui;
    private List<GoodModel> goodModelList;

    public CartUI(String categoryui,List<GoodModel>goodModelList) {
        this.categoryui = categoryui;
        this.goodModelList=goodModelList;
    }

    public String getCategoryui(){return categoryui;}
    public List<GoodModel> getGoodModelList(){return goodModelList;}
}
