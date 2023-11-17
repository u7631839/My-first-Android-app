package com.example.myapplication.DataController.UsersTreeTool;

import com.example.myapplication.DataController.AVLNode;
import com.example.myapplication.entity.Good;
import com.example.myapplication.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents an AVL tree node for categories.
 * It stores a User value and a list of associated goods.
 *
 * @author Rong Sun u6591996
 */
public class AVLUserNode extends AVLNode {

    public User value;
    public AVLUserNode leftN;
    public  AVLUserNode rightN;


    public AVLUserNode(User value) {
        super(value);
        this.value = value;
    }


    /**
     * Searches for a node with a specific value in the tree.
     * @author Jolene Sun
     * @param val The value to search for.
     * @return The node with the specified value or null if not found.
     */
    public AVLUserNode search(User val){
        if(val.equals(this.value)){
            return this;

        }else if(val.compareTo(value)>0){
            if(this.rightN == null){
                return null;
            }
            return this.rightN.search(val);
        }else{
            if(this.leftN == null){
                return null;
            }
            return this.leftN.search(val);
        }

    }

    /**
     * Searches for the parent node of a node with a specific value in the tree.
     * @author Jolene Sun
     * @param uid The value to search for.
     * @return The parent node of the node with the specified value or null if not found.
     */
    public AVLUserNode searchByID(String uid){
        if(uid.equals(this.value.getUid())) {
            return this;
        }

        AVLUserNode result = null;

        // 遍历左子树
        if (this.leftN != null) {
            result = this.leftN.searchByID(uid);
        }

        // 如果在左子树中找到了匹配的节点，直接返回
        if (result != null) {
            return result;
        }

        // 遍历右子树
        if (this.rightN != null) {
            result = this.rightN.searchByID(uid);
        }
        return result;

    }


    //find the parent node
    public AVLUserNode searchParent(User val){
       /* if((this.leftN!=null && this.leftN.value == val)||(this.rightN!=null && this.rightN.value == val)){
            return this;
        }else if(this.value.compareTo(val)>0 && this.leftN !=null){
            return this.leftN.searchParent(val);
        }else if(this.value.compareTo(val)<=0 && this.rightN !=null){
            return  this.rightN.searchParent(val);
        }else{
            return null;
        }*/
        if((this.leftN!=null && this.leftN.value.equals(val) )||(this.rightN!=null && this.rightN.value.equals(val) )){
            return this;
        }else {
            if (val.compareTo(this.value) < 0 && this.leftN != null) {
                return this.leftN.searchParent(val);
            } else if (this.value.compareTo(val) <= 0 && this.rightN != null) {
                return this.rightN.searchParent(val);
            } else {
                return null;
            }
        }
    }


    /**
     * Performs a left rotation on the node.
     */
    //返回左子树高度
    public int getLHeight(){
        if(leftN==null){
            return 0;
        }
        return leftN.getHeight();
    }
    /**
     * Performs a right rotation on the node.
     */
    public int getRHeight(){
        if(rightN==null){
            return 0;
        }
        return rightN.getHeight();
    }
    //preform the height
    public int getHeight(){
        return Math.max(this.leftN==null? 0: this.leftN.getHeight(),this.rightN==null?0:this.rightN.getHeight())+1;
    }

    //left rotate
    public void leftRotate(){
        AVLUserNode newNode = new AVLUserNode(this.value);
        newNode.leftN = this.leftN;
        newNode.rightN = this.rightN.leftN;
        this.value = this.rightN.value;
        this.rightN = this.rightN.rightN;
        this.leftN = newNode;
    }

    //right rotate
    public void rightRotate(){
        AVLUserNode newNode = new AVLUserNode(this.value);
        newNode.rightN = this.rightN;
        newNode.leftN = this.leftN.rightN;
        this.value = this.leftN.value;
        this.leftN = this.leftN.leftN;
        this.rightN = newNode;
    }


    //add a node to the node
    public void add(AVLUserNode node){
        //  System.out.println("add 111");
        if(node == null){
            return;
        }
        if(this.value.compareTo(node.value)>0){
            if(this.leftN ==null){
                this.leftN = node;
            }else{
                this.leftN.add(node);
            }

        }else {
            if(this.rightN ==null){
                this.rightN = node;
            }else{
                this.rightN.add(node);
            }
        }

        // If the height of the right subtree is greater than that of the left subtree by more than 1, rotate left
        if(this.getRHeight() - this.getLHeight()>1){
            if(rightN!=null && this.rightN.getRHeight() - this.rightN.getLHeight() < 0){
                rightN.rightRotate();
                leftRotate();
            }else {
                leftRotate();
            }
            return;
        }

        // If the height of the left subtree is greater than that of the right subtree by more than 1, rotate left
        if(this.getLHeight() - this.getRHeight()>1){
            if(leftN!=null && this.leftN.getLHeight() - this.leftN.getRHeight() < 0){
                leftN.leftRotate();
                rightRotate();
            }else {
                rightRotate();
            }

        }
    }



}
