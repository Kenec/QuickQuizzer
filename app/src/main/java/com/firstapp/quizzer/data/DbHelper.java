package com.firstapp.quizzer.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.firstapp.quizzer.Question;
import com.firstapp.quizzer.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static com.firstapp.quizzer.data.QuizContract.QuestionSchema.KEY_ANSWER;
import static com.firstapp.quizzer.data.QuizContract.QuestionSchema.KEY_CATEGORY;
import static com.firstapp.quizzer.data.QuizContract.QuestionSchema.KEY_ID;
import static com.firstapp.quizzer.data.QuizContract.QuestionSchema.KEY_OPTA;
import static com.firstapp.quizzer.data.QuizContract.QuestionSchema.KEY_OPTB;
import static com.firstapp.quizzer.data.QuizContract.QuestionSchema.KEY_OPTC;
import static com.firstapp.quizzer.data.QuizContract.QuestionSchema.KEY_OPTD;
import static com.firstapp.quizzer.data.QuizContract.QuestionSchema.KEY_QUES;
import static com.firstapp.quizzer.data.QuizContract.QuestionSchema.TABLE_QUEST;

/**
 * Created by kenechukwunnamani on 16/12/2017.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "QuickQuizzer.db";
    private Resources mResources;
    Context context;
    private SQLiteDatabase dbase;

    private int funnyQuestionsPath = R.raw.funny_questions;
    private int geographyQuestionPath = R.raw.geography_questions;
    private int politicsQuestionPath = R.raw.politics_questions;
    private int randomQuestionPath = R.raw.random_questions;
    private int religionQuestionPath = R.raw.religion_questions;
    private int scienceQuestionPath = R.raw.science_questions;
    private int sportsQuestionPath = R.raw.sports_questions;
    private int techQuestionPath = R.raw.tech_questions;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mResources = context.getResources();
        dbase = this.getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_QUEST + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_CATEGORY + " TEXT NOT NULL, "
                + KEY_QUES + " TEXT NOT NULL, " + KEY_ANSWER + " TEXT NOT NULL, " + KEY_OPTA + " TEXT NOT NULL, "
                + KEY_OPTB + " TEXT NOT NULL, " + KEY_OPTC + " TEXT NOT NULL, " + KEY_OPTD + " TEXT NOT NULL " +" );";

        db.execSQL(sql);

        try {
            addQuestion(db, funnyQuestionsPath);
            addQuestion(db, geographyQuestionPath);
            addQuestion(db, politicsQuestionPath);
            addQuestion(db, randomQuestionPath);

            addQuestion(db, religionQuestionPath);
            addQuestion(db, scienceQuestionPath);
            addQuestion(db, sportsQuestionPath);
            addQuestion(db, techQuestionPath);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the older table if it exists
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_QUEST);
        // Create table again
        onCreate(db);
    }

    private void addQuestion(SQLiteDatabase db, int filePath) throws IOException, JSONException {

        final String CATEGORY = "category";
        final String QUESTION = "question";
        final String ANSWER = "answer";
        final String OPTIONA = "optionA";
        final String OPTIONB = "optionB";
        final String OPTIONC = "optionC";
        final String OPTIOND = "optionD";

        try {
            String jsonDataString = readJsonData(filePath);
            JSONObject jsonQuestionObject = new JSONObject(jsonDataString);

            String questionCategory = jsonQuestionObject.getString("category");
            JSONArray jsonQuestionArray = jsonQuestionObject.getJSONArray("questions");

            for (int i = 0; i < jsonQuestionArray.length(); i++) {
                String question, answer, optionA, optionB, optionC, optionD;

                JSONObject questionItems = jsonQuestionArray.getJSONObject(i);

                question = questionItems.getString(QUESTION);
                answer = questionItems.getString(ANSWER);
                optionA = questionItems.getString(OPTIONA);
                optionB = questionItems.getString(OPTIONB);
                optionC = questionItems.getString(OPTIONC);
                optionD = questionItems.getString(OPTIOND);

                ContentValues values = new ContentValues();

                values.put(KEY_QUES, question);
                values.put(KEY_CATEGORY, questionCategory);
                values.put(KEY_ANSWER, answer);
                values.put(KEY_OPTA,optionA );
                values.put(KEY_OPTB, optionB);
                values.put(KEY_OPTC, optionC);
                values.put(KEY_OPTD, optionD);

                Log.d("Insertion", question);

                db.insert(TABLE_QUEST, null, values);

            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private String readJsonData(int file) throws IOException {
        InputStream inputStream = null;
        StringBuilder builder = new StringBuilder();

        try {
            String jsonDataString = null;
            inputStream = mResources.openRawResource(file);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            while (( jsonDataString = bufferedReader.readLine()) != null) {
                builder.append(jsonDataString);
            }

        } finally {
            if (inputStream != null){
                inputStream.close();
            }
        }

        return new String(builder);
    }


    public List<Question> getAllQuestion(String category) {
        List<Question> questionList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_QUEST + " WHERE category='"+ category +"'";
        dbase = this.getReadableDatabase();
        Cursor cursor = dbase.rawQuery(selectQuery, null);

        // loop through all the item and add them to the list
        if (cursor.moveToFirst()){
            do {
                Question question = new Question();

                question.setID(cursor.getInt(0));
                question.setCATEGORY(cursor.getString(1));
                question.setQUESTION(cursor.getString(2));
                question.setANSWER(cursor.getString(3));
                question.setOPTA(cursor.getString(4));
                question.setOPTB(cursor.getString(5));
                question.setOPTC(cursor.getString(6));
                question.setOPTD(cursor.getString(7));

                questionList.add(question);
            } while(cursor.moveToNext());
        }

        // return the question list
        return questionList;
    }

    public int rowCount() {
        int row = 0;

        String selectQuery = "SELECT * FROM " + TABLE_QUEST;
        SQLiteDatabase dbase = this.getWritableDatabase();
        Cursor cursor = dbase.rawQuery(selectQuery, null);

        row = cursor.getCount();

        return row;
    }

}
