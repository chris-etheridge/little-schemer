package com.example.chrisetheridge.littleschemer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chrisetheridge.littleschemer.model.ColorScheme;
import com.example.chrisetheridge.littleschemer.utils.ColorsUtil;
import com.pes.androidmaterialcolorpickerdialog.ColorPicker;

public class AddScheme_v1 extends Activity {

    private ColorPicker COLOR_PICKER;
    private final String SAVE_FILE_PATH = "schemes.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_add_scheme);

        COLOR_PICKER = new ColorPicker(this, 200, 100, 100);
    }

    public void onColorTextFieldTap(final View view) {
        COLOR_PICKER.show();

        /* Using this colorpicker lib: https://github.com/Pes8/android-material-color-picker-dialog */

        Button okColor = (Button) COLOR_PICKER.findViewById(R.id.okColorButton);
        okColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // parse the color
                String color = ColorsUtil.Util.rgbToHex(COLOR_PICKER.getRed(),
                        COLOR_PICKER.getGreen(), COLOR_PICKER.getBlue());

                ((Button) view).setText(color);
                view.setBackgroundColor(Color.parseColor(color));

                COLOR_PICKER.dismiss();
            }
        });
    }

    public void onCancelButtonTap(View view) {
        Intent i = new Intent(this, LittleSchemerMain_v1.class);

        startActivity(i);
    }

    public void onSaveButtonTap(View view) {
        Button b1 = (Button) findViewById(R.id.color_btn_1);
        Button b2 = (Button) findViewById(R.id.color_btn_2);
        Button b3 = (Button) findViewById(R.id.color_btn_3);
        Button b4 = (Button) findViewById(R.id.color_btn_4);
        TextView nametc = (TextView) findViewById(R.id.color_user_name);

        //TODO:
        // better error showing than just a toast
        if(validateNameEntry(nametc.getText().toString())) {
            Toast.makeText(this, "Name cannot be empty!", Toast.LENGTH_SHORT).show();
        } else {
            String[] colors = {b1.getText().toString(), b2.getText().toString(),
                    b3.getText().toString(), b4.getText().toString()};
            String name = nametc.getText().toString();

            ColorScheme newscheme = new ColorScheme(colors, name, false);

            ColorsUtil.FileUtil.saveOneSchemeToFile(newscheme, SAVE_FILE_PATH, this);

            // show the new intent
            Intent i = new Intent(this, LittleSchemerMain_v1.class);
            i.putExtra("new_scheme_data", newscheme.getSchemeForWriting());

            startActivity(i);
        }
    }

    private boolean validateNameEntry(String name) {
        boolean isValid = true;

        if(name.contains(" ") || name.isEmpty() || name.contains(",")) {
            isValid = false;
        }

        return isValid;
    }
}
