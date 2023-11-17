package com.example.myapplication;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.example.myapplication.DataController.AVLNode;

public class AVLNodeTest {
    public AVLNode<Integer> avlNode;

    @Before
    public void setUp() {
        avlNode = new AVLNode<>(10);
        // 在这里进行初始化操作
    }

    @Test
    public void testAddNode() {
        avlNode.add(new AVLNode<>(5));
        avlNode.add(new AVLNode<>(15));
        avlNode.add(new AVLNode<>(3));
        avlNode.add(new AVLNode<>(6));
        avlNode.add(new AVLNode<>(7));
        avlNode.add(new AVLNode<>(9));

        assertEquals(6, avlNode.value.intValue());
        assertEquals(5, avlNode.leftN.value.intValue());
        assertEquals(10, avlNode.rightN.value.intValue());
        assertEquals(3, avlNode.leftN.leftN.value.intValue());

        assertNotNull(avlNode.search(6));
        assertNotNull(avlNode.search(7));
        assertNotNull(avlNode.search(9));
        assertNotNull(avlNode.search(3));
        assertNotNull(avlNode.search(15));
       // assertNotNull(avlNode.search(5));


    }

    @Test
    public void testSearchNode() {
        AVLNode<Integer> node5 = new AVLNode<>(5);
        avlNode.add(node5);

        AVLNode<Integer> foundNode = avlNode.search(5);
        assertNotNull(foundNode);
        assertEquals(5, foundNode.value.intValue());
    }

    // 其他测试方法
    @Test
    public void testGetHeight() {
        avlNode.add(new AVLNode<>(5));
        avlNode.add(new AVLNode<>(15));
        avlNode.add(new AVLNode<>(3));

        assertEquals(3, avlNode.getHeight());
    }

    @Test
    public void testSearchParent() {
        avlNode.add(new AVLNode<>(5));
        avlNode.add(new AVLNode<>(15));

        AVLNode<Integer> parent5 = avlNode.searchParent(5);
        assertNotNull(parent5);
        assertEquals(10, parent5.value.intValue());

        AVLNode<Integer> parent15 = avlNode.searchParent(15);
        assertNotNull(parent15);
        assertEquals(10, parent15.value.intValue());
    }

    @Test
    public void testSearchParentForExistingValue() {
        AVLNode<Integer> root;
        root = new AVLNode<>(10);
        root.leftN = new AVLNode<>(5);
        root.rightN = new AVLNode<>(15);
        root.leftN.leftN = new AVLNode<>(3);
        root.leftN.rightN = new AVLNode<>(7);
        AVLNode<Integer> parent5 = root.searchParent(5);
        AVLNode<Integer> parent3 = root.searchParent(3);
        AVLNode<Integer> parent15 = root.searchParent(15);
        AVLNode<Integer> parent7 = root.searchParent(7);

        // 5, 3, 15, 7 的父节点应该都是 10
        assertNotNull(parent5);
        assertEquals(10, parent5.value.intValue());

        assertNotNull(parent3);
        assertEquals(5, parent3.value.intValue());

        assertNotNull(parent15);
        assertEquals(10, parent15.value.intValue());

        assertNotNull(parent7);
        assertEquals(5, parent7.value.intValue());
    }

/*    @Test
    public void testSearchParentForNonExistingValue() {
        AVLNode<Integer> parent20 = root.searchParent(20);
        AVLNode<Integer> parent1 = root.searchParent(1);

        // 20 和 1 不存在于树中，应该返回 null
        assertNull(parent20);
        assertNull(parent1);
    }*/



}