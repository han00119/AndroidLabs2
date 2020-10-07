package com.example.androidlabs;

public class Message {

        private String msg;
        private int msgType;

        public Message(int msgType, String msg) {
            this.msg = msg;
            this.msgType = msgType;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public int getMsgType() {
            return msgType;
        }

        public void setMsgType(int msgType) {
            this.msgType = msgType;
        }
    }

