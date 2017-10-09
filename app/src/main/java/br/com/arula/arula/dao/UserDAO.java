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

public class UserDAO extends SQLiteOpenHelper {
    public UserDAO(Context context) {
        super(context, "Arula_Users", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Users (" +
                "Id INTEGER PRIMARY KEY, " +
                "Name TEXT, " +
                "Email TEXT, " +
                "CPF TEXT, " +
                "Address TEXT, " +
                "Image TEXT, " +
                "Formation TEXT, " +
                "Score NUMERIC(18,0), " +
                "Desc TEXT, " +
                "Req TEXT" +
                ");";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void Insert(User user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues data = getContentValuesUsers(user);
        db.insert("Users", null, data);
    }

    public List<User> Read() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM Users";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        while(c.moveToNext()) {
            User user = new User();

            user.setId(c.getLong(c.getColumnIndex("Id")));
            user.setName(c.getString(c.getColumnIndex("Name")));
            user.setEmail(c.getString(c.getColumnIndex("Email")));
            user.setCPF(c.getString(c.getColumnIndex("CPF")));
            user.setAddress(c.getString(c.getColumnIndex("Address")));
            //user.setImage(c.getString(c.getColumnIndex("Image")));
            user.setFormation(c.getString(c.getColumnIndex("Formation")));
            user.setScore(c.getDouble(c.getColumnIndex("Score")));
            user.setDesc(c.getString(c.getColumnIndex("Desc")));
            user.setReq(c.getString(c.getColumnIndex("Req")));

            users.add(user);
        }
        c.close();

        return users;
    }

    public void Remove(User user) {
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

    public void Update(User user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues data = getContentValuesUsers(user);
        String[] params = {user.getId().toString()};
        db.update("Users", data, "Id = ?", params);
    }

    private ContentValues getContentValuesUsers(User user) {
        ContentValues data = new ContentValues();
        data.put("Name", user.getName());
        data.put("Email", user.getEmail());
        data.put("CPF", user.getCPF());
        data.put("Address", user.getAddress());
        //data.put("Image", user.getImage());
        data.put("Formation", user.getFormation());
        data.put("Score", user.getFormation());
        data.put("Desc", user.getDesc());
        data.put("Req", user.getReq());

        return data;
    }
}

