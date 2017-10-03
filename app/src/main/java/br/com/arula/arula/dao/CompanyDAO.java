package br.com.arula.arula.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.com.arula.arula.model.Company;

/**
 * Created by Rafael Felipe on 03/10/2017.
 */

public class CompanyDAO extends SQLiteOpenHelper{
    public CompanyDAO(Context context) {
        super(context, "Arula", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Companies (Id INTEGER PRIMARY KEY, Name TEXT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void Insert(Company company) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues data = getContentValuesDream(company);
        db.insert("Companies", null, data);
    }

    public List<Company> Read() {
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

    public void Remove(Company company) {
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

    public void Update(Company company) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues data = getContentValuesDream(company);
        String[] params = {company.getId().toString()};
        db.update("Companies", data, "Id = ?", params);
    }

    private ContentValues getContentValuesDream(Company company) {
        ContentValues data = new ContentValues();
        data.put("Name", company.getName());

        return data;
    }

}
