package com.example.chrisetheridge.littleschemer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private final String FILE_PATH = "scheme_data.txt";
    private List<ColorScheme> ALL_COLOR_SCHEMES = new ArrayList<>();

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

    public void onNewSchemeTap(View view) {
        Intent i = new Intent(this, ActivityAddScheme.class);

        this.startActivity(i);
    }

    private void cycleColors(LinearLayout btns) {
        ColorScheme color = ALL_COLOR_SCHEMES.get(randomNumberForColors());
        TextView uv = (TextView) findViewById(R.id.color_scheme_user_txt);
        Button likeb = (Button) findViewById(R.id.like_color_btn);
        ImageButton likeib = (ImageButton) findViewById(R.id.like_image_btn);

        for(int i = 0; i < btns.getChildCount(); i++) {
            Button b = (Button) btns.getChildAt(i);

            b.setText(color.Colors[i]);
            b.setBackgroundColor(Color.parseColor(color.Colors[i]));

            Log.d("CHRIS", color.getSchemeForWriting());

            // if the color is liked
            if(color.Liked) {
                likeb.setActivated(false);
                likeib.setColorFilter(Color.RED);
            } else {
                likeb.setActivated(true);
                likeib.setColorFilter(Color.BLACK);
            }
        }

        uv.setText("user: " + color.UserName);
    }

    private void loadColors(Context ctx, String filepath) {
        try {
            List<String[]> colordata = ColorsUtil.FileUtil.loadData(filepath, ",", ctx);

            for(String[] data : colordata) {
                // we know the first 4 of the array is the scheme
                String[] colors = {data[0], data[1], data[2], data[3]};
                // we know the username is the 5th item
                String name = data[4];
                // we know the like status is the last item
                Boolean liked = Boolean.parseBoolean(data[5]);

                ColorScheme s = new ColorScheme(colors, name, liked);

                ALL_COLOR_SCHEMES.add(s);
            }

            Toast.makeText(ctx, "Colors file loaded successfully!", Toast.LENGTH_SHORT).show();
        } catch(IOException e) {
            Toast.makeText(ctx, "There was an error loading the file!", Toast.LENGTH_LONG).show();
        }
    }

    private int randomNumberForColors() {
        Random r = new Random();

        return r.nextInt(ALL_COLOR_SCHEMES.size());
    }
}

