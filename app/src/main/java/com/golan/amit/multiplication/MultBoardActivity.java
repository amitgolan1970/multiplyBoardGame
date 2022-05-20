package com.golan.amit.multiplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MultBoardActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView ivBoard;
    Button btnGoBack;
    Animation animInOutScale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mult_board);

        ivBoard = findViewById(R.id.ivMultBoard);
        btnGoBack = findViewById(R.id.btnBack);
        btnGoBack.setOnClickListener(this);
        animInOutScale = AnimationUtils.loadAnimation(this, R.anim.anim_scale_inout);
        ivBoard.startAnimation(animInOutScale);
    }

    @Override
    public void onClick(View v) {
        if(v == btnGoBack) {
            finish();
        }
    }
}
