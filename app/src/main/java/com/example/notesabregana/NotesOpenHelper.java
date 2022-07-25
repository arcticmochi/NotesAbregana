package com.example.notesabregana;

import static com.example.notesabregana.Note.*;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class NotesOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "myDatabase.db";
    private static final String DATABASE_TABLE = "Notes";
    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_CREATE = "create table " + DATABASE_TABLE + " (" + KEY_ID +
            " integer primary key autoincrement, " + KEY_NOTE_COLUMN + " text, " + KEY_NOTE_CREATED_COLUMN +
            " double);";

    public NotesOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(sqLiteDatabase);
    }
}
