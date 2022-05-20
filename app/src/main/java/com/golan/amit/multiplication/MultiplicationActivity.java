package com.golan.amit.multiplication;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MultiplicationActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    ImageView ivPic;
    TextView tvDisplay;
    EditText etAnswer;
    ImageButton btnGo;
    RadioGroup radioGroupMode;
    RadioButton radioButtonTheMode;
    MultiplyHelper mh;
    TextView tvInfoStats;
    Button btnMultBoard;
    Animation animRight, animScale;
    SoundPool sp;
    int[] winSound;
    int[] looseSound;
    int punchSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplication);

        init();

        play();
    }

    private void init() {
        ivPic = findViewById(R.id.ivFront);
        tvDisplay = findViewById(R.id.tvDisplay);
        etAnswer = findViewById(R.id.etAnswer);
        etAnswer.requestFocus();
        btnGo = findViewById(R.id.btnGo);
        btnGo.setOnClickListener(this);
        radioGroupMode = findViewById(R.id.radioBtnMode);
        radioGroupMode.setOnCheckedChangeListener(this);
        mh = new MultiplyHelper();
        tvInfoStats = findViewById(R.id.tvInfoStats);
        btnMultBoard = findViewById(R.id.btnMultBoardId);
        btnMultBoard.setOnClickListener(this);
        animRight = AnimationUtils.loadAnimation(this, R.anim.anim_rotate_right);
        animScale = AnimationUtils.loadAnimation(this, R.anim.anim_scale);

        /**
         * Sound
         */

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes aa = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_GAME). build();
            sp = new SoundPool.Builder()
                    .setMaxStreams(10).setAudioAttributes(aa).build();
        } else {
            sp = new SoundPool(10, AudioManager.STREAM_MUSIC, 1);
        }
        winSound = new int[2];
        winSound[0] = sp.load(this, R.raw.cheering, 1);
        winSound[1] = sp.load(this, R.raw.applause, 1);
        looseSound = new int[2];
        looseSound[0] = sp.load(this, R.raw.failtrombone, 1);
        looseSound[1] = sp.load(this, R.raw.explode, 1);
        punchSound = sp.load(this, R.raw.punch, 1);
    }

    private void play() {
        mh.generateNums();
        mh.resetTotalFails();
        mh.resetFails();
        mh.resetRoundCounter();
        mh.set_mode(MultiplyHelper.NOVICE);
        btnMultBoard.setText(R.string.mult_board);
        btnGo.setEnabled(true);
        radioGroupMode.setVisibility(View.VISIBLE);
        RadioButton b = findViewById(R.id.novice);
        b.setChecked(true);
        etAnswer.setEnabled(true);
        tvDisplay.setText(mh.formattedRepresentation());
        tvInfoStats.setText("");
    }

    private void end_game() {

        String tmpStats = "";
        if(mh.get_total_fails() == 0) {
            tmpStats = "מעולה. ללא טעויות כלל";
        } else if (mh.get_total_fails() == 1) {
            tmpStats = "טעות אחת בלבד.";
        } else {
            tmpStats = "מספר טעויות: " + mh.get_total_fails();
        }
        tmpStats = "\n" + tmpStats + "\n";
        int tmpSuccess = MultiplyHelper.ROUNDS - mh.get_total_fails();
        if(tmpSuccess == 0) {
            tmpStats += "לא היו תשובות נכונות כלל. עצוב מאוד";
        } else if(tmpSuccess == 1) {
            tmpStats += "תשובה אחת נכונה";
        } else {
            tmpStats += "מספר תשובות נכונות: " + tmpSuccess;
        }
        tvInfoStats.setText(tvInfoStats.getText().toString() + " המשחק הסתיים." + tmpStats);
        btnMultBoard.setText(R.string.end_game);
        btnGo.setEnabled(false);
        radioGroupMode.setVisibility(View.INVISIBLE);
        etAnswer.setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        if (v == btnGo) {
            String numStr = null;
            int numInt = -1;
            if (etAnswer.getText() == null || etAnswer.getText().toString().length() == 0) {
                Toast.makeText(this, "הקלט ריק", Toast.LENGTH_SHORT).show();
                etAnswer.setText("");
                return;
            }
            try {
                numStr = etAnswer.getText().toString();
                numInt = Integer.parseInt(numStr);
            } catch (Exception e) {
                Toast.makeText(this, "קלט בלתי חוקי", Toast.LENGTH_SHORT).show();
                etAnswer.setText("");
                return;
            }

            if (numInt == mh.result()) {
                etAnswer.setText("");
                mh.increaseRoundCounter();
                mh.resetFails();

                if (mh.get_roundCounter() == (MultiplyHelper.ROUNDS + 1)) {
                    Toast.makeText(this, "סיום המשחק", Toast.LENGTH_SHORT).show();
                    tvDisplay.setText("");
                    end_game();
                } else {
                    Toast.makeText(this, "בינגו, ניצחון !!", Toast.LENGTH_SHORT).show();
                    mh.generateNums();
                    tvDisplay.setText(mh.formattedRepresentation());
                    tvInfoStats.setText("תשובה נכונה");
                    ivPic.startAnimation(animRight);
                    sp.play(winSound[((int)(Math.random() * 10) % 2)], 1, 1, 0, 0, 1);
                    v.setAlpha((float)0.8);
                }
            } else {
                if (mh.get_fails() == (MultiplyHelper.ALLOWEDFAILATTEMPTS - 1)) {
                    Toast.makeText(this, "תשובה לא נכונה. התשובה הייתה " + mh.result(), Toast.LENGTH_SHORT).show();
                    etAnswer.setText("");
                    mh.increaseRoundCounter();
                    tvInfoStats.setText("הפסדת בסיבוב זה. התשובה הייתה " + mh.result());
                    mh.generateNums();
                    tvDisplay.setText(mh.formattedRepresentation());
                    mh.resetFails();
                    mh.increaseTotalFails();
                    ivPic.startAnimation(animScale);
                    sp.play(looseSound[((int)(Math.random() * 10) % 2)], 1, 1, 0, 0, 1);
                    v.setAlpha((float)0.8);
                    if (mh.get_roundCounter() == (MultiplyHelper.ROUNDS + 1)) {
                        Toast.makeText(this, "סיום המשחק", Toast.LENGTH_SHORT).show();
                        tvDisplay.setText("");
//                        tvInfoStats.setText("סיום המשחק");
                        end_game();
                    }
                } else {
                    sp.play(punchSound, 1, 1, 0, 0, 1);
                    v.setAlpha((float)0.8);
                    Toast.makeText(this, "תשובה לא נכונה", Toast.LENGTH_SHORT).show();
                    etAnswer.setText("");
                    tvInfoStats.setText("תשובה לא נכונה");
                    mh.increaseFails();
                }
            }
        } else if (v == btnMultBoard) {
            if(radioGroupMode.getVisibility() == View.INVISIBLE) {
                Toast.makeText(this, "Another game starting", Toast.LENGTH_SHORT).show();
                play();
                return;
            }
            if (mh.get_mode().equals(MultiplyHelper.EXPERT)) {
                Toast.makeText(this, "Solve help Clicked", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, CalculationActivity.class);
                startActivity(i);
            } else {
                Toast.makeText(this, "Mult board Clicked", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, MultBoardActivity.class);
                startActivity(i);
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.begin:
                mh.set_mode(MultiplyHelper.BEGINNER);
                btnMultBoard.setText(R.string.mult_board);
                break;
            case R.id.novice:
                mh.set_mode(MultiplyHelper.NOVICE);
                btnMultBoard.setText(R.string.mult_board);
                break;
            case R.id.expert:
                mh.set_mode(MultiplyHelper.EXPERT);
                btnMultBoard.setText(R.string.help_solve);
                break;
            default:
                Log.e(MainActivity.DEBUGTAG, "in radio switch case - should not reach here");
                break;
        }
        mh.generateNums();
        tvDisplay.setText(mh.formattedRepresentation());
    }
}
