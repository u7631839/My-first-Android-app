package com.example.myapplication;


import static com.example.myapplication.Fragment.HomePage.addGoodIntoCart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.DataController.GoodsTreeTool.AVLCateNode;
import com.example.myapplication.DataController.GoodsTreeTool.AVLCateTree;

import com.example.myapplication.Fragment.LoginFragment;

import com.example.myapplication.Parser.Expression.BrandExp;
import com.example.myapplication.Parser.Expression.CategoryExp;
import com.example.myapplication.Parser.Expression.ClicksExp;
import com.example.myapplication.Parser.Expression.Exp;
import com.example.myapplication.Parser.Expression.NameExp;
import com.example.myapplication.Parser.Expression.PriceExp;
import com.example.myapplication.Parser.Parser;
import com.example.myapplication.Parser.SearchTokenizer;

import com.example.myapplication.entity.Good;

import com.example.myapplication.tools.MyAdapter;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This activity is use to search goods
 * @author Xuejie Dong u7602840
 * */
public class SearchActivity extends AppCompatActivity {
    private SearchTokenizer tokenizer;
    private Parser parser;

    private ArrayList<Exp> queryAttributes = new ArrayList<>();
    private List<Good> goodSourceList = new ArrayList<>();
    private List<Good> searchResultList = new ArrayList<>();
    private RecyclerView recyclerView;
    private LinearLayout emptyDataLayout;


    private MyAdapter adapter;

