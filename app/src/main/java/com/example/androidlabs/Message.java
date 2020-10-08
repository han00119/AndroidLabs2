package com.example.androidlabs;

public class Message {

        private String message;
        private int messageType;

        public Message(int messageType, String message) {
            this.message = message;
            this.messageType = messageType;
        }

        public String getMessage() {
            return message;
        }

        public int getMessageType () {
        return messageType;
        }

        public void setMessage(String message) {

            this.message = message;
        }

        public void setMessageType(int messageType) {
            this.messageType = messageType;
        }
    }

