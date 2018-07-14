package com.firstapp.quizzer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by kenechukwunnamani on 21/12/2017.
 */

public class GameEnd extends AppCompatActivity {

    Button newGame,gameMenu, shareGameScore;
    TextView gameEndScore, grade;
    int globalScore = 0;
    SharedPreferences.Editor editor;
    SharedPreferences pref;
    public static final String PREF_NAME = "highscore";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_end);
        editor = getSharedPreferences(PREF_NAME, MODE_PRIVATE).edit();
        pref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        int highscore = pref.getInt("highscore", 0);

        newGame = (Button) findViewById(R.id.new_game);
        gameMenu = (Button) findViewById(R.id.game_menu);
        shareGameScore = (Button) findViewById(R.id.share_game_score);

        gameEndScore = (TextView) findViewById(R.id.end_game_score);
        grade = (TextView) findViewById(R.id.grade);

        Intent intent = getIntent();
        int score = intent.getIntExtra("score", 0);
        String category = intent.getStringExtra("category");
        globalScore = score;

        gameEndScore.setText("$"+score);
        if (score == 1500) {
            grade.setText("Wow!! Ain't you a genius! \n You got "+ score/100 + " out of 15 questions");
        } else if (score > 900){
            grade.setText("Nice work!! \n You got "+ score/100 + " out of 15 questions");
        } else if (score > 600) {
            grade.setText("Try harder next time! \n You got "+ score/100 + " out of 15 questions");
        } else if (score > 300 ){
            grade.setText("That's really poor! \n You got "+ score/100 + " out of 15 questions");
        } else {
            grade.setText("That's a horrible score. Just try Again! \n You got "+ score/100 + " out of 15 questions");
        }

        if ((score/100) > highscore) {
            editor.putInt("highscore", score/100);
            editor.putString("category", category);
            editor.apply();
        }
    }

    public void gameEndAction(View view) {
        switch (view.getId()) {
            case R.id.new_game:
                Intent newGame = new Intent(GameEnd.this, Category.class);
                GameEnd.this.startActivity(newGame);
                break;

            case R.id.game_menu:
                Intent gameMenu = new Intent(GameEnd.this, Menu.class);
                GameEnd.this.startActivity(gameMenu);
                break;

            case R.id.share_game_score:
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                String shareBody = "Hey look! I just played QuickQuizzer and got "+ globalScore / 100
                        + " out of 15 questions \n See if you can beat my score";
                String shareSubject = "QuickQuizzer Game";
                share.putExtra(Intent.EXTRA_SUBJECT, shareSubject);
                share.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(share, "Share Using"));
                break;
        }
    }
}
