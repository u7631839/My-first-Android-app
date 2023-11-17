package com.example.myapplication.DataController.UsersTreeTool;


import com.example.myapplication.DataController.AVLMainTree;
import com.example.myapplication.DataController.AVLNode;
import com.example.myapplication.entity.User;

/**
 * This class represents an AVL tree node for goods.
 * It stores a category value and a list of associated goods.
 *
 * @author Rong Sun u6591996
 */
public class AVLUserTree implements AVLMainTree {
    public AVLUserNode root;


    public AVLUserTree() {
    }

    //添加
    public void add(AVLUserNode node){

        if(root==null){
            root = node;
        }else{
            this.root.add(node);
        }

    }


    /**
     * Searches for a node with a specific value in the tree.
     * @author Jolene Sun
     * @param value The value to search for, can be User
     * @return The node with the specified value or null if not found.
     */
    public AVLUserNode search(User value){
        if(this.root==null){
            return null;
        }else{
            return this.root.search(value);
        }
    }

    /**
     * Searches for a node with a specific value in the tree.
     * @author Jolene Sun
     * @param uid The value to search for, can be UserID
     * @return The node with the specified value or null if not found.
     */
    public AVLUserNode searchByID(String uid){
        if(this.root==null){
            return null;
        }else{
            return this.root.searchByID(uid);
        }
    }

    /**
     * Searches for a node's parent  with a specific value in the tree.
     * @author Jolene Sun
     * @param value The value to search for, can be User
     * @return The node's parent with the specified value or null if not found.
     */
    public AVLUserNode searchParent(User value){
        if(this.root==null){
            return null;
        }else{
            return this.root.searchParent(value);
        }
    }

    //delete user

    /**
     * delete User
     * @param value
     * @author Jolene Sun
     */
    public void delNode(User value){
        if(root == null){
            return ;
        }else {
            AVLUserNode target = search(value);
            if(target==null){
                return ;
            }
            if(root.leftN == null && root.rightN == null){
                root = null;
                return ;
            }
            AVLUserNode targetParent = searchParent(value);
            //if target is leaf node
            if(target.leftN == null && target.rightN == null){
                if(targetParent.leftN!=null && targetParent.leftN.value == value){
                    targetParent.leftN = null;
                }else if(targetParent.rightN!=null && targetParent.rightN.value == value){
                    targetParent.rightN = null;


                }

            } else if (target.leftN!=null && target.rightN!=null) {
                AVLUserNode newTarget = delMin(target.rightN);
                target.value= newTarget.value;

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




    //delete the minimum value's node
    public AVLUserNode delMin(AVLUserNode node){
        AVLUserNode newNode = node;
        while (newNode.leftN!=null){
            newNode = newNode.leftN;
        }
        delNode(newNode.value);
        return newNode;

    }

    /**
     * Change the node of old user to new user
     * @param user
     * @param newUser
     * @author Jolene Sun
     */
    public void changeNodeValue(User user,User newUser){
        AVLUserNode node =  search(user);
        if(node!=null){
            System.out.println("changeNodeValue树里的node搜出来了吗："+node.value.getUid());
        }else {
            System.out.println("changeNodeV==null"+node == null);
        }

        delNode(user);
        AVLUserNode nodeShouldNull =  search(user);
        if(nodeShouldNull==null){
            System.out.println("node被成功删除");
        }else{
            System.out.println("node没有被删");
        }
        System.out.println("del没有错");
        AVLUserNode userNode = new AVLUserNode(newUser);
        // node.value = newUser;
        //
        add(userNode);
        System.out.println("changeNodeValue没错");
    }

}
