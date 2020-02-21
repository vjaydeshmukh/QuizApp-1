package com.example.myquizapp;//container for the different constant

import android.provider.BaseColumns;

public final class QuizContract {

    private QuizContract() {
    }

    public static class QuestionsTable implements BaseColumns { //use to make unique id in row
        public static final String TABLE_NAME = "quiz_questions";
        public static final String COLUMN_QuizID = "QuizID";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_OPTION1 = "option1";
        public static final String COLUMN_OPTION2 = "option2";
        public static final String COLUMN_OPTION3 = "option3";
        public static final String COLUMN_OPTION4 = "option4";
    }

    public static class QuizTable implements BaseColumns { //use to make unique id in row
        public static final String TABLE_NAME = "QUIZ_TABLE";
        public static final String COLUMN_QUIZ_TOPIC = "QUIZ_TOPIC";

    }


    // for user table updated by by heena
    public static class UserTable implements BaseColumns {
        public static final String USER_TABLE_NAME = "User_Details";
        private static String ID = "id";
        public static final String COLUMN_FIRSTNAME = "First_Name";
        public static final String COLUMN_lASTNAME = "Last_Name";
        public static final String COLUMN_EMAIL = "Email";
        public static final String COLUMN_PHONE_NUMBER = "Phone_Number";
        public static final String COLUMN_PASSWORD = "Password";
        public static final String COLUMN_SECURITY_HINT = "SecurityHint";
    }

    public static class ScoreTable implements BaseColumns {
        public static final String SCORE_TABLE_NAME = "Score_details";
        public static final String COLUMN_USERNAME = "Username";
        public static final String COLUMN_EMAIL = "Email";
        public static final String COLUMN_QID = "QuizId";
        public static final String COLUMN_SCORE = "Score";
        public static final String COLUMN_NUMQUES = "Total_Questions";

    }
}