package br.com.arula.arula.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.com.arula.arula.model.User;

/**
 * Created by Rafael Felipe on 03/10/2017.
 */

public class UserDAO extends SQLiteOpenHelper {
    public UserDAO(Context context) {
        super(context, "Arula", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Users (Id INTEGER PRIMARY KEY, Name TEXT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void Insert(User user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues data = getContentValuesDream(user);
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
        ContentValues data = getContentValuesDream(user);
        String[] params = {user.getId().toString()};
        db.update("Users", data, "Id = ?", params);
    }

    private ContentValues getContentValuesDream(User user) {
        ContentValues data = new ContentValues();
        data.put("Name", user.getName());

        return data;
    }

}
