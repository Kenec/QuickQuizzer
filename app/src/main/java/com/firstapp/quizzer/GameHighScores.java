package com.firstapp.quizzer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by kenechukwunnamani on 12/01/2018.
 */

public class GameHighScores extends AppCompatActivity {

    private Button highScoreBtn;
    public static final String PREF_NAME = "highscore";
    SharedPreferences pref;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highscore);
        highScoreBtn = (Button) findViewById(R.id.highscore);

        pref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        int highscore = pref.getInt("highscore", 0);
        String category = pref.getString("category", null);

        if (category == null) {
            highScoreBtn.setText(Integer.toString(highscore) + " out of 15 questions");
        } else {
            highScoreBtn.setText("Category: " + category + "\n" + Integer.toString(highscore) + " out of 15 questions");
        }



    }


}
