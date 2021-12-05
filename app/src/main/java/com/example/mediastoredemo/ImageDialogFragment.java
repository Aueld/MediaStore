package com.example.mediastoredemo;

import androidx.fragment.app.DialogFragment;
import android.app.AlertDialog;
import android.app.Dialog;
//import android.app.DialogFragment;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

public class ImageDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//문자열을URI로변환
        Uri uri = Uri.parse(getArguments().getString("URI"));
        ImageView iv = new ImageView(getContext());
        iv.setImageURI(uri);
        return new AlertDialog.Builder(getContext())
                .setTitle("전혀 성장 하지 않았구나 쿠크루삥뽕")
                .setView(iv)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
    }
}
