package com.example.myapplication.bean;

/**
 * Created by YLSN on 2020/9/8.
 */

public class MessageBean {
    private String name;
    public MessageBean(String name) {//构造方法，用以赋值
        this.name = name;
    }

    public String getName() {//获得Name的值
        return name;
    }
}
