package com.example.androidlabs;

public class Message {

        private String message;
        private int messageType;
        private boolean isSent;
        private boolean isReceived;
        private long id;

        public Message(int messageType, String message) {
            this.message = message;
            this.messageType = messageType;
        }

        public Message(String Message, Boolean Send, long ID){
            this.message = Message;
            this.isSent = Send;
            this.id = ID;
        }
        public Message( String message) {
           this.message = message;

    }
        public String getMessage() {

            return message;
        }

        public int getMessageType () {

            return messageType;
        }

        public boolean getIsSent () {
            return isSent;
        }

        public boolean getIsReceived(){
            return isReceived;
        }

        public long getId () {
            return id;
        }

        public void setMessage(String message) {

            this.message = message;
        }

        public void setMessageType(int messageType) {

            this.messageType = messageType;
        }

        public void setIsSent () {
            this.isSent = isSent;
        }

        public void setIsReceived () {
            this.isReceived = isReceived;
        }

        public void setId () {
            this.id = id;
        }


    }



