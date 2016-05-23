package com.example.chrisetheridge.littleschemer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chrisetheridge.littleschemer.model.ColorScheme;
import com.example.chrisetheridge.littleschemer.utils.ColorsUtil;
import com.example.chrisetheridge.littleschemer.utils.DBUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// v2 uses an sql lite db
public class LittleSchemerMain_v2 extends AppCompatActivity {
    // seed data file path constant
    private final String SEED_DATA_PATH = "scheme_data.txt";

    // all our color schemes
    private List<ColorScheme> ALL_COLOR_SCHEMES = new ArrayList<>();

    // our current color scheme
    private ColorScheme CURRENT_SCHEME;

    // db
    private DBUtil _db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_little_schemer_v2);
        Intent i = getIntent();

        // create our db
        _db = new DBUtil(this);

        // check if the intent has extras
        if(i.getExtras() != null) {
            if(i.getStringExtra("new_scheme_data") != null) {
                String newscheme = i.getStringExtra("new_scheme_data");

                // parse the extra into a colorscheme
                ColorScheme s = ColorsUtil.FileUtil.parseLine(newscheme, ",");
                LinearLayout btns = (LinearLayout) findViewById(R.id.color_btns);

                // set our current scheme
                CURRENT_SCHEME = s;

                // setup the ui
                setUiButtons(btns, s, this);

                // show the user a success message
                Toast.makeText(this, "Color saved successfully!", Toast.LENGTH_SHORT);
            }
        } else {
            // init the ui
            initUi(this);
        }
    }

    // method for when the color view is tapped
    public void onColorChangeViewTap(View view) {
        LinearLayout btns = (LinearLayout) findViewById(R.id.color_btns);

        cycleColors(btns);
    }

    // method for when the new button is tapped
    public void onNewSchemeTap(View view) {
        Intent i = new Intent(this, AddScheme_v2.class);

        this.startActivity(i);
    }

    // quits the app
    public void onQuitButtonTap(View view) {
        this.finish();
        System.exit(0);
    }

    public void onDeleteButtonTap(View view) {
        _db.open();

        _db.deleteScheme(CURRENT_SCHEME._id);

        _db.close();

        // show a toast
        Toast.makeText(this, "Scheme deleted!", Toast.LENGTH_SHORT).show();

        // cycle the colors
        LinearLayout btns = (LinearLayout) findViewById(R.id.color_btns);

        cycleColors(btns);
    }

    // like the scheme
    public void onLikeButtonTap(View view) {
        // make the color scheme liked
        CURRENT_SCHEME.Liked = 1;

        // update
        _db.open();

        _db.updateScheme(CURRENT_SCHEME);

        _db.close();

        // update our ui to show the liked
        ImageButton likeib = (ImageButton) findViewById(R.id.like_image_btn);

        likeib.setColorFilter(Color.RED);

        // show a toast
        Toast.makeText(this, "Scheme liked!", Toast.LENGTH_SHORT).show();
    }

    /// PRIVATE METHODS

    // method to cycle a random color scheme
    private void cycleColors(LinearLayout btns) {
        // get a random color scheme
        ColorScheme color = ALL_COLOR_SCHEMES.get(randomNumberForColors());

        // set our current scheme up
        CURRENT_SCHEME = color;

        // setup the ui with the new scheme
        setUiButtons(btns, color, this);
    }

    // initializes the ui
    private void initUi(Context ctx) {
        LinearLayout btns = (LinearLayout) findViewById(R.id.color_btns);

        // load the colors
        loadColors(this);

        // cycle the colors
        cycleColors(btns);
    }

    // loads our colors from the db
    private void loadColors(Context ctx) {
        try {
            _db.open();

            // get all the schemes
            ALL_COLOR_SCHEMES = _db.getAllSchemes();

            _db.close();

            // show a success message
            Toast.makeText(ctx, "Colors loaded successfully!", Toast.LENGTH_SHORT).show();
        } catch(Exception e) {
            // show an error message if there was one
            Toast.makeText(ctx, "There was an error loading the colors!", Toast.LENGTH_LONG).show();
        }
    }

    // get a random number from the length of color schemes
    private int randomNumberForColors() {
        Random r = new Random();

        return r.nextInt(ALL_COLOR_SCHEMES.size());
    }

    // sets up the ui for the buttons
    // TODO: brittle code - assumes the layout of btns is only 4 buttons
    private void setUiButtons(LinearLayout btns, ColorScheme color, Context ctx) {
        TextView uv = (TextView) findViewById(R.id.color_scheme_user_txt);
        ImageButton likeib = (ImageButton) findViewById(R.id.like_image_btn);

        // go through all buttons and set their color to the corresponding
        // color in our scheme
        for(int i = 0; i < btns.getChildCount(); i++) {
            Button b = (Button) btns.getChildAt(i);

            b.setText(color.Colors[i]);
            b.setBackgroundColor(Color.parseColor(color.Colors[i]));

        }

        // check if the scheme is liked
        if(color.Liked == 1) {
            likeib.setColorFilter(Color.RED);
        } else {
            likeib.setColorFilter(Color.BLACK);
        }

        uv.setText("user: " + color.UserName);
    }
}
