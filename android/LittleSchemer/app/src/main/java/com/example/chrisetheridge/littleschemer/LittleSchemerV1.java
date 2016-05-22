package com.example.chrisetheridge.littleschemer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// v1 uses files
public class LittleSchemerV1 extends AppCompatActivity {

    private final String SEED_DATA_PATH = "scheme_data.txt";
    private final String SAVE_FILE_PATH = "schemes.txt";
    private List<ColorScheme> ALL_COLOR_SCHEMES = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_little_schemer_v1);
        Intent i = getIntent();

        if(i.getExtras() != null) {
            if(i.getStringExtra("new_scheme_data") != null) {
                String newscheme = i.getStringExtra("new_scheme_data");

                ColorScheme s = ColorsUtil.FileUtil.parseLine(newscheme, ",");
                LinearLayout btns = (LinearLayout) findViewById(R.id.color_btns);

                setUiButtons(btns, s, this);
            }
        } else {
            initUi(this);
        }
    }

    // initializes the ui
    private void initUi(Context ctx) {
        loadColors(ctx, SEED_DATA_PATH);

        // check if our db exists or not
        if(!checkForColorsDb()) {
            try {
                // setup the data if it does not exist
                ColorsUtil.FileUtil.setupData(SEED_DATA_PATH, SAVE_FILE_PATH, ",", this);
            } catch (IOException e) {

            }
        }

        LinearLayout btns = (LinearLayout) findViewById(R.id.color_btns);

        cycleColors(btns);
    }

    public void onColorChangeViewTap(View view) {
        LinearLayout btns = (LinearLayout) findViewById(R.id.color_btns);

        cycleColors(btns);
    }

    public void onNewSchemeTap(View view) {
        Intent i = new Intent(this, ActivityAddScheme.class);

        this.startActivity(i);
    }

    private void cycleColors(LinearLayout btns) {
        ColorScheme color = ALL_COLOR_SCHEMES.get(randomNumberForColors());

        setUiButtons(btns, color, this);
    }

    private void loadColors(Context ctx, String filepath) {
        try {
            ALL_COLOR_SCHEMES = ColorsUtil.FileUtil.loadData(SEED_DATA_PATH, ",", this);

            Toast.makeText(ctx, "Colors file loaded successfully!", Toast.LENGTH_SHORT).show();
        } catch(IOException e) {
            Toast.makeText(ctx, "There was an error loading the file!", Toast.LENGTH_LONG).show();
        }
    }

    private int randomNumberForColors() {
        Random r = new Random();

        return r.nextInt(ALL_COLOR_SCHEMES.size());
    }

    private boolean checkForColorsDb() {
        File f = new File("schemes.txt");

        Log.d("CHRIS", f.exists() + " ");

        return f.exists();
    }

    private void setUiButtons(LinearLayout btns, ColorScheme color, Context ctx) {
        TextView uv = (TextView) findViewById(R.id.color_scheme_user_txt);
        Button likeb = (Button) findViewById(R.id.like_color_btn);
        //ImageButton likeib = (ImageButton) findViewById(R.id.like_image_btn);

        for(int i = 0; i < btns.getChildCount(); i++) {
            Button b = (Button) btns.getChildAt(i);

            b.setText(color.Colors[i]);
            b.setBackgroundColor(Color.parseColor(color.Colors[i]));

            /* if the color is liked
            if(color.Liked) {
                likeb.setActivated(false);
                likeib.setColorFilter(Color.RED);
            } else {
                likeb.setActivated(true);
                likeib.setColorFilter(Color.BLACK);
            }*/
        }

        uv.setText("user: " + color.UserName);
    }
}

