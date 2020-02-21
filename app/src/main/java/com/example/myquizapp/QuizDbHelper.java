package com.example.myquizapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.myquizapp.QuizContract.*;

import java.util.ArrayList;
import java.util.List;

import static android.provider.BaseColumns._ID;

public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Question.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TAG = "QuizDbHelper";

    private SQLiteDatabase db;

    public QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, "QuizDbHelper: called");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: called");
        this.db = db;

        //create Quiz table
        final String SQL_CREATE_QUIZ_TABLE = "CREATE TABLE " +
                QuizTable.TABLE_NAME + " ( " +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuizTable.COLUMN_QUIZ_TOPIC + " TEXT " +
                ")";

        db.execSQL(SQL_CREATE_QUIZ_TABLE);

        //create Question table
        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QuizID + " INTEGER," +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION4 + " TEXT," +
                "FOREIGN KEY" + "(" + QuestionsTable.COLUMN_QuizID + ") REFERENCES " + QuizTable.TABLE_NAME + "(" + QuizTable._ID + "))";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);


        //create user table
        final String SQL_CREATE_USERDETAILS_TABLE = "CREATE TABLE " +
                UserTable.USER_TABLE_NAME + " ( " +
                UserTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                UserTable.COLUMN_FIRSTNAME + " TEXT, " +
                UserTable.COLUMN_lASTNAME + " TEXT, " +
                UserTable.COLUMN_EMAIL + " TEXT, " +
                UserTable.COLUMN_PHONE_NUMBER + " TEXT, " +
                UserTable.COLUMN_PASSWORD + " TEXT, " +
                UserTable.COLUMN_SECURITY_HINT + " TEXT" +
                ")";
        db.execSQL(SQL_CREATE_USERDETAILS_TABLE);


        //Create Score table
        final String SQL_CREATE_SCORE_TABLE = "CREATE TABLE " +
                ScoreTable.SCORE_TABLE_NAME + " ( " +
                ScoreTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ScoreTable.COLUMN_USERNAME + " TEXT, " +
                ScoreTable.COLUMN_EMAIL + " TEXT, " +
                ScoreTable.COLUMN_QID + " INTEGER," +
                ScoreTable.COLUMN_SCORE + " INTEGER, " +
                ScoreTable.COLUMN_NUMQUES + " INTEGER" +
                ")";
        db.execSQL(SQL_CREATE_SCORE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    //insert data in question table
    public void insertData(Integer selectedId, String question, String answer, String option1, String option2, String option3) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QuizID, selectedId);
        cv.put(QuestionsTable.COLUMN_QUESTION, question);
        cv.put(QuestionsTable.COLUMN_OPTION1, option1);
        cv.put(QuestionsTable.COLUMN_OPTION2, option2);
        cv.put(QuestionsTable.COLUMN_OPTION3, option3);
        cv.put(QuestionsTable.COLUMN_OPTION4, answer);
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }

    public List<Question> getAllQuestions() {
        Log.d(TAG, "getAllQuestions: called");
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setQID(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QuizID)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION4)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }


    //Quiz Table Query
    public List<Quiz> getAllQTopics() {
        Log.d(TAG, "getAllQTopics: quiz called");
        List<Quiz> qtopicsList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuizTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Quiz quiz = new Quiz();
                quiz.setQid(c.getString(c.getColumnIndex(_ID)));
                quiz.setQuizTopic(c.getString(c.getColumnIndex(QuizTable.COLUMN_QUIZ_TOPIC)));
                qtopicsList.add(quiz);
            } while (c.moveToNext());
        }

        c.close();
        return qtopicsList;
    }

