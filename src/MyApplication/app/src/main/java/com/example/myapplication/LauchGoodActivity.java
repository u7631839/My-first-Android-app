package com.example.myapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.DataController.GoodsTreeTool.AVLCateNode;
import com.example.myapplication.Fragment.LoginFragment;
import com.example.myapplication.Fragment.MyFragment;
import com.example.myapplication.entity.Good;
import com.example.myapplication.tools.GoodAdapter;

import java.util.ArrayList;
import java.util.List;
/**@author Xuejie Dong u7602840
 * Show the good which is published by the login user
 * */
public class LauchGoodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lauch_good);

        // Set up the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayUseLogoEnabled(false);
            toolbar.setTitle("Launched Goods");
        }


        ListView listView = findViewById(R.id.listView);
        Button deleteButton = findViewById(R.id.deleteButton);
        TextView emptyMessage = findViewById(R.id.emptyMessage);

        final List<Good> goodsList = LoginFragment.loginUser != null ? LoginFragment.loginUser.getLauchGoods() : new ArrayList<>();
        //final ArrayAdapter<Good> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, goodsList);
        final GoodAdapter adapter = new GoodAdapter(this, goodsList);

        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listView.isItemChecked(position)) {
                    listView.setItemChecked(position, false);
                } else {
                    listView.setItemChecked(position, true);
                }
                adapter.notifyDataSetChanged();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checkedItemPosition = listView.getCheckedItemPosition();
                if (checkedItemPosition != ListView.INVALID_POSITION) {
                    Good goodToRemove = adapter.getItem(checkedItemPosition);
                    deleteGoodFromAVL(goodToRemove, goodsList);
                    adapter.notifyDataSetChanged();
                    listView.setItemChecked(checkedItemPosition, false);
                    Toast.makeText(LauchGoodActivity.this, "Successfully delete your good", Toast.LENGTH_SHORT).show();
                    if (goodsList.isEmpty()) {
                        listView.setVisibility(View.GONE);
                        emptyMessage.setVisibility(View.VISIBLE);
                    }
                } else {
                    Toast.makeText(LauchGoodActivity.this, "Please select an item first", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (goodsList.isEmpty()) {
            listView.setVisibility(View.GONE);
            emptyMessage.setVisibility(View.VISIBLE);
        } else {
            listView.setVisibility(View.VISIBLE);
            emptyMessage.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Handle the back button on the action bar
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static void deleteGoodFromAVL(Good goodToRemove, List<Good> goodsList) {
        List<String> categories = goodToRemove.getCate();

        for (String category : categories) {
            if (category.isEmpty()) {
                continue;
            }

            AVLCateNode foundNode = MyApplication.goodAVLtree.search(category);
            if (foundNode != null) {
                foundNode.goodList.remove(goodToRemove);

                if (foundNode.goodList.isEmpty()) {
                    MyApplication.goodAVLtree.delNode(category);
                } else {
                    MyApplication.goodAVLtree.delNode(category);
                    MyApplication.goodAVLtree.add(foundNode);
                }
            } else {
                System.out.println("Category not found in AVL tree");
            }
        }

        goodsList.remove(goodToRemove);
    }
}
