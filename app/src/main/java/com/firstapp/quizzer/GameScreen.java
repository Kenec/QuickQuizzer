package com.firstapp.quizzer;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firstapp.quizzer.data.DbHelper;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Created by kenechukwunnamani on 11/12/2017.
 */

public class GameScreen extends AppCompatActivity {

    private static final int SPLASH_DISPLAY_LENGTH = 2000;
    private CountDownTimer timer;
    private long miliSecondsLeft;
    private TextView gameTimer, game_questions, game_score;
    private Button game_pause, option1, option2, option3, option4, game_screen_menu;
    private int numberOfQuestions = 1;
    private List<Question> questionList;
    private Question currentQuestion;
    Set<Integer> questionIds;
    Integer[] question;
    private int qid = 0;
    private int score;
    private String category = "";
    private boolean correct, clicked = false;
    private MediaPlayer gameSound;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        questionIds = new HashSet<>();
        Random r = new Random();
        int Low = 1;
        int High = 100;

        do  {
            int Result = r.nextInt(High-Low) + Low;
            questionIds.add(Result);
        } while (questionIds.size() < 15);
        question = questionIds.toArray(new Integer[questionIds.size()]);

        // get the game category save as an extras
        Intent intent = getIntent();
        category = intent.getStringExtra("category");

        // instantiate the DbHelper class and get all questions
        DbHelper db = new DbHelper(this);
        questionList = db.getAllQuestion(category);

        // Get all textViews and Butons from the XML views
        gameTimer = (TextView) findViewById(R.id.game_timer_display);
        game_score = (TextView) findViewById(R.id.game_score);
        game_questions = (TextView) findViewById(R.id.game_questions);
        game_pause = (Button) findViewById(R.id.game_pause);
        game_screen_menu = (Button) findViewById(R.id.game_screen_menu);

        option1 = (Button) findViewById(R.id.option1);
        option2 = (Button) findViewById(R.id.option2);
        option3 = (Button) findViewById(R.id.option3);
        option4 = (Button) findViewById(R.id.option4);

        // set initial game score to zero
        score = 0;

        // call the method to set questions
        setQuestionView();

        // start the timer
        timerStart(15000);

