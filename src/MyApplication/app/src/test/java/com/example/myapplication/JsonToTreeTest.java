package com.example.myapplication;

import com.example.myapplication.DataController.AVLMainTree;
import com.example.myapplication.DataController.GoodsTreeTool.AVLCateTree;
import com.example.myapplication.DataController.UsersTreeTool.AVLUserNode;
import com.example.myapplication.DataController.UsersTreeTool.AVLUserTree;
import com.example.myapplication.entity.Cart;
import com.example.myapplication.entity.Good;
import com.example.myapplication.entity.User;

import org.json.JSONObject;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonToTreeTest {

    public static AVLUserTree userTree1;
    public static AVLCateTree goodAVLtree1;
    private InputStream inputStream;
    private  InputStream inputStream2;

    @Test
    public void isFactoryWork(){


    }
}
