package com.prj.entity;

public class User {
    private int id;
    private String uname;

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    private String pwd;
    private String typeclass;
    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }



    public String getTypeclass() {
        return typeclass;
    }

    public void setTypeclass(String typeclass) {
        this.typeclass = typeclass;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
