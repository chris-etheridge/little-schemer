package com.example.chrisetheridge.littleschemer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private String[] TEST_ITEMS = {"#b7569a", "#883c82", "#e4f091", "#f9cd76"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
