package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Contacts;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.DataController.AVLTreeToJSONConverter;
import com.example.myapplication.DataController.GoodsTreeTool.AVLCateTree;
import com.example.myapplication.DataController.UsersTreeTool.AVLUserTree;
import com.example.myapplication.entity.Good;

import org.checkerframework.checker.guieffect.qual.UI;
import org.json.JSONException;
import org.json.JSONObject;
/**@Xuejie Dong u7602840
 * Show the detail of the goods
 * */
public class GoodDetailsActivity extends AppCompatActivity {

    private String uID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_details);
        ImageView backButton3 = findViewById(R.id.backButton3);
        backButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Intent intent = getIntent();
        if (intent != null) {
            String goodName = intent.getStringExtra("good_name");
            double goodPrice = intent.getDoubleExtra("good_price", 0.0);
            String goodInfo = intent.getStringExtra("good_info");
            String UID = intent.getStringExtra("good_uid");
            System.out.println("detail: "+UID);

            this.uID= UID;

            TextView goodNameTextView = findViewById(R.id.good_name);
            TextView goodPriceTextView = findViewById(R.id.good_price);
            TextView goodinfoTextView = findViewById(R.id.good_info);
            goodNameTextView.setText(goodName);
            goodPriceTextView.setText("$ "+String.valueOf(goodPrice));
            goodinfoTextView.setText(goodInfo);
        }

        AVLCateTree cateTree = MyApplication.goodAVLtree;
        AVLUserTree userTree = MyApplication.userTree;

        Context context = this;

        AVLTreeToJSONConverter converter = new AVLTreeToJSONConverter();

        try {

            JSONObject jsonObject = converter.convertTreeToJSON(userTree, cateTree);

            converter.writeJSONToFile(jsonObject, context);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Button contact_button = findViewById(R.id.contact_button);
        contact_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GoodDetailsActivity.this, ChatActivity.class);
                intent.putExtra("good_uid", uID) ;
                startActivity(intent);
            }
        });
    }

}
