package com.fdd.mydagger2test.loginmvptest.mvp.bean;

public class LoginInfo {

    private boolean success;
    private String name;
    private int id;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "LoginInfo{" +
                "success=" + success +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
