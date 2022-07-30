package com.festdepartment.awaaz_e_bezuban.Model;

public class Chat {
    String sender, receiver, chat,chatTime,senderName,receiverName, chatID;

    public Chat() {
    }


    public Chat(String sender, String receiver, String chat, String chatTime, String senderName, String receiverName) {
        this.sender = sender;
        this.receiver = receiver;
        this.chat = chat;
        this.chatTime = chatTime;
        this.senderName = senderName;
        this.receiverName = receiverName;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }

    public String getChatTime() {
        return chatTime;
    }

    public void setChatTime(String chatTime) {
        this.chatTime = chatTime;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getChatID() {
        return chatID;
    }

    public void setChatID(String chatID) {
        this.chatID = chatID;
    }
}