        // initialize game sound
        gameSound = MediaPlayer.create(GameScreen.this, R.raw.quiz_sound);
        gameSound.setLooping(true);
        gameSound.start();
    }

    private void setQuestionView() {
        currentQuestion = questionList.get(question[qid]);

        game_questions.setText(currentQuestion.getQUESTION());
        option1.setText(currentQuestion.getOPTA());
        option2.setText(currentQuestion.getOPTB());
        option3.setText(currentQuestion.getOPTC());
        option4.setText(currentQuestion.getOPTD());

        option1.setBackgroundResource(R.drawable.button_enabled);
        option2.setBackgroundResource(R.drawable.button_enabled);
        option3.setBackgroundResource(R.drawable.button_enabled);
        option4.setBackgroundResource(R.drawable.button_enabled);

        option1.setTextColor(Color.WHITE);
        option2.setTextColor(Color.WHITE);
        option3.setTextColor(Color.WHITE);
        option4.setTextColor(Color.WHITE);

    }

    public CountDownTimer timerStart(long timeLength) {

        if (qid < 15) {

        enableButton(option1);
        enableButton(option2);
        enableButton(option3);
        enableButton(option4);

        setQuestionView();

        timer = new CountDownTimer(timeLength, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                miliSecondsLeft = millisUntilFinished;
                gameTimer.setText("" + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                if (( qid < 15 ) && ( clicked == true )) {
                    timerStart(15000);
                } else {
                    gameSound.stop();
                    disableButton(option1);
                    disableButton(option2);
                    disableButton(option3);
                    disableButton(option4);

                    displayStartGame();
                }
            }
        };

    }
        return timer.start();
    }

    public void timerPause() {

        disableButton(option1);
        disableButton(option2);
        disableButton(option3);
        disableButton(option4);

        timer.cancel();
    }

    private void timerResume() {

        enableButton(option1);
        enableButton(option2);
        enableButton(option3);
        enableButton(option4);

        timerStart(miliSecondsLeft);
    }

    public void pauseAndResume(View view) {
        if (game_pause.getText().toString().equals("Pause")) {
            game_pause.setText("Resume");
            gameSound.pause();
            timerPause();

        } else if (game_pause.getText().toString().equals("Resume")) {
            game_pause.setText("Pause");
            gameSound.start();
            timerResume();
        } else if (game_pause.getText().toString().equals("Restart")) {
            game_pause.setText("Pause");
            numberOfQuestions = 1;
            miliSecondsLeft = 0;
            qid = 0;
            setQuestionView();
            timerStart(15000);
        }
    }

    private void disableButton(Button button) {
        button.setEnabled(false);
    }

    private void enableButton(Button button) {
        button.setEnabled(true);
    }

    private void displayStartGame() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(GameScreen.this, GameEnd.class);
                intent.putExtra("score", score);
                intent.putExtra("category", category);
                gameSound.stop();
                finish();
                startActivity(intent);
            }
        }, 1500);
    }

    public void submitAnswer(View view) {

        timerPause();
        miliSecondsLeft = 0;
        qid++;

        switch (view.getId()) {
            case R.id.option1:
                if (currentQuestion.getANSWER().toString().equals(option1.getText().toString())) {
                    correct = true;
                    clicked = true;
                    score += 100;
                    option1.setBackgroundResource(R.drawable.button_focused);
                } else {
                    correct = false;
                    option1.setBackgroundResource(R.drawable.button_disabled);
                    showCorrectOption();
                    timerPause();
                    displayStartGame();
                }
                option1.setTextColor(Color.WHITE);
                break;

            case R.id.option2:
                if (currentQuestion.getANSWER().toString().equals(option2.getText().toString())) {
                    correct = true;
                    clicked = true;
                    score += 100;
                    option2.setBackgroundResource(R.drawable.button_focused);
                } else {
                    correct = false;
                    option2.setBackgroundResource(R.drawable.button_disabled);
                    showCorrectOption();
                    timerPause();
                    displayStartGame();
                }
                option2.setTextColor(Color.WHITE);
                break;

            case R.id.option3:
                if (currentQuestion.getANSWER().toString().equals(option3.getText().toString())) {
                    correct = true;
                    clicked = true;
                    score += 100;
                    option3.setBackgroundResource(R.drawable.button_focused);
                } else {
                    correct = false;
                    option3.setBackgroundResource(R.drawable.button_disabled);
                    showCorrectOption();
                    timerPause();
                    displayStartGame();
                }
                option3.setTextColor(Color.WHITE);
                break;

            case R.id.option4:
                if (currentQuestion.getANSWER().toString().equals(option4.getText().toString())) {
                    correct = true;
                    clicked = true;
                    score += 100;
                    option4.setBackgroundResource(R.drawable.button_focused);
                } else {
                    correct = false;
                    option4.setBackgroundResource(R.drawable.button_disabled);
                    showCorrectOption();
                    timerPause();
                    displayStartGame();
                }
                option4.setTextColor(Color.WHITE);
                break;

            default:
                break;
        }

        game_score.setText("Score: $" + score);
        game_screen_menu.setText(qid + " / 15");

        if ((correct == true) && (qid < 15)) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                clicked = false;
                timerStart(15000);
                }
            }, SPLASH_DISPLAY_LENGTH);
        } else {
                gameSound.stop();
                displayStartGame();
        }
    }

    private void showCorrectOption() {
        if (currentQuestion.getANSWER().toString().equals(option1.getText().toString())) {
            option1.setBackgroundResource(R.drawable.button_focused);
            option1.setTextColor(Color.WHITE);
        } else if (currentQuestion.getANSWER().toString().equals(option2.getText().toString())) {
            option2.setBackgroundResource(R.drawable.button_focused);
            option2.setTextColor(Color.WHITE);
        } else if(currentQuestion.getANSWER().toString().equals(option3.getText().toString())) {
            option3.setBackgroundResource(R.drawable.button_focused);
            option3.setTextColor(Color.WHITE);
        } else if (currentQuestion.getANSWER().toString().equals(option4.getText().toString())) {
            option4.setBackgroundResource(R.drawable.button_focused);
            option4.setTextColor(Color.WHITE);
        }
    }

    @Override
    public void onBackPressed() {
        gameSound.pause();
        Utility customDialog = new Utility(GameScreen.this, "Please don't go", gameSound);
        customDialog.show();
    }
}
