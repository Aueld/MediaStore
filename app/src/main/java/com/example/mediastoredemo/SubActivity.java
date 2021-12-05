package com.example.mediastoredemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SubActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity);

        Intent intent = getIntent();
        //MainActivity가 넘겨준자료을 받는다
//        String str = intent.getStringExtra("자료");

        int size = intent.getIntExtra("크기", 1);

        TextView tv = findViewById(R.id.자료_출력);
        tv.setTextSize(size);
        //tv.setText(str);

        //int x = intent.getIntExtra("AAA", -1);
    }

    public void toMainActivity(View view) {
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
        finish();
    }
}