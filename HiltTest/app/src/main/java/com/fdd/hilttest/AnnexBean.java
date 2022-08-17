package com.fdd.hilttest;

public class AnnexBean {

    private String id;

    private String fileName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "AnnexBean{" +
                "id='" + id + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
