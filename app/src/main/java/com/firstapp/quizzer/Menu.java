package com.firstapp.quizzer;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.firstapp.quizzer.data.DbHelper;

/**
 * Created by kenechukwunnamani on 11/12/2017.
 */

public class Menu extends AppCompatActivity {

    private Button startGame, chooseLevel, highScore;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        startGame = (Button) findViewById(R.id.start_game);
        chooseLevel = (Button) findViewById(R.id.game_level);
        highScore = (Button) findViewById(R.id.game_scores);

    }

    public void HandleButtonClick(View view){

        switch(view.getId()){

            case R.id.start_game:

                Intent startGame = new Intent(Menu.this, Category.class);
                Menu.this.startActivity(startGame);

                break;

            case R.id.game_level:

                break;

            case R.id.game_scores:

                break;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
