package com.example.chrisetheridge.littleschemer;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pes.androidmaterialcolorpickerdialog.ColorPicker;

public class ActivityAddScheme extends Activity {

    private ColorPicker COLOR_PICKER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_add_scheme);

        COLOR_PICKER = new ColorPicker(this, 200, 100, 100);
    }

    public void onColorTextFieldTap(View view) {
        COLOR_PICKER.show();

        /* Using this colorpicker lib: https://github.com/Pes8/android-material-color-picker-dialog */

        Button okColor = (Button) COLOR_PICKER.findViewById(R.id.okColorButton);
        okColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // parse the color
                String color = ColorsUtil.Util.rgbToHex(COLOR_PICKER.getRed(),
                        COLOR_PICKER.getGreen(), COLOR_PICKER.getBlue());

                COLOR_PICKER.dismiss();
            }
        });
    }
}
