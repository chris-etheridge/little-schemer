package com.example.chrisetheridge.littleschemer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AssignmentTwoMainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_two_main_menu);
    }

    public void questionOneOnTap(View view) {
        Intent i = new Intent(this, LittleSchemerMain_v1.class);

        startActivity(i);
    }

    public void questionTwoOnTap(View view) {
        Intent i = new Intent(this, LittleSchemerMain_v2.class);

        startActivity(i);
    }
}
