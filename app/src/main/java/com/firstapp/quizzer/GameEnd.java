package com.firstapp.quizzer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by kenechukwunnamani on 21/12/2017.
 */

public class GameEnd extends AppCompatActivity {

    Button newGame,gameMenu, shareGameScore;
    TextView gameEndScore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_end);

        newGame = (Button) findViewById(R.id.new_game);
        gameMenu = (Button) findViewById(R.id.game_menu);
        shareGameScore = (Button) findViewById(R.id.share_game_score);

        gameEndScore = (TextView) findViewById(R.id.end_game_score);

        Intent intent = getIntent();
        int score = intent.getIntExtra("score", 0);


        gameEndScore.setText("$"+score);
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

                break;
        }
    }
}
