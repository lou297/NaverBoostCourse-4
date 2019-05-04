package com.practice.mymovie.DbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class OpenDatabase {
    //database를 여는 메소드를 위한 클래스이다.
    static SQLiteDatabase database;

    public static void openDatabase(Context context, String databaseName) {
        try {
            database = context.openOrCreateDatabase(databaseName, Context.MODE_PRIVATE, null);
        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
