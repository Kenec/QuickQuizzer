package com.firstapp.quizzer.data;

import android.provider.BaseColumns;

/**
 * Created by kenechukwunnamani on 16/12/2017.
 */

public class QuizContract {
    public static class QuestionSchema implements BaseColumns {
        public static final String TABLE_QUEST = "Questions";
        // Questions table name
        public static final String KEY_ID = "id";
        public static final String KEY_QUES = "question";
        public static final String KEY_CATEGORY = "category"; // question category
        public static final String KEY_ANSWER = "answer"; // correct option
        public static final String KEY_OPTA = "optiona"; // option A
        public static final String KEY_OPTB = "optionb"; // option B
        public static final String KEY_OPTC = "optionc"; // option C
        public static final String KEY_OPTD = "optiond"; // option D
    }
}
