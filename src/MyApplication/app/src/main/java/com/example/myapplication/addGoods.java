package com.example.myapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.DataController.AVLTreeToJSONConverter;
import com.example.myapplication.DataController.GoodsTreeTool.AVLCateNode;
import com.example.myapplication.DataController.GoodsTreeTool.AVLCateTree;
import com.example.myapplication.DataController.UsersTreeTool.AVLUserTree;
import com.example.myapplication.Fragment.LoginFragment;
import com.example.myapplication.entity.Good;
import com.example.myapplication.entity.User;

import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * this activity is used to add goods
 * @author Shiyu Pan u7615103
 * */
public class addGoods extends AppCompatActivity {

    private AVLCateTree goodAVLtree;
    private AVLUserTree avlUserTreeAVLtree;

    //good is published by current user so we need this instance
    private User loginUser = LoginFragment.loginUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goods);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //get all the information from items
        EditText editTextGoodId = findViewById(R.id.editTextGoodId);
        EditText editTextName = findViewById(R.id.editTextName);
        EditText editTextCategory = findViewById(R.id.editTextCategory);
        EditText editTextPrice = findViewById(R.id.editTextPrice);
        EditText editTextBrand = findViewById(R.id.editTextBrand);
        Button buttonSubmit = findViewById(R.id.buttonSubmit);

        //click the button to submit the new good
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("click add button");
                String goodId = editTextGoodId.getText().toString();
                String name = editTextName.getText().toString();
                String category = editTextCategory.getText().toString();
                String price = editTextPrice.getText().toString();
                String brand = editTextBrand.getText().toString();
/*                String goodId = editTextGoodId.getText().toString().toLowerCase();
                String name = editTextName.getText().toString().toLowerCase();
                String category = editTextCategory.getText().toString().toLowerCase();
                String price = editTextPrice.getText().toString().toLowerCase();
                String brand = editTextBrand.getText().toString().toLowerCase();*/
                goodAVLtree = MyApplication.goodAVLtree;
                avlUserTreeAVLtree = MyApplication.userTree;
                //force user to fill out all the content
                if (goodId.equals("")){
                    Toast.makeText(addGoods.this, "Fill out ID", Toast.LENGTH_SHORT).show();
                } else if (name.equals("")) {
                    Toast.makeText(addGoods.this, "Fill out Name", Toast.LENGTH_SHORT).show();
                } else if (category.equals("")) {
                    Toast.makeText(addGoods.this, "Fill out Category", Toast.LENGTH_SHORT).show();
                } else if (brand.equals("")) {
                    Toast.makeText(addGoods.this, "Fill out Brand", Toast.LENGTH_SHORT).show();
                } else if (price.equals("")) {
                    Toast.makeText(addGoods.this, "Fill out Price", Toast.LENGTH_SHORT).show();
                } else {
                    //because some reason,we need to recreate the instance
                    Good eachGood = new Good(goodId, name, category, Double.parseDouble(price), 0, brand);
                    eachGood.setUid(loginUser.getUid());
                    eachGood.setRegisterTime(System.currentTimeMillis());
                    eachGood.setIsDelete(0);
                    // Good good1 = new Good(good.getUid(), good.getName(), good.getCategory(), good.getPrice(),good.getRegisterTime(), good.getIsDelete(), good.getClicks(), good.getBrand(),good.getGid());
                    System.out.println(eachGood.getName());
                    //insert(eachGood);
                    //AVLCateNode search = goodAVLtree.search(category);
                    //if loginUser is null, user can not add new goods, so jump to login
                    if(loginUser!=null){
                        System.out.println(loginUser.getEmail());
                        List<Good> lauchGoods = loginUser.getLauchGoods();
                        User origin = loginUser;
                        lauchGoods.add(eachGood);
                        loginUser.setLauchGoods(lauchGoods);
                        avlUserTreeAVLtree.changeNodeValue(origin,loginUser);
                        DatabaseController controller = DatabaseController.getInstance();
                        controller.setUser(loginUser);
                        controller.addGood(eachGood);
                        Toast.makeText(addGoods.this, "Successfully lauched your good", Toast.LENGTH_SHORT).show();
                    }else {
                        System.out.println("loginUser为空");
                        Toast.makeText(addGoods.this, "Please login first", Toast.LENGTH_SHORT).show();
                    }


                    //  if(MyApplication.goodAVLtree.search(cate)!)

                    //    MyApplication.goodAVLtree.add(new AVLCateNode(eachGood.getCate()));
                    //

                }

            }
        });
    }
    /**
     * insert function using to insert new good into tree
     * */
    public static void insert(Good eachGood){
        List<String> cate = eachGood.getCate();

        for (String s : cate) {
            if(s.isEmpty()){
                continue;
            }
            AVLCateNode search = MyApplication.goodAVLtree.search(s);
            if(search!=null){
                System.out.println(search);
                MyApplication.goodAVLtree.delNode(s);
                search.goodList.add(eachGood);

                MyApplication.goodAVLtree.add(search);
                System.out.println("test1");
            }else {
                List<Good> objects = new ArrayList<>();
                objects.add(eachGood);
                MyApplication.goodAVLtree.add(new AVLCateNode(s,objects));
                System.out.println("test2");
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}