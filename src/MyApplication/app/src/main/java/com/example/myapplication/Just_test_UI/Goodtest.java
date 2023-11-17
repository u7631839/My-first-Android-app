package com.example.myapplication.Just_test_UI;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Xuejie Dong u7602840
 * UI test fopr good
 */
public class Goodtest {
    public static List<GoodModel> generateGoodModels() {
        List<GoodModel> goodModels = new ArrayList<>();


        goodModels.add(new GoodModel("aa", "好吃", R.drawable.img));
        goodModels.add(new GoodModel("bb", "黄色", R.drawable.img));
        goodModels.add(new GoodModel("cc", "圆的", R.drawable.img));

        return goodModels;
    }
    public static List<CartUI> cartUIS(){
        List<CartUI> cartUIS = new ArrayList<>();
        cartUIS.add(new CartUI("裤子",generateGoodModels()));
        return cartUIS;
    }

}
