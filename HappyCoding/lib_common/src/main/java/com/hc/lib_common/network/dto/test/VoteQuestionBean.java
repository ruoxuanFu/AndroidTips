package com.hc.lib_common.network.dto.test;

import java.util.List;

/**
 * @author furuoxuan
 * 投票 题目列表
 */
public class VoteQuestionBean {

    private String prompt;
    private int type;
    private List<VoteQuestionOptionBean> options;

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<VoteQuestionOptionBean> getOptions() {
        return options;
    }

    public void setOptions(List<VoteQuestionOptionBean> options) {
        this.options = options;
    }

}
