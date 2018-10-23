package com.myallsimpledemo.singleton;

public class SingletonActivityVo {
    private static SingletonActivityVo instance;

    public static SingletonActivityVo getInstance() {
        if (instance == null) {
            synchronized (SingletonActivityVo.class) {
                if (instance == null) {
                    instance = new SingletonActivityVo();
                }
            }
        }
        return instance;
    }

    public void destroy() {
        System.gc();
        instance = null;
    }

    private String name;

    private String phone;

    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
