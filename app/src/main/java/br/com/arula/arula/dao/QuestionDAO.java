package br.com.arula.arula.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.com.arula.arula.model.Question;

/**
 * Created by Rafael Felipe on 04/10/2017.
 */

public class QuestionDAO extends SQLiteOpenHelper{

    public QuestionDAO(Context context) {
        super(context, "Arula_Questions", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
<<<<<<< HEAD
        String sql = "CREATE TABLE Questions (Id INTEGER PRIMARY KEY, Course TEXT, Question TEXT, resA TEXT, resB TEXT, resC TEXT, resD TEXT, resE TEXT, correctAnswer INTEGER);";
=======
        String sql = "CREATE TABLE Questions (Id INTEGER PRIMARY KEY, Name TEXT, Question TEXT, Answers TEXT, correctAnswer INTEGER);";
>>>>>>> dc13c0ca8fd3745f4996e2b8186ebb2c83bbb0c0
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void Insert(Question question) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues data = getContentValuesQuestions(question);
        db.insert("Questions", null, data);
    }

    public List<Question> Read() {
        List<Question> questions = new ArrayList<>();
        String sql = "SELECT * FROM Questions";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        while(c.moveToNext()) {
            Question question = new Question();

            question.setId(c.getLong(c.getColumnIndex("Id")));
<<<<<<< HEAD
            question.setQuestion(c.getString(c.getColumnIndex("Question")));
            question.setCorrectAnswer(c.getInt(c.getColumnIndex("correctAnswer")));
            question.setCourse(c.getString(c.getColumnIndex("Course")));
            question.setResA(c.getString(c.getColumnIndex("resA")));
            question.setResB(c.getString(c.getColumnIndex("resB")));
            question.setResC(c.getString(c.getColumnIndex("resC")));
            question.setResD(c.getString(c.getColumnIndex("resD")));
            question.setResE(c.getString(c.getColumnIndex("resE")));
=======
            question.setName(c.getString(c.getColumnIndex("Name")));
            question.setQuestion(c.getString(c.getColumnIndex("Question")));
            question.setAnswers(c.getString(c.getColumnIndex("Answers")));
            question.setCorrectAnswer(c.getInt(c.getColumnIndex("correctAnswer")));
>>>>>>> dc13c0ca8fd3745f4996e2b8186ebb2c83bbb0c0

            questions.add(question);
        }
        c.close();

        return questions;
    }

    public void Remove(Question question) {
        SQLiteDatabase db = getWritableDatabase();

        if(question.getId() == null) {
            String sql = "SELECT * FROM Questions";
            SQLiteDatabase dbR = getReadableDatabase();
            Cursor c = dbR.rawQuery(sql, null);

            while(c.moveToNext())
                question.setId(c.getLong(c.getColumnIndex("Id")));
        }

        String[] params = {question.getId().toString()};

        db.delete("Questions", "Id = ?", params);
    }

    public void Update(Question question) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues data = getContentValuesQuestions(question);
        String[] params = {question.getId().toString()};
        db.update("Questions", data, "Id = ?", params);
    }

    private ContentValues getContentValuesQuestions(Question question) {
        ContentValues data = new ContentValues();
<<<<<<< HEAD
        data.put("Question", question.getQuestion());
        data.put("correctAnswer", question.getCorrectAnswer());
        data.put("Course", question.getCourse());
        data.put("resA", question.getResA());
        data.put("resB", question.getResB());
        data.put("resC", question.getResC());
        data.put("resD", question.getResD());
        data.put("resE", question.getResE());
=======
        data.put("Name", question.getName());
        data.put("Question", question.getQuestion());
        data.put("Answers", question.getAnswers());
        data.put("correctAnswer", question.getCorrectAnswer());
>>>>>>> dc13c0ca8fd3745f4996e2b8186ebb2c83bbb0c0

        return data;
    }
}
