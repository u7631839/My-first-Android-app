package com.example.myapplication.Fragment;

import static android.content.Intent.getIntent;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.myapplication.DataController.GoodsTreeTool.AVLCateNode;
import com.example.myapplication.DataController.GoodsTreeTool.AVLCateTree;
import com.example.myapplication.DataController.UsersTreeTool.AVLUserNode;
import com.example.myapplication.DataController.UsersTreeTool.AVLUserTree;
import com.example.myapplication.DatabaseController;
import com.example.myapplication.MyApplication;
import com.example.myapplication.R;
import com.example.myapplication.SearchActivity;
import com.example.myapplication.entity.Good;
import com.example.myapplication.entity.User;
import com.example.myapplication.tools.MyAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @Author Xuejie Dong u7602840
 * see the recommend goods in the first page
 * now the recommend good is "iron" label goods
 */
public class HomePage extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    public static AVLCateTree goodAVLtree;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home_page, container, false);
        view = rootView.findViewById(R.id.searcha);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNextActivity();
            }
        });
        recyclerView = rootView.findViewById(R.id.recyclerView_home);
        // Generate a list of products
        List<Good> productList = generateProductList("Iron"); // 生成商品列表数据
        myAdapter = new MyAdapter(getContext(), productList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(myAdapter);

        // ItemTouchHelper setup for swipe action
        ItemTouchHelper.Callback itemTouchHelperCallback = new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {

                int swipeFlags = ItemTouchHelper.LEFT;
                return makeMovementFlags(0, swipeFlags);
            }
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                int position = viewHolder.getAdapterPosition();
                Good product = productList.get(position);
                if (LoginFragment.loginUser != null) {
                Intent intent = new Intent(getActivity(), HomePage.class);
                intent.putExtra("good",product);
                product.setSwiped(true);
                myAdapter.notifyItemChanged(position);
               // Intent intent = getIntent();
                /*Activity activity = getActivity();
                Intent intent1 = activity.getIntent();
                User loginUser = (User) intent1.getSerializableExtra("loginUser");*/
                DatabaseController controller = DatabaseController.getInstance();
                addGoodIntoCart(LoginFragment.loginUser, product);
                //controller.setUser(LoginFragment.loginUser);
                //更新loginUser!
                }else {
                    Toast.makeText(getActivity(), "Please log in to add items to cart", Toast.LENGTH_SHORT).show();
                    ViewPager viewPager = getActivity().findViewById(R.id.viewpageer);
                    if (viewPager != null) {
                        viewPager.setCurrentItem(3);
                    }

                }
            }
            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    View itemView = viewHolder.itemView;

                    // Calculate the position for the swipe actions layout
                    float buttonWidth = -dX; // Adjust this value to control the button width

                    // Create a Paint object for drawing the background
                    Paint backgroundPaint = new Paint();
                    backgroundPaint.setColor(Color.parseColor("#FF673AB7")); // Set the background color

                    // Draw the background
                    c.drawRect(itemView.getRight() + dX, itemView.getTop(), itemView.getRight(), itemView.getBottom(), backgroundPaint);

                    // Create a Paint object for drawing text
                    Paint textPaint = new Paint();
                    textPaint.setColor(Color.WHITE); // Set text color
                    textPaint.setTextSize(36); // Set text size

                    // Calculate text position to center it vertically and horizontally
                    float textX = itemView.getRight() + dX + (buttonWidth - textPaint.measureText("Add to Cart")) / 2;
                    float textY = itemView.getTop() + itemView.getHeight() / 2;

                    // Draw the "Add to Cart" text
                    c.drawText("Add to Cart", textX, textY, textPaint);
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

        };


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);


        return rootView;
    }

    // Navigate to the next activity
    private void goToNextActivity() {
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        startActivity(intent);
    }



    private List<Good> generateProductList(String c) {

        List<Good> searchResults = new ArrayList<>();
        this.goodAVLtree = MyApplication.goodAVLtree;

        AVLCateNode targetNode = this.goodAVLtree.search(c.toLowerCase(Locale.ROOT));
        if(targetNode!=null){
            for (Good good : targetNode.goodList) {

                searchResults.add(good);

            }
        }else {
        }

        return searchResults;
    }

    //add goods into good cart TO BE CONTINUE
    public static void addGoodIntoCart(User originUser, Good good){
        // Logic for adding goods to the cart...

            User newUser = new User(originUser.getRegisterTime(), originUser.getPassword(), originUser.getEmail(), originUser.getUid(), originUser.getLat(), originUser.getLon(),  originUser.getLauchGoods(),originUser.getCartList());
        // newUser = user;
        newUser.addGood(good);
        AVLUserTree userTree = MyApplication.userTree;
        userTree.changeNodeValue(originUser,newUser);


        AVLUserNode search = userTree.search(newUser);
        //update the loginUser
        LoginFragment.loginUser = search.value;
        //Check the search
        if(search!=null){

           // search.value.toStringCart();
        }else{

        }

        page1Fragment.initgoodlist();
        DatabaseController controller=DatabaseController.getInstance();
        controller.setUser(LoginFragment.loginUser);

    }
}


