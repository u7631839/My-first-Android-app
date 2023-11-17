package com.example.myapplication.DataController;

/**
 * This class represents an AVL tree node for categories.
 * It stores a T value and a list of associated goods.
 *
 * @author Rong Sun u6591996
 */
public class AVLNode <T extends Comparable<T>> {

    public T value;
    public AVLNode<T> leftN;
    public AVLNode<T> rightN;

    public  AVLNode(T value) {
        this.value = value;
    }

    /**
     * Searches for a node with a specific value in the tree.
     * @author Jolene Sun
     * @param val The value to search for.
     * @return The node with the specified value or null if not found.
     */
    public AVLNode search(T val){
        if(val == this.value){
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
    //find the parent node
    public AVLNode searchParent(T val){
        if((this.leftN!=null && this.leftN.value == val)||(this.rightN!=null && this.rightN.value == val)){
            return this;
        }else if(this.value.compareTo(val)>0 && this.leftN !=null){
            return this.leftN.searchParent(val);
        }else if(this.value.compareTo(val)<=0 && this.rightN !=null){
            return  this.rightN.searchParent(val);
        }else{
            return null;
        }
    }

    //中序遍历
    public void midOrder(){
        if(this.leftN!=null){
            this.leftN.midOrder();
        }
        System.out.println(this.value);
        if(this.rightN!=null){
            this.rightN.midOrder();
        }
    }

    public int getLHeight(){
        if(leftN==null){
            return 0;
        }
        return leftN.getHeight();
    }

    /**
     * @author Jolene Sun
     * Performs a left rotation on the node.
     */
    public int getRHeight(){
        if(rightN==null){
            return 0;
        }
        return rightN.getHeight();
    }
    //perform the height
    public int getHeight(){
        return Math.max(this.leftN==null? 0: this.leftN.getHeight(),this.rightN==null?0:this.rightN.getHeight())+1;
    }

    /**
     * Performs a right rotation on the node.
     */
    public void leftRotate(){
        AVLNode <T> newNode = new AVLNode(this.value);
        newNode.leftN = this.leftN;
        newNode.rightN = this.rightN.leftN;
        this.value = this.rightN.value;
        this.rightN = this.rightN.rightN;
        this.leftN = newNode;
    }

    //right rotate
    public void rightRotate(){
        AVLNode<T> newNode = new AVLNode(this.value);
        newNode.leftN = this.leftN.rightN;
        newNode.rightN = this.rightN;
        this.value = this.leftN.value;
        this.leftN = this.leftN.leftN;
        this.rightN = newNode;
    }


    //add node

    /**
     * add the node
     * @author Jolene Sun
     * @param node the node add to the ress
     */
    public void add(AVLNode<T> node){
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

    //search the node



}