    public static AVLCateTree goodAVLtree;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        recyclerView = findViewById(R.id.recyclerView);
        emptyDataLayout = findViewById(R.id.ly_nodata);
        adapter = new MyAdapter(this, searchResultList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        ItemTouchHelper.Callback itemTouchHelperCallback = new ItemTouchHelper.Callback() {//Swipe left
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int swipeFlags = ItemTouchHelper.LEFT;
                return makeMovementFlags(0, swipeFlags);
            }
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            //using swip to select a good into cart
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Good product = searchResultList.get(position);
                if (LoginFragment.loginUser != null) {
                    Intent intent = new Intent(SearchActivity.this, SearchActivity.class);
                    intent.putExtra("good", product);
                    product.setSwiped(true);
                    adapter.notifyItemChanged(position);

                    addGoodIntoCart(LoginFragment.loginUser, product);

                }else {
                    Toast.makeText(SearchActivity.this, "Please log in to add items to cart", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }

            }


            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {//Swipe left on the purple part behind
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
                    // ObjectAnimator animX = ObjectAnimator.ofFloat(itemView, "translationX", 0);
                    // animX.setDuration(500); // Set the animation duration
                    //animX.start();

                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        ImageView backButton1 = findViewById(R.id.backButton1);

        backButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }//Return button, return to the previous page
        });
        EditText searchEditText = findViewById(R.id.edit_search);
        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String query = searchEditText.getText().toString();
                   // performSearch(query);

                    //searchFromGoodTree(query);
                    try{
                        if(!query.isEmpty()){
                            tokenizer = new SearchTokenizer(query);
                            parser = new Parser(tokenizer);
                            parser.parseExp();
                            queryAttributes = parser.getQueryAttributes();
                            List<Good> searchResults = executeQuery();
                            if(!searchResults.isEmpty()) {
                                updateSearchGoods(searchResults);
                            } else{
                                Toast.makeText(SearchActivity.this, "Sorry! \nThere is no good satisfying your search", Toast.LENGTH_SHORT).show();
                            }
                    return true;}
                    }catch (SearchTokenizer.IllegalTokenException e){
                        Toast.makeText(SearchActivity.this, "Please follow the grammar", Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });
        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        ImageView openFilterDrawerButton = findViewById(R.id.imageButton2);
        openFilterDrawerButton.setOnClickListener(new View.OnClickListener() {//filter price，a sidebar
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
        EditText priceMinEditText = findViewById(R.id.priceMinEditText);
        EditText priceMaxEditText = findViewById(R.id.priceMaxEditText);
        Button filterPriceButton = findViewById(R.id.filterPriceButton);
        filterPriceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String minPriceStr = priceMinEditText.getText().toString();
                String maxPriceStr = priceMaxEditText.getText().toString();

                double minPrice = Double.parseDouble(minPriceStr);
                double maxPrice = Double.parseDouble(maxPriceStr);

                filterGoodsByPriceRange(minPrice, maxPrice);
            }
        });
        ImageView l_h_button = findViewById(R.id.imageButton4);//Sort from low to high
        l_h_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortGoodsByPrice();
            }
        });
        ImageView h_l_button = findViewById(R.id.imageButton3);//Sort from high to low
        h_l_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortGoodsByPriceDescending();
            }
        });

    }

    //sort goods by price
    public void sortGoodsByPriceDescending() {
        if (!goodSourceList.isEmpty()) {
            Collections.sort(goodSourceList, new Comparator<Good>() {
                @Override
                public int compare(Good good1, Good good2) {
                    return Double.compare(good2.getPrice(), good1.getPrice());
                }
            });

            updateSearchGoods(goodSourceList);
        }
    }
    //sort the good by price

    public void sortGoodsByPrice() {
        if (!goodSourceList.isEmpty()) {
            Collections.sort(goodSourceList, new Comparator<Good>() {
                @Override
                public int compare(Good good1, Good good2) {
                    return Double.compare(good1.getPrice(), good2.getPrice());
                }
            });
            updateSearchGoods(goodSourceList);
        }
    }
    //price filter
    public void filterGoodsByPriceRange(double minPrice, double maxPrice) {
        List<Good>good_sort=executeQuery();
        if (!good_sort.isEmpty()) {
            List<Good> filteredGoods = good_sort.stream()
                    .filter(good -> good.getPrice() >= minPrice && good.getPrice() <= maxPrice)
                    .collect(Collectors.toList());

            updateSearchGoods(filteredGoods);
        }
    }
    /**
     * Executes the search query based on the parsed expressions stored in {@code queryAttributes}.
     * The method filters out the list of goods from the AVL tree according to the given query attributes such as
     * category, brand, name, price, and clicks. Each attribute filter is applied sequentially,
     * thereby refining the list of goods with each filter.
     * <p>
     * The method returns a list of goods that match all the criteria specified in the query attributes.
     * </p>
     *
     * @return A list of {@link Good} objects that match the search criteria.
     *         If no matches are found, an empty list is returned.
     *
     * @author Yuan Chen
     */
    public List<Good> executeQuery() {
        this.goodAVLtree = MyApplication.goodAVLtree;

        // Initialize expressions for price and clicks filtering
        ClicksExp clicksExp = null;
        PriceExp priceExp = null;

        // Loop through query attributes to identify expressions for price and clicks
        for (int i =0; i<queryAttributes.size(); i++) {
            if (queryAttributes.get(i) instanceof ClicksExp) {
                clicksExp =(ClicksExp) queryAttributes.get(i);
            }
            if (queryAttributes.get(i) instanceof PriceExp) {
                priceExp = (PriceExp) queryAttributes.get(i);
            }
        }

        // Add all goods from the AVL tree to the source list
        goodSourceList.addAll(goodAVLtree.getAllGoods());

        // Loop through query attributes to apply filters
        for (Exp queryAttribute : queryAttributes) {
            // Filter by category
            if (queryAttribute instanceof CategoryExp) {
                String currentcategory =((CategoryExp) queryAttribute).getCategory();
                AVLCateNode targetNode = this.goodAVLtree.search(((CategoryExp) queryAttribute).getCategory());

                // If category exists, filter goods by category
                if (targetNode != null) {
                    goodSourceList.clear();
                    List<Good> filteredGoods1 = targetNode.goodList.stream()
                            .filter(good -> good.getCategory().toLowerCase().contains(currentcategory))
                            .collect(Collectors.toList());

                    goodSourceList.addAll(filteredGoods1);
                }
                // If category doesn't exist, show a toast message
                else {
                    Toast.makeText(this, "Category not exists", Toast.LENGTH_SHORT).show();
                }
            }

            // Filter by brand
            if (queryAttribute instanceof BrandExp) {
                goodSourceList = goodSourceList.stream()
                        .filter(good -> good.getBrand().toLowerCase().contains(((BrandExp) queryAttribute).getBrand().toLowerCase()))
                        .collect(Collectors.toList());
            }

            // Filter by name
            if (queryAttribute instanceof NameExp) {
                goodSourceList = goodSourceList.stream()
                        .filter(good -> good.getName().toLowerCase().contains(((NameExp) queryAttribute).getName().toLowerCase()))
                        .collect(Collectors.toList());
            }

            // Filter by price based on different comparators (e.g., ==, >, <, >=, <=)
            if (queryAttribute instanceof PriceExp) {
                Double queryPrice = Double.parseDouble(((PriceExp) queryAttribute).getPrice());
                // ... [rest of the price filtering logic]
            }

            // Filter by clicks based on different comparators (e.g., ==, >, <, >=, <=)
            if (queryAttribute instanceof ClicksExp) {
                Integer queryClicks = Integer.parseInt(clicksExp.getClicks());
                // ... [rest of the clicks filtering logic]
            }
        }

        // Remove duplicate goods from the list
        goodSourceList = goodSourceList.stream().distinct().collect(Collectors.toList());

        // Return the final filtered list of goods
        return goodSourceList;
    }

    private void updateSearchGoods(List<Good> results) {
       // RecyclerView recyclerView = findViewById(R.id.recyclerView);
        //LinearLayout emptyDataLayout = findViewById(R.id.ly_nodata);
        searchResultList.clear();
        searchResultList.addAll(results);

        if (!searchResultList.isEmpty()) {
            adapter.notifyDataSetChanged();
            recyclerView.setVisibility(View.VISIBLE);//control display
            emptyDataLayout.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.GONE);
            emptyDataLayout.setVisibility(View.VISIBLE);
        }
    }
}