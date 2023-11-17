package com.example.myapplication.DataController.GoodsTreeTool;

import com.example.myapplication.DataController.AVLNode;
import com.example.myapplication.entity.Good;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents an AVL tree node for categories.
 * It stores a special cate(combine of name, brand, category) value and a list of associated goods.
 *
 * @author Rong Sun u6591996
 */
public class AVLCateNode extends AVLNode {

    public String value;
    AVLCateNode leftN;
    AVLCateNode rightN;
    public List<Good> goodList = new ArrayList<>();


    public AVLCateNode(String value, List<Good> goodList) {
        super(value);
        this.value = value;
        this.goodList = goodList;
    }


    /**
     * Searches for a node with a specific value in the tree.
     * @author Jolene Sun
     * @param val The value to search for.
     * @return The node with the specified value or null if not found.
     */
    public AVLCateNode search(String val){
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
     * @param val The value to search for.
     * @return The parent node of the node with the specified value or null if not found.
     */
    public AVLCateNode searchParent(String val){
        if((this.leftN!=null && this.leftN.value.equals(val))||(this.rightN!=null && this.rightN.value.equals(val))){
            return this;
        }else if(this.value.compareTo(val)>0 && this.leftN !=null){
            return this.leftN.searchParent(val);
        }else if(this.value.compareTo(val)<=0 && this.rightN !=null){
            return this.rightN.searchParent(val);
        }else{
            return null;
        }
    }

    //mid order method
    public void midOrder(){
        if(this.leftN!=null){
            this.leftN.midOrder();
        }
        System.out.println(this.value);
        if(this.rightN!=null){
            this.rightN.midOrder();
        }
    }
    /**
     * Performs a left rotation on the node.
     */
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
    //返回以该节点为根节点的树的高度
    /**
     * Performs height
     */
    public int getHeight(){
        return Math.max(this.leftN==null? 0: this.leftN.getHeight(),this.rightN==null?0:this.rightN.getHeight())+1;
    }

    //left rotate
    public void leftRotate(){
        AVLCateNode newNode = new AVLCateNode(this.value,this.goodList);
        newNode.leftN = this.leftN;
        newNode.rightN = this.rightN.leftN;
        this.value = this.rightN.value;
        this.goodList = this.rightN.goodList;
        this.rightN = this.rightN.rightN;
        this.leftN = newNode;
    }

    //right rotete
    public void rightRotate(){
        AVLCateNode newNode = new AVLCateNode(this.value, this.goodList);

        newNode.rightN = this.rightN;
        newNode.leftN = this.leftN.rightN;
        this.value = this.leftN.value;
        this.goodList = this.leftN.goodList;
        this.leftN = this.leftN.leftN;
        this.rightN = newNode;
    }


    //add a node to the node
    public void add(AVLCateNode node){

        //long a=System.currentTimeMillis();
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
        // System.out.println(System.currentTimeMillis()-a);
    }

    //search the node



    //to String method
    @Override
    public String toString() {
        return "AVLCateNode{" +
                "value='" + value + '\'' +
                ", leftN=" + leftN +
                ", rightN=" + rightN +
                ", goodList=" + goodList +
                '}';
    }


}
