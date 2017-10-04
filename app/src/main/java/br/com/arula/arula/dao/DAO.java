package br.com.arula.arula.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.com.arula.arula.model.Company;
import br.com.arula.arula.model.Job;
import br.com.arula.arula.model.User;

/**
 * Created by Rafael Felipe on 03/10/2017.
 */

public class DAO extends SQLiteOpenHelper {
    public DAO(Context context) {
        super(context, "Arula", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Users (Id INTEGER PRIMARY KEY, Name TEXT);";
        db.execSQL(sql);
        sql = "CREATE TABLE Companies (Id INTEGER PRIMARY KEY, Name TEXT);";
        db.execSQL(sql);
        sql = "CREATE TABLE Jobs (Id INTEGER PRIMARY KEY, Name TEXT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void InsertUsers(User user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues data = getContentValuesUsers(user);
        db.insert("Users", null, data);
    }

    public List<User> ReadUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM Users";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        while(c.moveToNext()) {
            User user = new User();

            user.setId(c.getLong(c.getColumnIndex("Id")));
            user.setName(c.getString(c.getColumnIndex("Name")));

            users.add(user);
        }
        c.close();

        return users;
    }

    public void RemoveUsers(User user) {
        SQLiteDatabase db = getWritableDatabase();

        if(user.getId() == null) {
            String sql = "SELECT * FROM Users";
            SQLiteDatabase dbR = getReadableDatabase();
            Cursor c = dbR.rawQuery(sql, null);

            while(c.moveToNext())
                user.setId(c.getLong(c.getColumnIndex("Id")));
        }

        String[] params = {user.getId().toString()};

        db.delete("Users", "Id = ?", params);
    }

    public void UpdateUsers(User user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues data = getContentValuesUsers(user);
        String[] params = {user.getId().toString()};
        db.update("Users", data, "Id = ?", params);
    }

    private ContentValues getContentValuesUsers(User user) {
        ContentValues data = new ContentValues();
        data.put("Name", user.getName());

        return data;
    }

    //


    public void InsertJobs(Job job) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues data = getContentValuesJobs(job);
        db.insert("Jobs", null, data);
    }

    public List<Job> ReadJobs() {
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

    public void RemoveJobs(Job job) {
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

    public void UpdateJobs(Job job) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues data = getContentValuesJobs(job);
        String[] params = {job.getId().toString()};
        db.update("Jobs", data, "Id = ?", params);
    }

    private ContentValues getContentValuesJobs(Job job) {
        ContentValues data = new ContentValues();
        data.put("Name", job.getName());

        return data;
    }





    public void InsertCompanies(Company company) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues data = getContentValuesCompanies(company);
        db.insert("Companies", null, data);
    }

    public List<Company> ReadCompanies() {
        List<Company> companies = new ArrayList<>();
        String sql = "SELECT * FROM Companies";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        while(c.moveToNext()) {
            Company company = new Company();

            company.setId(c.getLong(c.getColumnIndex("Id")));
            company.setName(c.getString(c.getColumnIndex("Name")));

            companies.add(company);
        }
        c.close();

        return companies;
    }

    public void RemoveCompanies(Company company) {
        SQLiteDatabase db = getWritableDatabase();

        if(company.getId() == null) {
            String sql = "SELECT * FROM Companies";
            SQLiteDatabase dbR = getReadableDatabase();
            Cursor c = dbR.rawQuery(sql, null);

            while(c.moveToNext())
                company.setId(c.getLong(c.getColumnIndex("Id")));
        }

        String[] params = {company.getId().toString()};

        db.delete("Companies", "Id = ?", params);
    }

    public void UpdateCompanies(Company company) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues data = getContentValuesCompanies(company);
        String[] params = {company.getId().toString()};
        db.update("Companies", data, "Id = ?", params);
    }

    private ContentValues getContentValuesCompanies(Company company) {
        ContentValues data = new ContentValues();
        data.put("Name", company.getName());

        return data;
    }

}

