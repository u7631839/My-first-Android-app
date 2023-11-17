package com.example.myapplication.DataController;

import android.content.Context;

import com.example.myapplication.DataController.GoodsTreeTool.AVLCateNode;
import com.example.myapplication.DataController.GoodsTreeTool.AVLCateTree;

import com.example.myapplication.DataController.UsersTreeTool.AVLUserNode;
import com.example.myapplication.DataController.UsersTreeTool.AVLUserTree;
import com.example.myapplication.DatabaseController;
import com.example.myapplication.entity.Cart;
import com.example.myapplication.entity.Good;
import com.example.myapplication.entity.User;

import com.google.firebase.database.FirebaseDatabase;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 * This class represents a JSON to AVL tree converter factory for categories and users.
 * It provides methods for converting JSON data into AVL trees and managing the conversion process.
 *
 * @author Jolene Sun u6591996
 * @param <T> A type parameter representing the data type to be stored in the trees.
 */
public class JSONToAVLTreeConverterFactory  <T extends Comparable<T>> {

    private HashMap<String,List<Good>> cateGood = new HashMap<>();
    private HashMap<String,List<Good>> cateGood1 = new HashMap<>();

    private static JSONToAVLTreeConverterFactory instance = null;

    /**
     * Private constructor for the singleton pattern.
     */
    private JSONToAVLTreeConverterFactory() {

    }

    /**
     * Returns a new instance if it currently does not exist and the current instance if it already exists.
     *
     * @return A new or existing instance of JSONToAVLTreeConverterFactory.
     */
    public static JSONToAVLTreeConverterFactory getInstance() {
        /*
         TODO: implement this method
         Note that you will need to create an instance of PrinterQueue.
         */
        if(instance == null){
            instance = new JSONToAVLTreeConverterFactory();
            // db = FirebaseFirestore.getInstance();

        }
        return instance;
    }

    /**
     * Initializes and returns an AVL tree based on the provided input data.
     *
     * @param userTree       An AVLUserTree to store user data.
     * @param cateTree       An AVLCateTree to store category data.
     * @param type           The type of data to convert ('user' or 'good').
     * @param inputStream    The input stream for JSON data (user data).
     * @param fileInputStream The input stream for JSON data (good data).
     * @return An AVLMainTree based on the specified type.
     */
    public AVLMainTree init( AVLUserTree userTree, AVLCateTree cateTree, String type,FileInputStream inputStream, FileInputStream fileInputStream){


        if (type.equals("user") ) {
            return convertUserJsonToAVLTree(userTree, inputStream);
        } else if (type.equals("good")) {
            return convertGoodJsonToAVLTree( cateTree,fileInputStream);
        } else {
            // throw new IllegalArgumentException("Unsupported file type: " + fileType);
            return null;
        }
        //  return null;
    }

    /**
     * Converts JSON data containing user information into an AVLUserTree.
     *
     * @param avlTree      An AVLUserTree to store user data.
     * @param inputStream  The input stream for JSON data (user data).
     * @return An AVLUserTree containing user data.
     */
    public AVLUserTree convertUserJsonToAVLTree(AVLUserTree avlTree, FileInputStream inputStream) {

        //AVLTree avlTree = new AVLTree();
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            // 读取JSON文件
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            // 将文件内容读取为字符串
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String jsonString = stringBuilder.toString();

            // Parses a JSON string into a JSON object
            JSONObject jsonObject = new JSONObject(jsonString);

            // get user attribute
            JSONObject users = jsonObject.getJSONObject("users");

            Iterator<String> keysIterator = users.keys();


            while (keysIterator.hasNext()) {
                String userId = keysIterator.next();
                // get JsonObject
                JSONObject user = users.getJSONObject(userId);
                // get key and value
                //  int age = user.getInt("age");
                String email = user.getString("email");
                String password = user.getString("password");

                long registerTime = Long.parseLong(user.getString("registerTime"));

                List<Good> lauchGoodsList = new ArrayList<>();
                try {
                    lauchGoodsList = getLauchGoodsListFromJson(user.getJSONArray("goods"));
                }catch (Exception e){
                    lauchGoodsList = new ArrayList<>();
                }
                List<Cart> cartList = new ArrayList<>();
                try {
                    cartList = getCartListFromJson(user.getJSONArray("carts"));
                }catch (Exception e){
                    cartList = new ArrayList<>();
                }

                // insert the key and data to AVL tree
                //long registerTime, String password, String email, String uid, List<Good> lauchGoods, List<Cart> cartList
                avlTree.add(new AVLUserNode(new User(registerTime,password, email, userId,lauchGoodsList, cartList)));

            }


            //  bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return  avlTree;
    }

