package com.firstapp.quizzer;

import android.app.Activity;
import android.app.Dialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by kenechukwunnamani on 12/01/2018.
 */

public class Utility extends Dialog implements View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button yes, no;
    public TextView display;
    private String dialogText;
    private MediaPlayer gameSound;

    public Utility(Activity a, String displayText) {
        super(a);
        this.c = a;
        dialogText = displayText;
        gameSound = new MediaPlayer();
    }

    public Utility(Activity a, String displayText, MediaPlayer myGameSound) {
        super(a);
        this.c = a;
        dialogText = displayText;
        gameSound = myGameSound;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        display = (TextView) findViewById(R.id.txt_dia);
        yes = (Button) findViewById(R.id.btn_yes);
        no = (Button) findViewById(R.id.btn_no);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);

        display.setText(dialogText);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                c.finish();
                break;
            case R.id.btn_no:
                gameSound.start();
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}
