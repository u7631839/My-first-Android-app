package com.example.myapplication.tools;
import com.example.myapplication.GoodDetailsActivity;
import com.example.myapplication.Just_test_UI.GoodModel;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Location_tool.LocationTool;
import com.example.myapplication.MyApplication;
import com.example.myapplication.R;
import com.example.myapplication.SearchActivity;
import com.example.myapplication.entity.Good;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    // private List<GoodModel> dataList;
    private List<Good> dataGoodList;
    private Context context;

//    public MyAdapter(Context context, List<GoodModel> dataList) {
//        this.context = context;
//        this.dataList = dataList;
//    }

    public MyAdapter(Context context, List<Good> dataGoodList) {
        this.context = context;
        this.dataGoodList = dataGoodList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Good item = dataGoodList.get(position);
        holder.tvName.setText(item.getName());

        holder.tvInfo.setText(item.getBrand()+item.getLat());
        String location="   "+String.format("%.2f",LocationTool.calculateDistance(MyApplication.my_lat,MyApplication.my_lon,item.getLat(),item.getLon()))+"km" ;
        holder.price.setText("$ "+String.valueOf(item.getPrice())+" "+location);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, GoodDetailsActivity.class);
                intent.putExtra("good_name", item.getName());
                intent.putExtra("good_price",item.getPrice());
                intent.putExtra("good_info", item.getBrand());
                System.out.println("UID="+item.getUid());
                intent.putExtra("good_uid", item.getUid());
                System.out.println("GOODUID="+item.getGid());
                context.startActivity(intent);
            }
        });


        // holder.imageView.setImageResource(item.getImageResource());
    }

    @Override
    public int getItemCount() {
        return dataGoodList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvInfo;
        public TextView price;
        public ImageView imageView;
        public FrameLayout containerLayout;
        //public Button addToCartButton;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvInfo = itemView.findViewById(R.id.tv_info);
            price = itemView.findViewById(R.id.cate_info);
            imageView = itemView.findViewById(R.id.tv_img);


        }

        public void bind(Good item) {
            tvName.setText(item.getName());
            tvInfo.setText(item.getBrand());
            // imageView.setImageResource(item.getImageResource());
        }
    }
    public void updateData(List<Good> newDataList) {
        dataGoodList = newDataList;
        notifyDataSetChanged();
    }
}
