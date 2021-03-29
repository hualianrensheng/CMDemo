package com.cmdemo.cmdemo.textview;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cmdemo.cmdemo.R;

public class CMStepActivity extends AppCompatActivity {

    private CMStyepTextView cmStyep;
    private EditText cmStyepEt;
    private Button cmStyepBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_m_step);

        initView();
        initCMStep();
    }

    private void initView() {
        cmStyep = findViewById(R.id.step);
        cmStyepEt = findViewById(R.id.step_et);
        cmStyepBt = findViewById(R.id.step_bt);
    }

    private void initCMStep() {
        final String currentText = cmStyepEt.getText().toString();
        //1.设置最大步数
        cmStyep.setMaxStep(1000);

        cmStyepBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(cmStyepEt == null){
//                    Toast.makeText(getApplicationContext(),"请输入步数",Toast.LENGTH_SHORT).show();
//                    return;
//                }
                //2.添加属性动画
                ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 500);
                valueAnimator.setDuration(1000);
                valueAnimator.setInterpolator(new DecelerateInterpolator());
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float animatedValue = (float) animation.getAnimatedValue();
                        cmStyep.setStepProgress((int) animatedValue);
                    }
                });
                valueAnimator.start();

            }
        });
    }

}