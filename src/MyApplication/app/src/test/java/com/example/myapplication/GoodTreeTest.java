package com.example.myapplication;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.example.myapplication.DataController.GoodsTreeTool.AVLCateNode;
import com.example.myapplication.DataController.GoodsTreeTool.AVLCateTree;
import com.example.myapplication.DataController.JSONToAVLTreeConverterFactory;
import com.example.myapplication.DataController.UsersTreeTool.AVLUserNode;
import com.example.myapplication.DataController.UsersTreeTool.AVLUserTree;
import com.example.myapplication.entity.Good;
import com.example.myapplication.entity.User;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Collectors;


public class GoodTreeTest {


    private AVLCateTree avlCateTree;

    private AVLCateNode[] nodes = new AVLCateNode[20];
    private HashMap<Integer,AVLCateNode> setUpGoods= new HashMap<>();

    private HashMap<String, List<Good>> cateGood = new HashMap<>();



    private JSONToAVLTreeConverterFactory factory =  JSONToAVLTreeConverterFactory.getInstance();
    @Before
    public void setUp() {
        avlCateTree = new AVLCateTree();
        for (int i = 0; i < 10; i++) {
            Good eachGood = new Good(i+"1", "Milk"+i, "food"+i, Double.parseDouble("16"), 0, "XXX");
            Good eachGood2 = new Good(i+"1", "Milk"+i, "food"+i, Double.parseDouble("17"), 0, "YYY");
            for (String s : eachGood.getCate()) {

            }
            List<String> cateOfEach = eachGood.getCate();
            List<String> cateOfEach2 = eachGood2.getCate();
            //put into the hashmap
            // 检查是否已经存在该类别的商品列表
            for (String newCate : cateOfEach) {
                List<Good> goodsList = cateGood.get(newCate);
                if (goodsList == null) {
                    // 如果不存在，创建一个新的商品列表
                    goodsList = new ArrayList<>();

                }

                // 将新的 Good 对象添加到商品列表中
                goodsList.add(eachGood);
                cateGood.put(newCate, goodsList);

            }

            for (String newCate : cateOfEach2) {
                List<Good> goodsList = cateGood.get(newCate);
                if (goodsList == null) {
                    // 如果不存在，创建一个新的商品列表
                    goodsList = new ArrayList<>();

                }

                // 将新的 Good 对象添加到商品列表中
                goodsList.add(eachGood);
                cateGood.put(newCate, goodsList);

            }

            // nodes[i] = new AVLUserNode(new User("i","i*2")) ;
        }
        int i1 = 0;
        for (Map.Entry<String, List<Good>> entry : cateGood.entrySet()) {
            AVLCateNode avlCateNode = new AVLCateNode(entry.getKey(), entry.getValue());

            System.out.println(entry.getKey()+": ");
            for (Good good : entry.getValue()) {
                System.out.println(good);
            }
            avlCateTree.add(avlCateNode);
            setUpGoods.put(i1++,avlCateNode);
        }
        System.out.println("几个node: " + i1);


    }

    @Test
    public void testDelNode() {

       // Good eachGood = new Good(i+"1", "Milk"+i, "food"+i, Double.parseDouble("16"), 0, "XXX");
        AVLCateNode avlCateNode = setUpGoods.get(3);
        AVLCateNode avlCateNode2 = setUpGoods.get(4);
        String value = avlCateNode.value;
        System.out.println("应该被delete的value="+value);
        avlCateTree.delNode(value);
        assertNull(avlCateTree.search(value));
        // rootavlUserTree.midOrder();
        assertNotNull(avlCateTree.search(avlCateNode2.value));
    }


    @Test
    public void testDelNode2() {

        // Good eachGood = new Good(i+"1", "Milk"+i, "food"+i, Double.parseDouble("16"), 0, "XXX");
        AVLCateNode avlCateNode = setUpGoods.get(5);
        AVLCateNode avlCateNode3 = setUpGoods.get(6);
        AVLCateNode avlCateNode4 = setUpGoods.get(7);
        AVLCateNode avlCateNode2 = setUpGoods.get(3);
        String value = avlCateNode.value;
        String value2 = avlCateNode3.value;
        String value3 = avlCateNode4.value;
        System.out.println("应该被delete的value="+value);
        avlCateTree.delNode(value);
        avlCateTree.delNode(value2);
        avlCateTree.delNode(value3);
        assertNull(avlCateTree.search(value));
        assertNull(avlCateTree.search(value2));
        // rootavlUserTree.midOrder();
        assertNotNull(avlCateTree.search(avlCateNode2.value));
    }


}