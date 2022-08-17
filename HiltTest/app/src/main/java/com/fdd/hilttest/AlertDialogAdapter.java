package com.fdd.hilttest;

import androidx.appcompat.app.AlertDialog;

import javax.inject.Inject;

public class AlertDialogAdapter {
    private final AlertDialog.Builder dialogBuilder;

    @Inject
    public AlertDialogAdapter(AlertDialog.Builder dialogBuilder) {
        this.dialogBuilder = dialogBuilder;
    }

    public AlertDialog.Builder getDialogBuilder() {
        return dialogBuilder;
    }
}
