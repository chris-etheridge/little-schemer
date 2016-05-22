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

import com.example.chrisetheridge.littleschemer.model.ColorScheme;
import com.example.chrisetheridge.littleschemer.utils.ColorsUtil;
import com.example.chrisetheridge.littleschemer.utils.DBUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// v2 uses an sql lite db
public class LittleSchemerMain_v2 extends AppCompatActivity {
    private final String SEED_DATA_PATH = "scheme_data.txt";
    private List<ColorScheme> ALL_COLOR_SCHEMES = new ArrayList<>();

    private DBUtil _db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_little_schemer_v2);
        Intent i = getIntent();

        _db = new DBUtil(this);

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
        // check if our db exists or not
        try {
            _db.open();

            _db.runSchemerInstall(SEED_DATA_PATH, ctx);

            _db.close();
        } catch (IOException e) {

        }

        loadColors(ctx);

        LinearLayout btns = (LinearLayout) findViewById(R.id.color_btns);

        cycleColors(btns);
    }

    public void onColorChangeViewTap(View view) {
        LinearLayout btns = (LinearLayout) findViewById(R.id.color_btns);

        cycleColors(btns);
    }

    public void onNewSchemeTap(View view) {
        Intent i = new Intent(this, ActivityAddScheme_v1.class);

        this.startActivity(i);
    }

    private void cycleColors(LinearLayout btns) {
        ColorScheme color = ALL_COLOR_SCHEMES.get(randomNumberForColors());

        setUiButtons(btns, color, this);
    }

    private void loadColors(Context ctx) {
        try {
            _db.open();

            ALL_COLOR_SCHEMES = _db.getAllSchemes();

            _db.close();

            Toast.makeText(ctx, "Colors loaded successfully!", Toast.LENGTH_SHORT).show();
        } catch(Exception e) {
            Toast.makeText(ctx, "There was an error loading the colors!", Toast.LENGTH_LONG).show();
        }
    }

    private int randomNumberForColors() {
        Random r = new Random();

        return r.nextInt(ALL_COLOR_SCHEMES.size());
    }

    private void setUiButtons(LinearLayout btns, ColorScheme color, Context ctx) {
        TextView uv = (TextView) findViewById(R.id.color_scheme_user_txt);

        for(int i = 0; i < btns.getChildCount(); i++) {
            Button b = (Button) btns.getChildAt(i);

            b.setText(color.Colors[i]);
            b.setBackgroundColor(Color.parseColor(color.Colors[i]));

        }

        uv.setText("user: " + color.UserName);
    }
}
