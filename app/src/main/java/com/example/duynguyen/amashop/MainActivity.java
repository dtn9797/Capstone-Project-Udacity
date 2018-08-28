package com.example.duynguyen.amashop;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageButton imageButton = findViewById(R.id.button);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageButton.setSelected(!imageButton.isSelected());
                imageButton.setImageDrawable(getDrawable(R.drawable.ic_check_black_24dp));
//                imageButton.setImageDrawable(null);
                imageButton.setBackgroundColor(getColor(R.color.colorAccent));

            }
        });
    }
}
