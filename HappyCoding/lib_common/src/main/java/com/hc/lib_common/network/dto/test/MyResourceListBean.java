package com.hc.lib_common.network.dto.test;

import com.google.gson.annotations.SerializedName;
import com.hc.lib_common.network.dto.BaseResponse;

import java.util.List;

/**
 * @author furuoxuan
 */
public class MyResourceListBean extends BaseResponse {

    @SerializedName("matList")
    private List<ResourceBean> resourceBeanList;

    public List<ResourceBean> getResourceBeanList() {
        return resourceBeanList;
    }

    public void setResourceBeanList(List<ResourceBean> resourceBeanList) {
        this.resourceBeanList = resourceBeanList;
    }
}
