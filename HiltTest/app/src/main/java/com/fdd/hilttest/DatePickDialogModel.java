package com.fdd.hilttest;

import android.content.Context;

import com.codbking.widget.DatePickDialog;
import com.codbking.widget.bean.DateType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.qualifiers.ActivityContext;

@Module
@InstallIn(ActivityComponent.class)
public class DatePickDialogModel {

    @NormalDialog
    @Provides
    public DatePickDialog provideNormalDialog(@ActivityContext Context context) {
        DatePickDialog dialog = new DatePickDialog(context);
        dialog.setType(DateType.TYPE_YMD);
        dialog.setMessageFormat("yyyy-MM-dd");
        return dialog;
    }

    @ComprehensiveDialog
    @Provides
    public DatePickDialog provideComprehensiveDialog(@ActivityContext Context context) {
        DatePickDialog dialog = new DatePickDialog(context);
        dialog.setType(DateType.TYPE_ALL);
        dialog.setMessageFormat("yyyy-MM-dd HH:mm");
        return dialog;
    }

    @Provides
    public DatePickDialog provideDateDialog(@ActivityContext Context context) {
        DatePickDialog dialog = new DatePickDialog(context);
        return dialog;
    }

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface NormalDialog {
    }

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ComprehensiveDialog {
    }
}
