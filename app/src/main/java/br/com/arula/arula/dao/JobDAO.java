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
 * Created by Rafael Felipe on 04/10/2017.
 */

public class JobDAO extends SQLiteOpenHelper {
    public JobDAO(Context context) {
        super(context, "Arula_Jobs", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Jobs (" +
                "Id INTEGER PRIMARY KEY, " +
                "Name TEXT, Desc TEXT, " +
                "Salary numeric(18,0) " +
                "Image TEXT, " +
                "CompanyName TEXT, " +
                "Type TEXT, " +
                "Hour TEXT, " +
                "Req TEXT " +
                ");";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void Insert(Job job) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues data = getContentValuesJobs(job);
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
            job.setDesc(c.getString(c.getColumnIndex("Desc")));
            job.setSalary(c.getDouble(c.getColumnIndex("Salary")));
            //job.setImage(c.getString(c.getColumnIndex("Image")));
            job.setCompanyName(c.getString(c.getColumnIndex("CompanyName")));
            job.setType(c.getString(c.getColumnIndex("Type")));
            job.setHour(c.getString(c.getColumnIndex("Hour")));
            job.setReq(c.getString(c.getColumnIndex("Req")));


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
        ContentValues data = getContentValuesJobs(job);
        String[] params = {job.getId().toString()};
        db.update("Jobs", data, "Id = ?", params);
    }

    private ContentValues getContentValuesJobs(Job job) {
        ContentValues data = new ContentValues();
        data.put("Name", job.getName());
        data.put("Desc", job.getDesc());
        data.put("Salary", job.getSalary());
        //data.put("Image", job.getImage());
        data.put("CompanyName", job.getCompanyName());
        data.put("Type", job.getType());
        data.put("Hour", job.getHour());
        data.put("Req", job.getReq());

        return data;
    }

}
