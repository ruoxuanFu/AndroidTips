package com.hc.lib_common.network.dto.test;

/**
 * @author furuoxuan
 * 在线作业 自动评分规则
 */
public class ScoreruleInfoBean {

    private String minute;
    private String minutePercent;
    private String wordcount;
    private String wordcountPercent;
    private String keywords;
    private String keywordsPercent;

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public String getMinutePercent() {
        return minutePercent;
    }

    public void setMinutePercent(String minutePercent) {
        this.minutePercent = minutePercent;
    }

    public String getWordcount() {
        return wordcount;
    }

    public void setWordcount(String wordcount) {
        this.wordcount = wordcount;
    }

    public String getWordcountPercent() {
        return wordcountPercent;
    }

    public void setWordcountPercent(String wordcountPercent) {
        this.wordcountPercent = wordcountPercent;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getKeywordsPercent() {
        return keywordsPercent;
    }

    public void setKeywordsPercent(String keywordsPercent) {
        this.keywordsPercent = keywordsPercent;
    }
}
