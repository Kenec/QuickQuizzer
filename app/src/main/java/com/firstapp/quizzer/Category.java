package com.firstapp.quizzer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by kenechukwunnamani on 17/12/2017.
 */

public class Category extends AppCompatActivity {

    private Button Random, Tech, Politics, Science, Sports, Religion, Geography, Funny;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category);
        
        Random = (Button) findViewById(R.id.random);
        Tech = (Button) findViewById(R.id.tech);
        Politics = (Button) findViewById(R.id.politics);
        Science = (Button) findViewById(R.id.science);
        Sports = (Button) findViewById(R.id.sports);
        Religion = (Button) findViewById(R.id.religion);
        Geography = (Button) findViewById(R.id.geography);
        Funny = (Button) findViewById(R.id.funny);
    }


    private void customIntent(String category) {
        Intent intent = new Intent(this, GameScreen.class);
        intent.putExtra("category", category);
        startActivity(intent);
    }

    public void chooseCategory(View view) {

        switch (view.getId()){
            case R.id.random:
                customIntent("random");
                break;

            case R.id.tech:
                customIntent("tech");
                break;

            case R.id.politics:
                customIntent("politics");
                break;

            case R.id.science:
                customIntent("science");
                break;

            case R.id.sports:
                customIntent("sports");
                break;

            case R.id.religion:
                customIntent("religion");
                break;

            case R.id.geography:
                customIntent("geography");
                break;

            case R.id.funny:
                customIntent("funny");
                break;
        }
    }
}
