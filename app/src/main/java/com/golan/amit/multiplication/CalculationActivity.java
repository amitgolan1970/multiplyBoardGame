package com.golan.amit.multiplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CalculationActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etLeft, etRight;
    Button btnSolve, btnGoBack;
    TextView tvAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation);

        etLeft = findViewById(R.id.etLeftId);
        etLeft.requestFocus();
        etRight = findViewById(R.id.etRightId);
        btnSolve = findViewById(R.id.btnSolve);
        ((View) btnSolve).setOnClickListener(this);
        btnGoBack = findViewById(R.id.btnGoBack);
        btnGoBack.setOnClickListener(this);
        tvAnswer = findViewById(R.id.tvAnswerId);

    }

    @Override
    public void onClick(View v) {
        if(v == btnSolve) {
            String leftStr = null, rightStr=null;
            int leftInt = -1, rightInt = -1;
            //  Left operand
            if(etLeft.getText() == null || etLeft.getText().toString().length() == 0) {
                Toast.makeText(this, "left operand is empty.", Toast.LENGTH_SHORT).show();
                etLeft.setText("");
                tvAnswer.setText("");
                return;
            }
            try {
                leftStr = etLeft.getText().toString();
                leftInt = Integer.parseInt(leftStr);
            } catch (Exception e) {
                Toast.makeText(this, "Illegal input in left operand.", Toast.LENGTH_SHORT).show();
                etLeft.setText("");
                tvAnswer.setText("");
                return;
            }
            //  Right operand
            if(etRight.getText() == null || etRight.getText().toString().length() == 0) {
                Toast.makeText(this, "right operand is empty.", Toast.LENGTH_SHORT).show();
                etRight.setText("");
                tvAnswer.setText("");
                return;
            }
            try {
                rightStr = etRight.getText().toString();
                rightInt = Integer.parseInt(rightStr);
            } catch (Exception e) {
                Toast.makeText(this, "illegal input in right operand.", Toast.LENGTH_SHORT).show();
                etRight.setText("");
                tvAnswer.setText("");
                return;
            }
            tvAnswer.setText(String.valueOf(leftInt * rightInt));

        } else if(v == btnGoBack) {
//            Intent i = new Intent(this, MultiplicationActivity.class);
//            startActivity(i);
            finish();
        }
    }
}
