package com.example.chrisetheridge.littleschemer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ActivityHowTo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_how_to);
    }

    public void okayButtonOnTap(View view) {
        Intent i = new Intent(this, LittleSchemerMain_v2.class);

        startActivity(i);

        finish();
    }
}
