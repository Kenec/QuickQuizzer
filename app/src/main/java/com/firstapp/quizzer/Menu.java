package com.firstapp.quizzer;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firstapp.quizzer.data.DbHelper;

/**
 * Created by kenechukwunnamani on 11/12/2017.
 */

public class Menu extends AppCompatActivity {

    private Button startGame, gameCredit, highScore;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        startGame = (Button) findViewById(R.id.start_game);
        gameCredit = (Button) findViewById(R.id.game_credit);
        highScore = (Button) findViewById(R.id.game_scores);

    }

    public void HandleButtonClick(View view){

        switch(view.getId()) {

            case R.id.start_game:
                Intent startGame = new Intent(Menu.this, Category.class);
                Menu.this.startActivity(startGame);
                break;

            case R.id.game_credit:
                Intent gameCredit = new Intent(Menu.this, GameCredit.class);
                Menu.this.startActivity(gameCredit);
                break;

            case R.id.game_scores:
                Intent gameScore = new Intent(Menu.this, GameHighScores.class);
                Menu.this.startActivity(gameScore);
                break;
        }

    }

    @Override
    public void onBackPressed() {
        Utility customDialog = new Utility(Menu.this, "Please don't go");
        customDialog.show();
    }
}
