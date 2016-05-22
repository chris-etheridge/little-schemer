package com.example.chrisetheridge.littleschemer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private String[] TEST_ITEMS = {"#b7569a", "#883c82", "#e4f091", "#f9cd76"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onColorChangeViewTap(View view) {}

    private void cycleColors() {}
}
