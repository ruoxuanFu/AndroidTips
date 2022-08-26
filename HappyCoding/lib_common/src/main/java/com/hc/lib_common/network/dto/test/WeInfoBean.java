package com.hc.lib_common.network.dto.test;

import android.text.TextUtils;

import java.util.List;

/**
 * @author frx
 * 字段说明：http://192.168.8.191:8090/pages/viewpage.action?pageId=25493527
 */
public class WeInfoBean {
    private String title;
    private String content;
    private String reference;
    private int showrefrule;
    private ScoreruleInfoBean scorerule;
    private String fileidlist;
    private String needreply;
    private String needgroup;
    private String hint;
    private String sample;
    private int period;
    private String type;
    private String link;
    private List<VoteQuestionBean> questionList;
    private int we_rid;
    private int duration;
    private String image;
    private int speed;
    private String source;
    private String lecturer;
    private int wordcount;
    private String difficult;
    private String filesize;
    private String ext;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public int getShowrefrule() {
        return showrefrule;
    }

    public void setShowrefrule(int showrefrule) {
        this.showrefrule = showrefrule;
    }

    public ScoreruleInfoBean getScorerule() {
        return scorerule;
    }

    public void setScorerule(ScoreruleInfoBean scorerule) {
        this.scorerule = scorerule;
    }

    public String getFileidlist() {
        return fileidlist;
    }

    public void setFileidlist(String fileidlist) {
        this.fileidlist = fileidlist;
    }

    public String getNeedreply() {
        return needreply;
    }

    public boolean isNeedreply() {
        return TextUtils.equals(needreply, "true");
    }

    public void setNeedreply(String needreply) {
        this.needreply = needreply;
    }

    public String getNeedgroup() {
        return needgroup;
    }

    public boolean isNeedgroup() {
        return TextUtils.equals(needgroup, "true");
    }

    public void setNeedgroup(String needgroup) {
        this.needgroup = needgroup;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getSample() {
        return sample;
    }

    public void setSample(String sample) {
        this.sample = sample;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public List<VoteQuestionBean> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<VoteQuestionBean> questionList) {
        this.questionList = questionList;
    }

    public int getWe_rid() {
        return we_rid;
    }

    public void setWe_rid(int we_rid) {
        this.we_rid = we_rid;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public int getWordcount() {
        return wordcount;
    }

    public void setWordcount(int wordcount) {
        this.wordcount = wordcount;
    }

    public String getDifficult() {
        return difficult;
    }

    public void setDifficult(String difficult) {
        this.difficult = difficult;
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }
}
