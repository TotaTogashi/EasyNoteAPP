package com.example.easynote;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class UserOpenHelper extends SQLiteOpenHelper {
    public UserOpenHelper(@Nullable Context context) {
        super(context, "EasyNote.db", null, 1);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints 开启外键约束
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table users (_ID integer primary key autoincrement , _NAME varchar(20) not null ,_PSWD varchar(20) not null);");
        db.execSQL("create table msg(_NAME vharchar(20) , _GENDER integer default 0 , _SIGN varchar(20));");
        db.execSQL("create table res (_NAME varchar(20) ,_DATE varchar(20),_HEAD varchar(20),_MSG varchar(20),_TYPE int);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists users");
        db.execSQL("drop table if exists usr_msg");
        db.execSQL("drop table if exists res");
        onCreate(db);
    }
}
