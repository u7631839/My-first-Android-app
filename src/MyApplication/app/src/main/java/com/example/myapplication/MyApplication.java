package com.example.myapplication;

import static android.content.ContentValues.TAG;

import android.app.Application;
import android.content.Context;
import android.media.MediaRecorder;
import android.util.Log;

import com.example.myapplication.DataController.AVLTreeToJSONConverter;
import com.example.myapplication.DataController.GoodsTreeTool.AVLCateNode;
import com.example.myapplication.DataController.GoodsTreeTool.AVLCateTree;
import com.example.myapplication.DataController.UsersTreeTool.AVLUserNode;
import com.example.myapplication.DataController.UsersTreeTool.AVLUserTree;
import com.example.myapplication.conversation.Message;
import com.example.myapplication.entity.Good;
import com.example.myapplication.entity.User;
import com.example.myapplication.DataController.JSONToAVLTreeConverterFactory;
import com.example.myapplication.tools.GenerateGoodsData;
import com.example.myapplication.tools.MyAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** this class is the app class,and all the initialization process will run here
 * @author all
 * */
public class MyApplication extends Application {


    private String userType = "user";
    private String goodType = "good";
    private long message_update_time = 0;
    private long good_update_time;
    private long user_update_time;


    private HashMap<String,List<Good>> cateGood1 = new HashMap<>();

    public static AVLUserTree userTree = new AVLUserTree();
    public static AVLCateTree goodAVLtree = new AVLCateTree();


    private FileInputStream fileInputStream;
    private FileInputStream fileInputStream1;
    public static double my_lat;
    public static double my_lon;


    private Context context = this;


