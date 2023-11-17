package com.example.myapplication.DataController;

import android.content.Context;
import android.util.Log;

import com.example.myapplication.DataController.GoodsTreeTool.AVLCateNode;
import com.example.myapplication.DataController.GoodsTreeTool.AVLCateTree;
import com.example.myapplication.DataController.UsersTreeTool.AVLUserNode;
import com.example.myapplication.DataController.UsersTreeTool.AVLUserTree;
import com.example.myapplication.entity.Cart;
import com.example.myapplication.entity.Good;
import com.example.myapplication.entity.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
/**
 * using this class to convert Tree into JSON file for recording in local
 * @author Shiyu Pan u7615103 & Yuan Chen u7631839
 * */
public class AVLTreeToJSONConverter {
    /**
     * Converts given user and category trees to a single JSON object.
     *
     * @param userTree The user AVL tree.
     * @param cateTree The category AVL tree.
     * @return The JSON object representing the given trees.
     * @throws JSONException If there's an issue with JSON processing.
     */
    public JSONObject convertTreeToJSON(AVLUserTree userTree, AVLCateTree cateTree) throws JSONException {
        JSONObject mainObject = new JSONObject();

        mainObject.put("users", buildUsersJSON(userTree));
        mainObject.put("goods", buildGoodsJSON(cateTree));

        return mainObject;
    }
    /**
     * Writes the given JSON object to a file named 'newjson.json' in the app's file directory.
     *
     * @param json    The JSON object to write.
     * @param context The app context used to access the file directory.
     */
    public void writeJSONToFile(JSONObject json, Context context) {
        try {
            File outputDir = context.getFilesDir();
            File outputFile = new File(outputDir, "newjson.json");
            try (FileWriter fileWriter = new FileWriter(outputFile)) {
                fileWriter.write(json.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Builds a JSON object representing all users in the given user tree.
     *
     * @param userTree The user AVL tree.
     * @return JSON object representing all users.
     * @throws JSONException If there's an issue with JSON processing.
     */
    public JSONObject buildUsersJSON(AVLUserTree userTree) throws JSONException {
        JSONObject usersObject = new JSONObject();

        List<AVLUserNode> userNodes = getAllUserNodes(userTree.root, new ArrayList<>());
        for (AVLUserNode userNode : userNodes) {
            User user = userNode.value;
            JSONObject userObject = buildUserJSON(user);
            usersObject.put(user.getUid(), userObject);
        }

        return usersObject;
    }
    /**
     * Converts a User object to a JSON object.
     *
     * @param user The user to convert.
     * @return The JSON representation of the user.
     * @throws JSONException If there's an issue with JSON processing.
     */
    public JSONObject buildUserJSON(User user) throws JSONException {
        JSONObject userObject = new JSONObject();

        userObject.put("email", user.getEmail());
        userObject.put("password", user.getPassword());
        userObject.put("registerTime", user.getRegisterTime());
        userObject.put("lat", user.getLat());
        userObject.put("lon", user.getLon());
        userObject.put("goods", buildGoodsArray(user.getLauchGoods()));
        userObject.put("carts", buildCartsArray(user.getCartList()));

        return userObject;
    }
    /**
     * Builds a JSON array from a list of goods.
     *
     * @param goods List of goods.
     * @return A JSON array representing the list of goods.
     * @throws JSONException If there's an issue with JSON processing.
     */
    public JSONArray buildGoodsArray(List<Good> goods) throws JSONException {
        JSONArray goodsJsonArray = new JSONArray();
        for (Good good : goods) {
            goodsJsonArray.put(buildGoodJSON(good));
        }
        return goodsJsonArray;
    }
    /**
     * Converts a Good object to a JSON object.
     *
     * @param good The good to convert.
     * @return The JSON representation of the good.
     * @throws JSONException If there's an issue with JSON processing.
     */
    public JSONObject buildGoodJSON(Good good) throws JSONException {
        JSONObject goodObject = new JSONObject();

        goodObject.put("name", good.getName());
        goodObject.put("category", good.getCategory());
        goodObject.put("price", good.getPrice());
        goodObject.put("brand", good.getBrand());
        goodObject.put("gid", good.getGid());
        goodObject.put("uid", good.getUid());
        goodObject.put("registerTime", good.getRegisterTime());
        goodObject.put("isDelete", good.getIsDelete());
        goodObject.put("lat", good.getLat());
        goodObject.put("lon", good.getLon());

        return goodObject;
    }
    /**
     * Builds a JSON array from a list of carts.
     *
     * @param carts List of carts.
     * @return A JSON array representing the list of carts.
     * @throws JSONException If there's an issue with JSON processing.
     */
    public JSONArray buildCartsArray(List<Cart> carts) throws JSONException {
        JSONArray cartsJsonArray = new JSONArray();
        for (Cart cart : carts) {
            JSONObject cartJson = new JSONObject();
            cartJson.put("categoryui", cart.getCategoryui());
            cartJson.put("goods", buildGoodsArray(cart.getGoodModelList()));
            cartsJsonArray.put(cartJson);
        }
        return cartsJsonArray;
    }
    /**
     * Builds a JSON object representing all goods in the given category tree.
     *
     * @param cateTree The category AVL tree.
     * @return JSON object representing all goods.
     * @throws JSONException If there's an issue with JSON processing.
     */
    public JSONObject buildGoodsJSON(AVLCateTree cateTree) throws JSONException {
        JSONObject goodsObject = new JSONObject();

        List<AVLCateNode> cateNodes = cateTree.getAllNodes();
        for (AVLCateNode cateNode : cateNodes) {
            for (Good good : cateNode.goodList) {
                goodsObject.put(good.getGid(), buildGoodJSON(good));
            }
        }

        return goodsObject;
    }
    /**
     * Recursively retrieves all user nodes from the given AVL tree.
     *
     * @param root The root node of the tree.
     * @param list The list to store the retrieved nodes.
     * @return The list of all user nodes.
     */
    private List<AVLUserNode> getAllUserNodes(AVLUserNode root, List<AVLUserNode> list) {
        if (root != null) {
            getAllUserNodes(root.leftN, list);
            list.add(root);
            getAllUserNodes(root.rightN, list);
        }
        return list;
    }
}
