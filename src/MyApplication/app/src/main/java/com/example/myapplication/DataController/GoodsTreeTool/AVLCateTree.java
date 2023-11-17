package com.example.myapplication.DataController.GoodsTreeTool;

import com.example.myapplication.DataController.AVLMainTree;
import com.example.myapplication.DataController.AVLNode;
import com.example.myapplication.DataController.UsersTreeTool.AVLUserNode;
import com.example.myapplication.entity.Good;
import com.example.myapplication.entity.User;

import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents an AVL tree node for goods.
 * It stores a category value and a list of associated goods.
 *
 * @author Rong Sun u6591996
 */
public class AVLCateTree implements AVLMainTree {
    AVLCateNode root;

    List<AVLCateNode> nodes = new ArrayList<>();

    public AVLCateTree() {
    }

    //添加
    public void add(AVLCateNode node){
        if(root==null){
            root = node;
        }else{
            this.root.add(node);
        }

    }
    /**
     * Searches for a node with a specific value in the tree.
     *
     * @param category The value to search for, can be brand/name/catefory of the good
     * @return The node with the specified value or null if not found.
     */
    public AVLCateNode search(String category){
        if(this.root==null){
            return null;
        }else{
            return this.root.search(category);
        }
    }

    /**
     * Searches for the parent node of a node with a specific value in the tree.
     *
     * @param category The value to search for, brand/name/catefory of the good
     * @return The parent node of the node with the specified value or null if not found.
     */
    public AVLCateNode searchParent(String category){
        if(this.root==null){
            return null;
        }else{
            return this.root.searchParent(category);
        }
    }
    //delete the nodes
    public void delNode(String value){
        if(root == null){
            return ;
        }else {
            AVLCateNode target = search(value);
            if(target==null){
                return ;
            }
            if(root.leftN == null && root.rightN == null){
                root = null;
                return ;
            }
            AVLCateNode targetParent = searchParent(value);
            //if target is leaf node
            if(target.leftN == null && target.rightN == null){
                if(targetParent.leftN!=null && targetParent.leftN.value == value){
                    targetParent.leftN = null;
                }else if(targetParent.rightN!=null && targetParent.rightN.value == value){
                    targetParent.rightN = null;


                }

            } else if (target.leftN!=null && target.rightN!=null) {
                AVLCateNode newTarget = delMin(target.rightN);
                target.value= newTarget.value;
                target.goodList= newTarget.goodList;

            }else {

                if(target.rightN!=null){
                    if(targetParent!=null){
                        if(targetParent.leftN.value == value){
                            targetParent.leftN = target.rightN;
                        }else {
                            targetParent.rightN = target.rightN;
                        }
                    }else {
                        root = target.rightN;
                    }
                }else {
                    if(targetParent!=null){
                        if(targetParent.leftN.value == value){
                            targetParent.leftN = target.leftN;
                        }else {
                            targetParent.rightN = target.leftN;
                        }
                    }else {
                        root = target.leftN;
                    }
                }

            }
        }



    }




    public AVLCateNode delMin(AVLCateNode node){
        AVLCateNode newNode = node;
        while (newNode.leftN!=null){
            newNode = newNode.leftN;
        }
        delNode(newNode.value);
        return newNode;

    }

    /**
     * This method will use inorder traversal to fetch all the goods from the tree.
     * @return List of all goods.
     */
    public List<Good> getAllGoods() {
        List<Good> goods = new ArrayList<>();
        inorderTraversal(root, goods);
        return goods;
    }

    /**
     * A recursive method to do an inorder traversal of the tree and collect goods.
     * @param node The current node.
     * @param goods List to collect the goods.
     */
    private void inorderTraversal(AVLCateNode node, List<Good> goods) {
        if (node != null) {
            inorderTraversal(node.leftN, goods);
            goods.addAll(node.goodList);
            inorderTraversal(node.rightN, goods);
        }
    }
    public List<AVLCateNode> getAllNodes() {
        List<AVLCateNode> nodesList = new ArrayList<>();
        inorderTraversalForNodes(root, nodesList);
        return nodesList;
    }

    private void inorderTraversalForNodes(AVLCateNode node, List<AVLCateNode> nodesList) {
        if (node != null) {
            inorderTraversalForNodes(node.leftN, nodesList);
            nodesList.add(node);
            inorderTraversalForNodes(node.rightN, nodesList);
        }
    }
}
