package com.example.chrisetheridge.littleschemer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MenuActivity_v2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void schemesButtonOnTap(View view) {
        Intent i = new Intent(this, LittleSchemerMain_v2.class);

        startActivity(i);
    }

    public void howToButtonOnTap(View view) {
        Intent i = new Intent(this, ActivityHowTo.class);

        startActivity(i);
    }
}
