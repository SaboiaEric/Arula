package br.com.arula.arula.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.com.arula.arula.model.Job;

/**
 * Created by Rafael Felipe on 03/10/2017.
 */

public class JobDAO extends SQLiteOpenHelper{
    public JobDAO(Context context) {
        super(context, "Arula", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Jobs (Id INTEGER PRIMARY KEY, Name TEXT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void Insert(Job job) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues data = getContentValuesDream(job);
        db.insert("Jobs", null, data);
    }

    public List<Job> Read() {
        List<Job> jobs = new ArrayList<>();
        String sql = "SELECT * FROM Jobs";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        while(c.moveToNext()) {
            Job job = new Job();

            job.setId(c.getLong(c.getColumnIndex("Id")));
            job.setName(c.getString(c.getColumnIndex("Name")));

            jobs.add(job);
        }
        c.close();

        return jobs;
    }

    public void Remove(Job job) {
        SQLiteDatabase db = getWritableDatabase();

        if(job.getId() == null) {
            String sql = "SELECT * FROM Jobs";
            SQLiteDatabase dbR = getReadableDatabase();
            Cursor c = dbR.rawQuery(sql, null);

            while(c.moveToNext())
                job.setId(c.getLong(c.getColumnIndex("Id")));
        }

        String[] params = {job.getId().toString()};

        db.delete("Jobs", "Id = ?", params);
    }

    public void Update(Job job) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues data = getContentValuesDream(job);
        String[] params = {job.getId().toString()};
        db.update("Jobs", data, "Id = ?", params);
    }

    private ContentValues getContentValuesDream(Job job) {
        ContentValues data = new ContentValues();
        data.put("Name", job.getName());

        return data;
    }

}
