package com.example.myapplication;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import com.example.myapplication.DataController.JSONToAVLTreeConverterFactory;
import com.example.myapplication.DataController.UsersTreeTool.AVLUserNode;
import com.example.myapplication.DataController.UsersTreeTool.AVLUserTree;
import com.example.myapplication.entity.User;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;


public class UserTreeTest {
    private AVLUserTree avlUserTree;

    private AVLUserNode[] nodes = new AVLUserNode[20];
    private HashMap<Integer,AVLUserNode> setUpUsers= new HashMap<>();



    private JSONToAVLTreeConverterFactory factory =  JSONToAVLTreeConverterFactory.getInstance();
    @Before
   public void setUp() {
        avlUserTree = new AVLUserTree();
        for (int i = 0; i < 10; i++) {
            User newUser = new User(Integer.toString(i),"i*2");
            setUpUsers.put(i,new AVLUserNode(newUser));
            avlUserTree.add(new AVLUserNode(newUser));
           // nodes[i] = new AVLUserNode(new User("i","i*2")) ;
        }
        for (int i = 0; i < 20; i++) {
          //  avlUserTree.add(new AVLUserNode(new User("i","i*2")) );
            nodes[i] = new AVLUserNode(new User(Integer.toString(i*22),"#i*2")) ;
        }

    }

    @Test
   public void testAdd() {
        AVLUserNode node1 = new AVLUserNode(new User("user1","user1"));
        AVLUserTree rootavlUserTree =  new AVLUserTree();
        rootavlUserTree.add(node1);
        assertEquals(node1, rootavlUserTree.root);

        AVLUserNode node2 = new AVLUserNode(new User("user2","user2"));
        rootavlUserTree.add(node2);
        assertEquals(node1, rootavlUserTree.root);
        assertEquals(node2, rootavlUserTree.root.leftN);
    }

    @Test
   public void testMidOrder() {
        // TODO: 编写测试中序遍历的测试用例
    }

    @Test
    public  void testSearch() {
        User user1  = new User("user1","user1");
        AVLUserNode node1 = new AVLUserNode(user1);
        AVLUserTree rootavlUserTree =  new AVLUserTree();
        rootavlUserTree.add(node1);
        assertEquals(node1, rootavlUserTree.root);

        User user2 = new User("user2","user2");
        AVLUserNode node2 = new AVLUserNode(user2);
        rootavlUserTree.add(node2);


        assertEquals(node1, rootavlUserTree.search(user1));
        assertEquals(node2, rootavlUserTree.search(user2));


       /* assertEquals(node1, avlUserTree.search(user1));
        assertEquals(node2, avlUserTree.search(user2));*/
    }

    @Test
   public void testSearchParent() {
        // TODO: 编写测试查找父节点的测试用例
    }

    @Test
   public void testDelNode() {
        User user1 = new User("user1", "user1");
        User user2 = new User("user2", "user2");

        AVLUserNode node1 = new AVLUserNode(user1);
        AVLUserNode node2 = new AVLUserNode(user2);
        AVLUserTree rootavlUserTree =  new AVLUserTree();
        rootavlUserTree.add(node1);
        rootavlUserTree.add(node2);

        rootavlUserTree.delNode(user1);
        assertNull(rootavlUserTree.search(user1));
       // rootavlUserTree.midOrder();
        assertNotNull(rootavlUserTree.search(user2));
    }

    public void testDelRoot() {
        User user1 = new User("user1", "user1");


        AVLUserNode node1 = new AVLUserNode(user1);

        AVLUserTree rootavlUserTree =  new AVLUserTree();
        rootavlUserTree.add(node1);


        rootavlUserTree.delNode(user1);
        assertNull(rootavlUserTree.search(user1));
        // rootavlUserTree.midOrder();

    }

    @Test
    public void testDelNodeComplex() {
        User user0 = setUpUsers.get(0).value;
        User user1 = setUpUsers.get(1).value;
        User user2 = setUpUsers.get(2).value;
        User user3 = setUpUsers.get(3).value;
        User user4 = setUpUsers.get(4).value;
        User user5 = setUpUsers.get(5).value;
        avlUserTree.delNode(user0);
        assertNull(avlUserTree.search(user0));

        avlUserTree.delNode(user1);
        assertNull(avlUserTree.search(user1));

        avlUserTree.delNode(user2);
        assertNull(avlUserTree.search(user2));

        avlUserTree.delNode(user3);
        assertNull(avlUserTree.search(user3));

     /*   avlUserTree.delNode(user4);
        assertNull(avlUserTree.search(user4));

        avlUserTree.delNode(user5);
        assertNull(avlUserTree.search(user5));*/


    }

    @Test
    public void testDelMin() {
        // TODO: 编写测试删除最小节点的测试用例
    }

    @Test
    public void testSearchByID(){
        User user1 = new User("enola.blick@gmail.com", "nc4frprq","0kLLVgkHjwV4wdozQjdYCCdi1KL2");
        avlUserTree.add(new AVLUserNode(user1));

        AVLUserNode avlUserNode = avlUserTree.searchByID("0kLLVgkHjwV4wdozQjdYCCdi1KL2");
        assertNotNull(avlUserNode);

        assertEquals("enola.blick@gmail.com", avlUserNode.value.getEmail());
        assertEquals("nc4frprq", avlUserNode.value.getPassword());
       // assertEquals("Other", avlUserNode.value.getGender());

    }

    @Test
    public  void  changeNodeTest(){
        User user1 = setUpUsers.get(1).value;
        User user2 = new User("user2", "user2");
        avlUserTree.changeNodeValue(user1,user2);

        assertNull( avlUserTree.search(user1));
        assertNotNull( avlUserTree.search(user2));

    }


}
