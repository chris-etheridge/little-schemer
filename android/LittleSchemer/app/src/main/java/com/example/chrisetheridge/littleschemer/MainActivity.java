package com.example.chrisetheridge.littleschemer;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private final String FILE_PATH = "clean_data.txt";
    private List<String[]> ALL_COLORS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUi(this);
    }

    // initializes the ui
    private void initUi(Context ctx) {
        loadColors(ctx, FILE_PATH);

        LinearLayout btns = (LinearLayout) findViewById(R.id.color_btns);

        cycleColors(btns);
    }

    public void onColorChangeViewTap(View view) {
        LinearLayout btns = (LinearLayout) findViewById(R.id.color_btns);

        cycleColors(btns);
    }

    private void cycleColors(LinearLayout btns) {
        String[] colors = ALL_COLORS.get(randomNumberForColors());

        for(int i = 0; i < btns.getChildCount(); i++) {
            Button b = (Button) btns.getChildAt(i);

            b.setText(colors[i]);
            b.setBackgroundColor(Color.parseColor(colors[i]));
        }
    }

    private void loadColors(Context ctx, String filepath) {
        ALL_COLORS =  ColorFiles.Util.loadData(filepath, ",", ctx);
    }

    private int randomNumberForColors() {
        Random r = new Random();

        return r.nextInt(ALL_COLORS.size());
    }
}

