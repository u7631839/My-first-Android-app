package com.example.myapplication.conversation;

/**
 * message class using to record all the info of message
 * @author Shiyu Pan u7615103
 */
public class Message {

    private String content;
    private String senderID;
    private String receiverID;
    private long message_time;

    public Message(String content, String getSenderID, String receiverID){
        this.content=content;
        this.senderID=getSenderID;
        this.receiverID=receiverID;
        message_time=System.currentTimeMillis()/1000;
    }

    public Message() {
        super();
    }

    public String getContent() {
        return content;
    }

    public String getSenderID() {
        return senderID;
    }

    public String getReceiverID() {
        return receiverID;
    }
    public long getMessage_time(){return message_time;}

    public void setContent(String content) {
        this.content = content;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public void setReceiverID(String receiverID) {
        this.receiverID = receiverID;
    }
}