    private JSONToAVLTreeConverterFactory factory =  JSONToAVLTreeConverterFactory.getInstance();
    @Override
    public void onCreate() {
        super.onCreate();
        try{
           String[] times = readTimes("timetable");
          message_update_time=Long.parseLong(times[0]);
         good_update_time=Long.parseLong(times[1]);
            user_update_time=Long.parseLong(times[2]);
//         message_update_time=0;
//           good_update_time=0;
//            user_update_time=0;
        }catch (Exception e){
            message_update_time=0;
            good_update_time=0;
            user_update_time=0;
        }

        //  generateData();
//        if (times[0]!=null){
//            message_update_time=Long.parseLong(times[0]);
//        }else {
//            message_update_time=0;
//        }
//        if (times[1]!=null){
//            good_update_time=Long.parseLong(times[1]);
//        }else {
//            good_update_time=0;
//        }
//        if (times[2]!=null){
//            user_update_time=Long.parseLong(times[2]);
//        }else {
//            user_update_time=0;
//        }
       /* try {
             inputStream = getAssets().open("searchengine-9511e-default-rtdb-export (2).json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        User dummy = new User();
        userAVLtree=new AVLTree<>(dummy);
        initializeUserTree();
        if (initializeUserTree()) {
           userAVLtree.display();

        }*/
        //System.out.println("1451981652");
        if(good_update_time!=0){
            //testSearchByID();
            //System.out.println("--------------------goodtime>0----------------------");

            try {
                // filepath
                String filePath = context.getFilesDir() + File.separator + "newjson.json";
                // System.out.println("filePath = "+filePath);
                // open file
                fileInputStream = new FileInputStream(filePath);
                // in try block file will close automatically
            } catch (IOException e) {
                //if there is an error data will load from firebase
                good_update_time=0;
                //  System.out.println("报异常了");
                throw new RuntimeException(e);
            }
            goodAVLtree = new AVLCateTree();
            // if update time == 0 initialize
            initializeGoodsTree();
        }
//        System.out.println(user_update_time);
        if(user_update_time!=0){
//            System.out.println("--------------------time>0----------------------");
            try {
                //   inputStream = getAssets().open("searchengine-9511e-default-rtdb-export (2).json");
                String filePath1 = context.getFilesDir() + File.separator + "newjson.json";
                fileInputStream1 = new FileInputStream(filePath1);
            } catch (IOException e) {
                user_update_time=0;
                throw new RuntimeException(e);
            }
            userTree = new AVLUserTree();
            initializeUserTree();
            System.out.println("-------------------------tree 大成功---------------------------");
            //testSearchByID();


        }

        //listen the users branch of firebase
        DatabaseReference usersReference = FirebaseDatabase.getInstance().getReference("users");
        Query userQuery = usersReference.orderByChild("registerTime").startAt(user_update_time);
        ChildEventListener usersListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                User user = dataSnapshot.getValue(User.class);
                Log.d("data",user.getEmail());
//                System.out.println("ceshi");
//                System.out.println(user.getUid());
                factory.firstConvertUserJsonToAVLTree(userTree,user);
                user_update_time=user.getRegisterTime();
                //record the update time
                write("timetable",String.valueOf(message_update_time),String.valueOf(good_update_time),String.valueOf(user_update_time));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                User user = dataSnapshot.getValue(User.class);
                System.out.println("Changed: " + user.getLauchGoods());
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "goodsListener:onCancelled", databaseError.toException());
            }
        };
        userQuery.addChildEventListener(usersListener);


        //listen the goods branch of firebase
        DatabaseReference goodsReference = FirebaseDatabase.getInstance().getReference("goods");
        Query goodQuery = goodsReference.orderByChild("registerTime").startAt(good_update_time);
        ValueEventListener goodsListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long last_time=0;
                for (DataSnapshot goodsSnapshot : dataSnapshot.getChildren()) {
                    Good good=goodsSnapshot.getValue(Good.class);
                    //we using a sign to mark whether a good is deleted in firebase, so that we can noticefy all the users
                    if (good.getIsDelete()==0){
                       // Good good1 = new Good(good.getGid(),good.getName(), good.getCategory(), good.getPrice(),good.getClicks(), good.getBrand());
                        Good good1 = new Good(good.getUid(), good.getName(), good.getCategory(), good.getPrice(),good.getRegisterTime(), good.getIsDelete(), good.getClicks(), good.getBrand(),good.getGid(),good.getLon(),good.getLat());
                        List<String> cateOfEach = good1.getCate();
                        last_time=good1.getRegisterTime();
                        //System.out.println();

                        //put into the hashmap
                        // 检查是否已经存在该类别的商品列表
                        for (String newCate : cateOfEach) {
                            List<Good> goodsList = cateGood1.get(newCate);
                            if (goodsList == null) {
                                // 如果不存在，创建一个新的商品列表
                                goodsList = new ArrayList<>();

                            }

                            // 将新的 Good 对象添加到商品列表中
                            goodsList.add(good1);
                            cateGood1.put(newCate, goodsList);

                        }
                    }else if (good.getIsDelete()==1){
                        //if the sign is 1, it means the good has been deleted.
                        Good good1 = new Good(good.getGid(),good.getName(), good.getCategory(), good.getPrice(),good.getClicks(), good.getBrand());
                        last_time=good1.getRegisterTime();
                        //本地树里面删除
                    }


                }
                if (last_time!=0){
                    //record the update time
                    good_update_time=last_time;
                    write("timetable",String.valueOf(message_update_time),String.valueOf(good_update_time),String.valueOf(user_update_time));
                }
                //once complete the hashmap , convert them to tree,
                //the value of tree is each category, each node store the goods list
                for (Map.Entry<String, List<Good>> entry : cateGood1.entrySet()) {
                    //split the category
                    AVLCateNode avlCateNode = new AVLCateNode(entry.getKey(), entry.getValue());
                    goodAVLtree.add(avlCateNode);
                }


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadMessages:onCancelled", databaseError.toException());
            }
        };
        goodQuery.addValueEventListener(goodsListener);






