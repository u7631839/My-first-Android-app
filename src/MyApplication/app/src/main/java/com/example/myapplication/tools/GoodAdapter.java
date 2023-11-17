package com.example.myapplication.tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.entity.Good;

import java.util.List;

public class GoodAdapter extends ArrayAdapter<Good> {
    private final Context context;
    private final List<Good> goodsList;

    public GoodAdapter(Context context, List<Good> goodsList) {
        super(context, R.layout.item_launch, goodsList);
        this.context = context;
        this.goodsList = goodsList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_launch, parent, false);
        }

        RadioButton radioButton = convertView.findViewById(R.id.radio_button);
        if (position == ((ListView) parent).getCheckedItemPosition()) {
            radioButton.setChecked(true);
        } else {
            radioButton.setChecked(false);
        }

        TextView tvName = convertView.findViewById(R.id.tv_name);
        TextView tvInfo = convertView.findViewById(R.id.tv_info);
        TextView cateInfo = convertView.findViewById(R.id.cate_info);

        Good currentGood = goodsList.get(position);

        tvName.setText(currentGood.getName());
        tvInfo.setText(currentGood.getBrand());
        cateInfo.setText(String.join(", ", currentGood.getCate()));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((ListView) parent).isItemChecked(position)) {
                    ((ListView) parent).setItemChecked(position, false);
                } else {
                    ((ListView) parent).setItemChecked(position, true);
                }
                notifyDataSetChanged();
            }
        });

        return convertView;
    }
}