    /**
     * Parses a JSONArray and returns a list of Good objects.
     * @author Shiyu Pan
     * @param jsonArray The JSONArray containing Good objects.
     * @return A list of Good objects.
     * @throws JSONException If there is an issue with JSON parsing.
     */
    public List<Good> getLauchGoodsListFromJson(JSONArray jsonArray) throws JSONException {
        List<Good> lauchGoodsList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject good = jsonArray.getJSONObject(i);
            // explain json obect
            String name = good.getString("name");
            String category = good.getString("category");
            Double  price = Double.parseDouble(good.getString("price"));
            Double  lat = Double.parseDouble(good.getString("lat"));
            Double  lon = Double.parseDouble(good.getString("lon"));
            //  Integer clicks = Integer.parseInt(good.getString("clicks"));
            String brand = good.getString("brand");
            String uid = good.getString("uid");
            String gid = good.getString("gid");
            long registerTime = Long.parseLong(good.getString("registerTime"));
            int isDelete = Integer.parseInt(good.getString("isDelete"));
            //String uid,  String name, String category, Double price, long registerTime, int isDelete, Integer clicks, String brand, double lon, double lat, String gid
            //  Supermarket supermarket = good.getString("supermarket");
            Good eachGood = new Good(uid, name, category, price,registerTime, isDelete, 0, brand,gid,lon,lat);
//            Good good = new Good(/* 参数 */);
            lauchGoodsList.add(eachGood);
        }
        return lauchGoodsList;
    }

    /**
     * Parses a JSONArray and returns a list of Cart objects.
     * @author Shiyu Pan
     * @param jsonArray The JSONArray containing Cart objects.
     * @return A list of Cart objects.
     * @throws JSONException If there is an issue with JSON parsing.
     */
    public List<Cart> getCartListFromJson(JSONArray jsonArray) throws JSONException {
        List<Cart> cartList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            // 这里根据你的Cart对象的结构来解析JSON对象
            String categoryui=obj.getString("categoryui");
            List<Good> cartGoods = getLauchGoodsListFromJson(obj.getJSONArray("goods"));
            Cart eachcart = new Cart(categoryui,cartGoods);
            cartList.add(eachcart);
        }
        return cartList;
    }


    /**
     * Converts a single User object into an AVLUserTree.
     * @Jolene Sun
     * @param avlTree   An AVLUserTree to store user data.
     * @param eachUser  The User object to convert and insert.
     * @return An AVLUserTree containing the new user data.
     */
    public AVLUserTree firstConvertUserJsonToAVLTree(AVLUserTree avlTree, User eachUser) {
        avlTree.add(new AVLUserNode(eachUser));
        return  avlTree;
    }

    /**
     * Converts JSON data containing goods information into an AVLCateTree.
     *
     * @param cateTree       An AVLCateTree to store category data.
     * @param fileInputStream The input stream for JSON data (good data).
     * @return An AVLCateTree containing category and goods data.
     */
    public AVLCateTree convertGoodJsonToAVLTree(AVLCateTree cateTree, FileInputStream fileInputStream) {

        try {
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            // read from JSon
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            // read to String
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String jsonString = stringBuilder.toString();

            // Parse a JSON string into a JSON object
            JSONObject jsonObject = new JSONObject(jsonString);

            // get "users" attribute
            JSONObject goods = jsonObject.getJSONObject("goods");

            Iterator<String> keysIterator = goods.keys();

            while (keysIterator.hasNext()) {

                String goodId = keysIterator.next();

                JSONObject good = goods.getJSONObject(goodId);
                // get key and balue from json onject
                String name = good.getString("name");
                String category = good.getString("category");
                Double  price = Double.parseDouble(good.getString("price"));
                Double  lat = Double.parseDouble(good.getString("lat"));
                Double  lon = Double.parseDouble(good.getString("lon"));
                //  Integer clicks = Integer.parseInt(good.getString("clicks"));
                String brand = good.getString("brand");
                String uid = good.getString("uid");
                String gid = good.getString("gid");
                long registerTime = Long.parseLong(good.getString("registerTime"));
                int isDelete = Integer.parseInt(good.getString("isDelete"));

                //String uid,  String name, String category, Double price, long registerTime, int isDelete, Integer clicks, String brand, double lon, double lat, String gid
                //  Supermarket supermarket = good.getString("supermarket");
                Good eachGood = new Good(uid, name, category, price,registerTime, isDelete, 0, brand,gid,lon,lat);
                eachGood.toString();
                List<String> cateOfEach = eachGood.getCate();

                //put into the hashmap
                // check if already exists the new cate
                for (String newCate : cateOfEach) {
                    List<Good> goodsList = cateGood.get(newCate);
                    if (goodsList == null) {
                        // if not exist, generate a new list
                        goodsList = new ArrayList<>();

                    }

                    // put new good into the list
                    goodsList.add(eachGood);
                    cateGood.put(newCate, goodsList);

                }


            }

            // close the reader
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //once complete the hashmap , convert them to tree,
        //the value of tree is each category, each node store the goods list
        for (Map.Entry<String, List<Good>> entry : cateGood.entrySet()) {
            AVLCateNode avlCateNode = new AVLCateNode(entry.getKey(), entry.getValue());
            cateTree.add(avlCateNode);


        }

        return  cateTree;
    }





}