//        DatabaseReference mMessagesReference = FirebaseDatabase.getInstance().getReference("message");
//        mMessagesReference.push().setValue(new Message("Rongshen niubi","152478","852465"));
//        Query query = mMessagesReference.orderByChild("message_time").startAt(message_update_time);

        /*ValueEventListener messagesListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long last_time=0;
                //int num=0;
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    //System.out.println(++num);
                    Message message = messageSnapshot.getValue(Message.class);
                    last_time=message.getMessage_time();
//                    System.out.println(message_update_time);
//                    System.out.println("-------------------");
//                    System.out.println(message.getContent());
//                    System.out.println(message.getMessage_time());
                }
               *//* if (last_time!=0){
                    message_update_time=last_time;
                    write("timetable",String.valueOf(message_update_time),String.valueOf(good_update_time),String.valueOf(user_update_time));
                }*//*
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadMessages:onCancelled", databaseError.toException());
            }
        };
        query.addValueEventListener(messagesListener);*/

    }
    public String[] readTimes(String fileName) {
        String[] times = new String[3];
        try {
            // open the file, and the file will close
            FileInputStream fis = openFileInput(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            // 逐行读取文件，直到达到文件的末尾
            for (int i = 0; i < 3; i++) {
                times[i]=reader.readLine();
            }
            // close the file
            reader.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("FileReadError", "Error reading file: " + e.getMessage());
            //if the file does not exist, the time will update into 0, and all the data will load from firebase
            times[0]="0";
            times[1]="0";
            return times;

        }
        // return all the time
        return times;
    }

    public void write(String fileName,String m_t,String g_t,String u_t){
        String fileContents = String.valueOf(System.currentTimeMillis());
        System.out.println(fileContents);
        //write down the time, for next time reading, and the block will be closed after.
        try (FileOutputStream fos = openFileOutput(fileName, MODE_PRIVATE)) {
            //\n used to change line
            fos.write((m_t + "\n").getBytes());
            fos.write((g_t + "\n").getBytes());
            fos.write((u_t + "\n").getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private boolean initializeUserTree(){
        // initialize the userTree, the starttime and endTime using to record the running time for optimise
        if(user_update_time!=0){
            long startTime = System.currentTimeMillis();
            userTree = (AVLUserTree) factory.init(userTree,goodAVLtree, userType,fileInputStream1,fileInputStream);
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            System.out.println("看看User树用多长时间 " + duration + " milliseconds");
            //  System.out.println("工厂运行");
            //    System.out.println(userTree==null);

        }
        if(userTree.root==null){
            System.out.println("userTree是空的");
            return false;
        }else {
            System.out.println("userTree不为null");
            return true;
        }

    }

    private boolean initializeGoodsTree(){
        // initialize the goodsTree, the starttime and endTime using to record the running time for optimise
        if(user_update_time!=0){
            System.out.println("init good tree");
            long startTime = System.currentTimeMillis();
            this.goodAVLtree  = (AVLCateTree) factory.init(userTree,goodAVLtree, goodType,fileInputStream1,fileInputStream);
            //  this.goodAVLtree = factory.convertGoodJsonToAVLTree(goodAVLtree,inputStream2);
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            //0.7s
            System.out.println("看看Good树用多长时间" + duration + " milliseconds");

        }

        if(goodAVLtree!=null){
            return true;
        }else {

            return false;
        }

    }




/*    private DatabaseReference mDatabase;
// ...

    private void generateData(){
        GenerateGoodsData goodGenerator = new GenerateGoodsData();
        List<Good> randomGood = goodGenerator.multiple_signup();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        for (Good good : randomGood) {
            System.out.println(good);
            mDatabase.child("goods").child(good.getGid()).setValue(good);
        }
    }*/


/*    public void testSearchByID(){


        AVLUserNode avlUserNode = userTree.searchByID("0kLLVgkHjwV4wdozQjdYCCdi1KL2");
        if(avlUserNode!=null){
            System.out.println( "Email= "+ avlUserNode.value.getEmail());
        };


        assertEquals("nc4frprq", avlUserNode.value.getPassword());
        assertEquals("Other", avlUserNode.value.getGender());

    }*/
}
