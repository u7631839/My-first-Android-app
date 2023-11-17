package com.example.myapplication.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Fragment.LoginFragment;
import com.example.myapplication.GoodDetailsActivity;
import com.example.myapplication.Just_test_UI.CartUI;
import com.example.myapplication.Just_test_UI.GoodModel;
import com.example.myapplication.R;
import com.example.myapplication.entity.Cart;
import com.example.myapplication.entity.Good;
import com.example.myapplication.entity.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CartAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<Cart> cartgood;
    public CartAdapter(Context context){
        this.context=context;
    }

    public  void setData(List<Cart>cartgood){
        this.cartgood=cartgood;
        notifyDataSetChanged();
    }
    @Override
    public int getGroupCount() {//Display for category
        if (cartgood != null && cartgood.size() > 0) {
            return cartgood.size();
        } else {
            return 0;
        }
    }
    @Override
    public Object getGroup(int groupPosition) {
        return cartgood.get(groupPosition);
    }
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
    @Override
    public View getGroupView(final int groupPosition, final boolean isExpanded,View convertView, ViewGroup parent){
        GroupViewHolder groupViewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.shopping_cart_category_item, null);

            groupViewHolder = new GroupViewHolder(convertView);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        final Cart cartUI=cartgood.get(groupPosition);
        String good_category=cartUI.getCategoryui();
        Log.d("CartAdapter", "category_name: " + good_category);
        if (good_category != null) {
            groupViewHolder.category_name.setText(good_category);
        } else {
            groupViewHolder.category_name.setText("");
        }
        return convertView;
    }

    static class GroupViewHolder{

        TextView category_name;
        GroupViewHolder(View view) {
            category_name = view.findViewById(R.id.category_name_text_view);
        }
    }
    @Override
    public int getChildrenCount(int groupPosition) {//For the following products
        if (cartgood.get(groupPosition).getGoodModelList() != null && cartgood.get(groupPosition).getGoodModelList().size() > 0) {
            return cartgood.get(groupPosition).getGoodModelList().size();
        } else {
            return 0;
        }
    }
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return cartgood.get(groupPosition).getGoodModelList().get(childPosition);
    }
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent){
        ChildViewHolder childViewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_layout, null);

            childViewHolder = new ChildViewHolder(convertView);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        final Cart cartUI = cartgood.get(groupPosition);
        String good_category=cartUI.getCategoryui();
        final Good goodModel = cartUI.getGoodModelList().get(childPosition);
        String goods_name = goodModel.getName();
        String good_info=goodModel.getBrand();
        String good_price=String.valueOf(goodModel.getPrice());
        Log.d("CartAdapter", "good_name: " + goods_name);
        if (goods_name != null) {
            childViewHolder.goodname.setText(goods_name);
            childViewHolder.goodprice.setText(good_price);
            childViewHolder.goodinfo.setText(good_info);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, GoodDetailsActivity.class);
                    intent.putExtra("good_name", goodModel.getName());
                    intent.putExtra("good_info", goodModel.getBrand());
                    intent.putExtra("good_price", goodModel.getPrice());
                    context.startActivity(intent);
                }
            });
        } else {
            childViewHolder.goodname.setText("");
        }
        return convertView;
    }
    static class ChildViewHolder{

        TextView goodname;
        TextView goodprice;
        TextView goodinfo;
        ChildViewHolder(View view) {

            goodname=view.findViewById(R.id.tv_name);
            goodinfo=view.findViewById(R.id.tv_info);
            goodprice=view.findViewById(R.id.cate_info);
        }
    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
    @Override
    public boolean hasStableIds() {
        return false;
    }
    /*
    public static HashMap<String,List<Good>> getGoodsByCart(User user) {

        for (Map.Entry<String, TreeMap<Double, List<Good>>> categoryEntry : user.getGoodCart().entrySet()) {
            String category = categoryEntry.getKey();
            TreeMap<Double, List<Good>> priceGoodsMap = categoryEntry.getValue();

            List<Good> sortedGoodsByPrice = new ArrayList<>();
            for (Map.Entry<Double, List<Good>> priceEntry : priceGoodsMap.entrySet()) {
                List<Good> goodsList = priceEntry.getValue();
                sortedGoodsByPrice.addAll(goodsList);

                if (itemList.containsKey(category)) {
                    // 如果键已经存在，获取现有列表并添加新商品
                    List<Good> existingList = itemList.get(category);
                    existingList.addAll(sortedGoodsByPrice);
                } else {
                    // 如果键不存在，创建新列表并添加商品
                    itemList.put(category, sortedGoodsByPrice);
                }
            }

            // 按照价格顺序排序
            //sortedGoodsByPrice.sort(Comparator.comparingDouble(Good::getPrice));

            // 将按照价格排序后的商品列表放入 TreeMap 中

        }

        return itemList;
    }
   /*public void getGoodsByCart(User user) {
       for (Map.Entry<String, TreeMap<Double, List<Good>>> categoryEntry : user.getGoodCart().entrySet()) {
           String category = categoryEntry.getKey();
           TreeMap<Double, List<Good>> priceGoodsMap = categoryEntry.getValue();

           List<Good> sortedGoodsByPrice = new ArrayList<>();
           for (Map.Entry<Double, List<Good>> priceEntry : priceGoodsMap.entrySet()) {
               List<Good> goodsList = priceEntry.getValue();
               sortedGoodsByPrice.addAll(goodsList);

               if (itemList.containsKey(category)) {
                   // If the key already exists, get the existing list and add new goods.
                   List<Good> existingList = itemList.get(category);
                   existingList.addAll(sortedGoodsByPrice);
               } else {
                   // If the key doesn't exist, create a new list and add goods.
                   itemList.put(category, sortedGoodsByPrice);
               }
           }
       }
   }*/
}
