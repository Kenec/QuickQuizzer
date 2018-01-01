package com.firstapp.quizzer;

/**
 * Created by kenechukwunnamani on 16/12/2017.
 */

public class Question {
    private int ID;
    private String QUESTION;
    private String ANSWER;
    private String CATEGORY;
    private String OPTA;
    private String OPTB;
    private String OPTC;
    private String OPTD;

    public Question() {
        ID = 0;
        QUESTION = "";
        ANSWER = "";
        CATEGORY = "";
        OPTA = "";
        OPTB = "";
        OPTC = "";
        OPTD = "";
    }

    public Question(String Category, String Question, String Answer, String OptionA, String OptionB, String OptionC, String OptionD) {
        QUESTION = Question;
        ANSWER = Answer;
        CATEGORY = Category;
        OPTA = OptionA;
        OPTB = OptionB;
        OPTC = OptionC;
        OPTD = OptionD;
    }

    public int getID() { return ID; }
    public String getQUESTION() { return QUESTION; }
    public String getANSWER() { return ANSWER; }
    public String getCATEGORY() { return CATEGORY; }
    public String getOPTA() { return OPTA; }
    public String getOPTB() { return  OPTB; }
    public String getOPTC() { return OPTC; }
    public String getOPTD() { return OPTD; }

    public void setID(int id) { ID = id; }
    public void setQUESTION(String question) { QUESTION = question; }
    public void setANSWER(String answer) { ANSWER = answer; }
    public void setCATEGORY(String category) { CATEGORY = category; }
    public void setOPTA(String optionA) { OPTA = optionA; }
    public void setOPTB(String optionB) { OPTB = optionB; }
    public void setOPTC(String optionC) { OPTC = optionC; }
    public void setOPTD(String optionD) { OPTD = optionD; }

}
