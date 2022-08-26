package com.hc.lib_common.network.dto.test;

import android.text.TextUtils;

/**
 * @author frx
 * 类型
 */
public class ResourceBean {

    /**
     * 素材id
     */
    private int id;
    private String title;

    /**
     * 创建或收藏时间
     */
    private String time;

    /**
     * 类型
     * 101 在线作业
     * 102 线下作业
     * 103 作文作业
     * 104 打卡作业
     * 105 班级公告
     * 106 班级资料
     * 107 投票
     * 201 音频（WE资源）
     * 202 视频（WE资源）
     * 203 微课（WE资源）
     * 204 阅读（WE资源）
     * 205 课件（WE资源）
     * 206 材料（WE资源）
     */
    private int type;

    /**
     * 是否已共享("true"/"false")
     */
    private String isshare;

    /**
     * WE资源的信息
     */
    private String weInfo;
    private WeInfoBean weInfoBean;

    private int grade;
    private String teaName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getIsshare() {
        return isshare;
    }

    public void setIsshare(String isshare) {
        this.isshare = isshare;
    }

    public boolean isShare() {
        return TextUtils.equals(isshare, "true");
    }

    public String getWeInfo() {
        return weInfo;
    }

    public void setWeInfo(String weInfo) {
        this.weInfo = weInfo;
    }

    public WeInfoBean getWeInfoBean() {
        return weInfoBean;
    }

    public void setWeInfoBean(WeInfoBean weInfoBean) {
        this.weInfoBean = weInfoBean;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getTeaName() {
        return teaName;
    }

    public void setTeaName(String teaName) {
        this.teaName = teaName;
    }
}
