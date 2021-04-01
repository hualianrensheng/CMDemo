package com.cmdemo.cmdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cmdemo.cmdemo.textview.CMStepActivity;
import com.cmdemo.cmdemo.textview.ColorTrackActivity;

public class MainActivity extends AppCompatActivity{


    private TextView cmStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setCMStyepTextView();

        setCMColorTrackTextView();

    }


    private void setCMStyepTextView() {
        TextView cmStep = findViewById(R.id.tv_cm_step);

        cmStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, CMStepActivity.class);
                startActivity(intent);
            }
        });
    }


    private void setCMColorTrackTextView() {

        TextView cmTrackFragment = findViewById(R.id.tv_color_track_fragment);

        cmTrackFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ColorTrackActivity.class);
                startActivity(intent);
            }
        });
    }

}