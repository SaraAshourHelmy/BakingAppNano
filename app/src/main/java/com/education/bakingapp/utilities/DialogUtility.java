package com.education.bakingapp.utilities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;


import com.education.bakingapp.R;


public class DialogUtility {

    static ProgressDialog progressDialog;
    static Dialog dialog;

    public static void showProgressDialog(Context context) {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getString(R.string.wait));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public static void dismissProgressDialog() {
        if (progressDialog != null)
            progressDialog.dismiss();
    }

    public static void showMessageDialog(Context context, String msg) {

        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }

        dialog = new AlertDialog.Builder(context)
                .setMessage(msg)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                })
                .create();
        dialog.show();
    }
}
