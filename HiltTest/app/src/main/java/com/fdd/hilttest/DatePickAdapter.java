package com.fdd.hilttest;

import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;

import javax.inject.Inject;

public class DatePickAdapter {

    private final DatePickDialog dialog;

    @Inject
    public DatePickAdapter(DatePickDialog dialog) {
        this.dialog = dialog;
    }

    public DatePickAdapter setDateType(DateType dateType) {
        dialog.setType(dateType);
        return this;
    }

    public DatePickAdapter setMessageFormat() {
        dialog.setMessageFormat("yyyy-MM-dd HH:mm");
        return this;
    }

    public DatePickAdapter setYearLimit(int years) {
        dialog.setYearLimt(years);
        return this;
    }

    public DatePickAdapter setYearLimit(String title) {
        dialog.setTitle(title);
        return this;
    }

    public DatePickAdapter setOnSureCallback(OnSureLisener sureCallback) {
        dialog.setOnSureLisener(sureCallback);
        return this;
    }

    public void show() {
        if (dialog.isShowing()) {
            return;
        }
        dialog.show();
    }

    public void dismiss() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