// returns list of quiz topic in label

    public List<String> getAllLabels() {
        List<String> list = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + QuizTable.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);//selectQuery,selectedArguments

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(1));//adding 2nd column data
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return list;
    }

    //fetching questions from db
    public List<Question> getSelectedQuestions(String qid) {
        int sqid = Integer.parseInt(qid);
        Log.d(TAG, "getSelectedQuestions: called");
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME + " WHERE QuizID=" + qid + ";", null);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setQID(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QuizID)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION4)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }

    //insert user details in table .updated by heena
    public void insertuserdetails(String firstName, String lastName, String email, String phone, String password, String security_hint) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(UserTable.COLUMN_FIRSTNAME, firstName);
        cv.put(UserTable.COLUMN_lASTNAME, lastName);
        cv.put(UserTable.COLUMN_EMAIL, email);
        cv.put(UserTable.COLUMN_PHONE_NUMBER, phone);
        cv.put(UserTable.COLUMN_PASSWORD, password);
        cv.put(UserTable.COLUMN_SECURITY_HINT, security_hint);
        db.insert(UserTable.USER_TABLE_NAME, null, cv);
    }

    //verify user account
    public String[] searchPassword(String uname) {
        db = this.getReadableDatabase();
        String username = "select First_Name,Email,Password from " + UserTable.USER_TABLE_NAME;
        Cursor cursor = db.rawQuery(username, null);
        String email, password, firstName;
        password = "not found";
        String[] return_value = new String[2];
        if (cursor.moveToFirst()) {
            do {

                email = cursor.getString(1);
                Log.d(TAG, "email: " + email);


                if (email.equals(uname)) {
                    password = cursor.getString(2);
                    firstName = cursor.getString(0);
                    return_value[0] = password;
                    return_value[1] = firstName;

                    break;

                }


            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return return_value;

    }

    //insert data into Score Table
    public void insertScoreData(String username, String emailid, Integer quizId, Integer score, Integer numQues) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ScoreTable.COLUMN_USERNAME, username);
        cv.put(ScoreTable.COLUMN_EMAIL, emailid);
        cv.put(ScoreTable.COLUMN_QID, quizId);
        cv.put(ScoreTable.COLUMN_SCORE, score);
        cv.put(ScoreTable.COLUMN_NUMQUES, numQues);
        db.insert(ScoreTable.SCORE_TABLE_NAME, null, cv);
    }

    //Red data from Score table
    public List<Score> getScoreData(String emailId) {
        Log.d(TAG, "getUserId: called");
        List<Score> scorelist = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT QuizId,Score FROM " + ScoreTable.SCORE_TABLE_NAME + " WHERE Email='" + emailId + "' GROUP BY QuizId", null);
        if (c.moveToFirst()) {
            do {
                Score score = new Score();
                score.setQuizId(c.getString(0));
                score.setScore(c.getString(1));
                scorelist.add(score);
            } while (c.moveToNext());
        }

        c.close();
        return scorelist;
    }

    //insert category in table
    public void insertCategory(String quiz) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(QuizTable.COLUMN_QUIZ_TOPIC, quiz);
        db.insert(QuizTable.TABLE_NAME, null, cv);
    }

    // email already exist validation
    public Boolean searchemail(String uemail) {
        Boolean flag = false;
        String email;
        db = this.getReadableDatabase();
        String username = "select Email from " + UserTable.USER_TABLE_NAME;
        Cursor cursor = db.rawQuery(username, null);

        if (cursor.moveToFirst()) {
            do {

                email = cursor.getString(0);
                Log.d(TAG, "email: " + email);


                if (email.equals(uemail)) {

                    flag = true;


                }


            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return flag;

    }

    //get all user data
    public List<User> getAllUser() {
        Log.d(TAG, "getAllUser: called");
        List<User> userList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + UserTable.USER_TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                User user = new User();
                user.setFname(c.getString(c.getColumnIndex(UserTable.COLUMN_FIRSTNAME)));
                user.setLname(c.getString(c.getColumnIndex(UserTable.COLUMN_lASTNAME)));
                user.setEmail(c.getString(c.getColumnIndex(UserTable.COLUMN_EMAIL)));
                user.setPhone(c.getString(c.getColumnIndex(UserTable.COLUMN_PHONE_NUMBER)));
                user.setPswd(c.getString(c.getColumnIndex(UserTable.COLUMN_PASSWORD)));
                user.setHint(c.getString(c.getColumnIndex(UserTable.COLUMN_SECURITY_HINT)));
                userList.add(user);
            } while (c.moveToNext());
        }

        c.close();
        return userList;
    }


    //insert user details in table .updated by heena
    public void updatePassword(String emailid, String security, String newpwd) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(UserTable.COLUMN_PASSWORD, newpwd);
        db.update(UserTable.USER_TABLE_NAME, cv, "Email=? and SecurityHint=?", new String[]{emailid, security});


    }

    //get leaderboard details
    public List<Score> getleaderboardscore(int quizId) {
        List<Score> adminscorelist = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT Username,Score FROM " + ScoreTable.SCORE_TABLE_NAME + " WHERE QuizId=" + quizId + " GROUP BY Username ORDER BY Score DESC", null);
        if (c.moveToFirst()) {
            do {
                Score score1 = new Score();
                score1.setUsername(c.getString(0));
                score1.setScore(c.getString(1));
                adminscorelist.add(score1);

            } while (c.moveToNext());
        }

        c.close();
        return adminscorelist;
    }
}
