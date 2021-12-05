package com.example.mediastoredemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;

import android.Manifest;
//import android.app.DialogFragment;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private long[] ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestPermission();
    }

    public void onShowList(View view) {
        String[] projection = new String[]{
                MediaStore.Images.Media._ID,//아이디
                MediaStore.Images.Media.DISPLAY_NAME//파일이름
        };
        Cursor cursor = getApplicationContext().getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection, null, null, null
        );
        ids = new long[cursor.getCount()];
        ArrayList<String> names = new ArrayList<>();
        int idCol = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
        int nameCol = cursor.getColumnIndexOrThrow(
                MediaStore.Images.Media.DISPLAY_NAME);
        int i = 0;
        while (cursor.moveToNext()) {
//그림파일의아이디
            ids[i++] = cursor.getLong(idCol);
//그림파일이름
            names.add(cursor.getString(nameCol));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, names);
        ListView listView = findViewById(R.id.images);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
//아이디로부터URI가져오기
                Uri uri = ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, ids[i]);
//URI를문자열로변환하여저장
                bundle.putString("URI", uri.toString());
                DialogFragment fragment = new ImageDialogFragment();
                fragment.setArguments(bundle);
                fragment.show(getSupportFragmentManager(), "IMAGE_DIALOG");
            }
        });
    }


    private static final int REQUEST_PERMISSION = 1;

    public void requestPermission() {
        //이미허가되었는지알아본다.
        int permission = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            //허가요청항목
            String[] permissions = new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE
            };
            //허가를요청한다.
            ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] result) {
        super.onRequestPermissionsResult(requestCode, permissions, result);
        if (requestCode == REQUEST_PERMISSION) {
            if (result.length == 0 ||
                    result[0] != PackageManager.PERMISSION_GRANTED) {
                //허가되지않음
                finish();
            }
        }
    }

    public void onActivity(View view) {
        Intent intent = new Intent(this, SubActivity.class);
        int size = 0;

        EditText et = findViewById(R.id.edit_자료);
//        String str = et.getText().toString();
        if(et.getText().toString().length() <= 0)
            size = 1;
        else
            size = Integer.parseInt(et.getText().toString());

        intent.putExtra("크기", size);
//        intent.putExtra("자료",str);

        startActivity(intent);

    }
}